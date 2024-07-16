package com.example.composeime

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atilika.kuromoji.mozc.Token
import com.atilika.kuromoji.mozc.Tokenizer

class ComposeKeyboardView(context: Context) : AbstractComposeView(context) {

	val view = KeyboardScreenViewModel()
	var tokenizer: Tokenizer = Tokenizer.Builder().build()

	@Composable
	override fun Content() {
		val imeService = (LocalContext.current) as IMEService
		Column {
			Text(view.composingText)
			val a: List<List<Token>> = tokenizer.multiTokenizeNBest(view.composingText, 40)
			val lists = a.distinctBy { tokens -> tokens.joinToString(separator = "") { it.reading } }
			LazyHorizontalGrid(rows = GridCells.Fixed(1), modifier = Modifier
				.height(48.dp)
				.fillMaxWidth()) {
				items(lists.size) { index ->
					val tokens = lists.getOrNull(index) ?: return@items
					Row(
						modifier = Modifier
							.border(1.dp, Color.Black)
							.padding(4.dp)
							.clickable {
								view.composingText = tokens.joinToString(separator = "") { it.reading }
								imeService.currentInputConnection.setComposingText(
									view.composingText,
									view.composingText.length
								)
								imeService.currentInputConnection.finishComposingText()
								view.composingText = ""
							}
					) {
						tokens.forEach {
							Text(it.reading, Modifier.background(Color.White), fontSize = 26.sp)
						}
					}
				}
			}
			KeyboardScreen(view)
		}
	}

}
