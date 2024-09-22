package com.example.composeime.keys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composeime.KeyboardScreenViewModel
import com.example.composeime.KeyboardType

@Composable
fun KanaAlphabetKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyString = when (view.keyboardType) {
		KeyboardType.FLICK -> "A"
		KeyboardType.ALPHABET -> "あ"
		KeyboardType.NUMBER -> "あ"
	}
	AbsbstractKey(modifier = modifier, string = keyString) {
		view.keyboardType = when (view.keyboardType) {
			KeyboardType.FLICK -> KeyboardType.ALPHABET
			KeyboardType.ALPHABET -> KeyboardType.FLICK
			KeyboardType.NUMBER -> KeyboardType.FLICK
		}
	}
}
