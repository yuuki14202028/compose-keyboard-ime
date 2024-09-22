package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun SpaceKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	AbsbstractKey(modifier = modifier, string = "空") {
		view.composingText += " "
		view.selectedText = view.composingText
		(ctx as IMEService).currentInputConnection.apply {
			setComposingText(view.composingText, view.composingText.length)
			val extractedText = getExtractedText(ExtractedTextRequest(), 0)
			setSelection(extractedText.selectionEnd - view.composingText.length, extractedText.selectionEnd)
		}
	}
}
