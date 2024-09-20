package com.example.composeime

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atilika.kuromoji.dict.Dictionary
import com.atilika.kuromoji.dict.UnknownDictionary
import com.atilika.kuromoji.mozc.Token
import com.atilika.kuromoji.mozc.Tokenizer
import com.atilika.kuromoji.viterbi.ViterbiNode

class ComposeKeyboardView(context: Context) : AbstractComposeView(context) {

	private val view = KeyboardScreenViewModel()


	companion object {
		private val tokenizer: Tokenizer = Tokenizer.Builder().build()
		val default = listOf("そうなんだ", "ありがとう", "ちがうよ", "えー", "ごめんね").map(tokenizer::tokenize)
	}

	@Composable
	override fun Content() {
		val imeService = (LocalContext.current) as IMEService
		Column(Modifier.background(Color.hsl(0f, 0.1f, 0f))) {
			Box(modifier = Modifier.height(100.dp)) {
				val selectedText = view.selectedText
				val nBested: List<List<Token>> = if (selectedText.isNotEmpty()) {
					tokenizer.multiTokenizeNBest(selectedText, 40)
				} else default
				val lists = nBested.distinctBy { tokens -> tokens.joinToString(separator = "") { it.reading } }
				val column = lists.fold(listOf<List<List<Token>>>()) { acc, next ->
					acc.lastOrNull()?.let { last ->
						if (last.sumOf { it.joinToString("") { it.reading }.length + 2 } < 15) {
							acc.dropLast(1) + listOf(last + listOf(next))
						} else acc + listOf(listOf(next))
					} ?: (acc + listOf(listOf(next)))
				}
				Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
					column.map { row ->
						Row {
							row.map { tokens ->
								val text = tokens.joinToString("") { it.reading }
								Text(
									text = text,
									color = Color.White,
									fontSize = 22.sp,
									textAlign = TextAlign.Center,
									modifier = Modifier
										.border(1.dp, Color.hsl(0f, 0f, 0.35f))
										.padding(10.dp)
										.height(30.dp)
										.weight(1f)
										.clickable {
											view.lastTranslatedText = selectedText
											val translateText = tokens.joinToString(separator = "") { it.reading }
											imeService.currentInputConnection.setComposingText(translateText, 1)
											view.lastTranslateText = translateText
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
			KeyboardScreen(view)
		}
	}

}
