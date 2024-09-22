package com.example.composeime.keys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composeime.KeyboardScreenViewModel


@Composable
fun DefaultInputKey(view: KeyboardScreenViewModel, keyboardKey: String, modifier: Modifier) {
	AbsbstractKey(modifier = modifier, string = keyboardKey) {
		view.composingText += keyboardKey
		view.selectedText = view.composingText
	}
}