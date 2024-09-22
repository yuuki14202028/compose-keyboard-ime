package com.example.composeime

import android.content.Context
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atilika.kuromoji.mozc.Token
import com.atilika.kuromoji.mozc.Tokenizer

class KeyboardView(context: Context) : AbstractComposeView(context) {

	private val view = KeyboardScreenViewModel()

	companion object {
		private val tokenizer: Tokenizer = Tokenizer.Builder().build()
		val default = listOf("そうなんだ", "ありがとう", "ちがうよ", "えー", "ごめん").map(tokenizer::tokenize)
	}

	@Composable
	override fun Content() {
		val imeService = (LocalContext.current) as IMEService
		Column(Modifier.background(Color.hsl(0f, 0.1f, 0f))) {
			Box(modifier = Modifier.height(80.dp)) {
				val selectedText = view.selectedText
				val nBested: List<List<Token>> = if (selectedText.isNotEmpty()) {
					tokenizer.multiTokenizeNBest(selectedText, 40)
				} else default
				val lists = nBested.distinctBy { tokens -> tokens.joinToString(separator = "") { if (it.reading != "*") it.reading else it.surface } }
				val column = lists.fold(listOf<List<List<Token>>>()) { acc, next ->
					acc.lastOrNull()?.let { last ->
						if (last.sumOf { it.joinToString("") { it.reading }.length + 1 } < 25 - next.sumOf { it.reading.length }) {
							acc.dropLast(1) + listOf(last + listOf(next))
						} else acc + listOf(listOf(next))
					} ?: (acc + listOf(listOf(next)))
				}
				val first = if (selectedText.isNotEmpty() ) {
					lists.firstOrNull()?.let { first ->
						if (first.size > 1) {
							first.dropLast(1).joinToString("") { if (it.reading != "*") it.reading else it.surface } + first.last().surface
						} else selectedText
					}
				} else ""
				imeService.currentInputConnection.apply {
					setComposingText(first, 1)
					val extractedText = getExtractedText(ExtractedTextRequest(), 0)
					setSelection(extractedText.selectionEnd - view.composingText.length, extractedText.selectionEnd)
				}
				Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
					column.map { row ->
						Row {
							row.map { tokens ->
								val text = tokens.joinToString("") { if (it.reading != "*") it.reading else it.surface }
								Text(
									text = text,
									color = Color.White,
									fontSize = 16.sp,
									textAlign = TextAlign.Center,
									modifier = Modifier
										.border(1.dp, Color.hsl(0f, 0f, 0.35f))
										.padding(2.dp, 8.dp)
										.height(24.dp)
										.weight(text.length.toFloat())
										.clickable {
											view.lastTranslatedText = selectedText
											imeService.currentInputConnection.setComposingText(text, 1)
											view.lastTranslateText = text
											imeService.currentInputConnection.finishComposingText()
											view.composingText = view.composingText.replaceFirst(view.lastTranslatedText, "")
											imeService.currentInputConnection.setComposingText(view.composingText, 1)
											view.selectedText = view.composingText
										})
							}
						}
					}
				}
			}
			when(view.keyboardType) {
				KeyboardType.FLICK -> FlickKeyboardScreen(view)
				KeyboardType.ALPHABET -> AlphabetKeyboardScreen(view)
				KeyboardType.NUMBER -> NumberKeyboardScreen(view)
			}
		}
	}

}
