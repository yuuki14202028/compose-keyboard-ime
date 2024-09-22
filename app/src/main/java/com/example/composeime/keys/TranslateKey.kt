package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KanaUtils
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun TranslateKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	AbsbstractKey(modifier = modifier, string =  "変") {
		if (view.composingText.isNotEmpty()) {
			val translatedKana = KanaUtils.translateKana(view.composingText.last())
			view.composingText = view.composingText.dropLast(1) + translatedKana
			view.selectedText = view.composingText
			(ctx as IMEService).currentInputConnection.apply {
				setComposingText(view.composingText, 1)
				val extractedText = getExtractedText(ExtractedTextRequest(), 0)
				setSelection(extractedText.selectionEnd - view.composingText.length, extractedText.selectionEnd)
			}
		}
	}
}
