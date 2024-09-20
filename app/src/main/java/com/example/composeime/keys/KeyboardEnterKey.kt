package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun KeyboardEnterKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	KeyboardAbstractKey(modifier = modifier, string = "決") {
		(ctx as IMEService).currentInputConnection.apply {
			val extractedText = getExtractedText(ExtractedTextRequest(), 0)
			finishComposingText()
			setSelection(extractedText.selectionEnd, extractedText.selectionEnd)
		}
		view.composingText = ""
		view.selectedText = ""
	}
}
