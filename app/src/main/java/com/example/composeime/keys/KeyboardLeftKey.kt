package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun KeyboardLeftKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	KeyboardAbstractKey(modifier = modifier, string = "左") {
		val imeService = (ctx as IMEService)
		val extractedText = imeService.currentInputConnection.getExtractedText(ExtractedTextRequest(), 0)
		if (view.composingText.isNotEmpty()) {
			imeService.currentInputConnection.setSelection(extractedText.selectionStart, extractedText.selectionEnd - 1)
			view.selectedText = view.selectedText.dropLast(1)
		} else {
			if (extractedText.selectionStart == extractedText.selectionEnd) {
				imeService.currentInputConnection.setSelection(extractedText.selectionStart - 1, extractedText.selectionEnd - 1)
			} else {
				imeService.currentInputConnection.setSelection(extractedText.selectionStart - 1, extractedText.selectionEnd)
			}
		}

	}
}
