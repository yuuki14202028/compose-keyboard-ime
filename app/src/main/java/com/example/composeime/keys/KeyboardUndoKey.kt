package com.example.composeime.keys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun KeyboardUndoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	KeyboardAbstractKey(modifier = modifier, string = "戻") {
		view.composingText = view.lastTranslatedText
		val imeService = (ctx as IMEService)
		imeService.currentInputConnection.setComposingText(view.composingText, view.composingText.length)
	}
}
