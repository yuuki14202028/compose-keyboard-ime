package com.example.composeime.keys

import android.view.KeyEvent
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun EnterKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	AbsbstractKey(modifier = modifier, string = "決") {
		(ctx as IMEService).currentInputConnection.apply {
			if (view.composingText.isNotEmpty()) {
				val extractedText = getExtractedText(ExtractedTextRequest(), 0)
				finishComposingText()
				setSelection(extractedText.selectionEnd, extractedText.selectionEnd)
			} else {
				this.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
			}
		}
		view.composingText = ""
		view.selectedText = ""
	}
}
