package com.example.composeime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.composeime.keys.KeyboardDeleteKey
import com.example.composeime.keys.KeyboardEnterKey
import com.example.composeime.keys.KeyboardKey
import com.example.composeime.keys.KeyboardLeftKey
import com.example.composeime.keys.KeyboardRightKey
import com.example.composeime.keys.KeyboardSpaceKey
import com.example.composeime.keys.KeyboardTranslateKey

class KeyboardScreenViewModel : ViewModel() {

	var lastTranslateText by mutableStateOf("")
	var lastTranslatedText by mutableStateOf("")
	var composingText by mutableStateOf("")
	var selectedText by mutableStateOf("")

}

class CustomViewConfiguration(private val defaultViewConfiguration: ViewConfiguration) : ViewConfiguration by defaultViewConfiguration {
	override val minimumTouchTargetSize: DpSize = DpSize((1200).dp, (1200).dp)
	override val touchSlop: Float = -5f
}

@Composable
fun KeyboardScreen(view: KeyboardScreenViewModel = KeyboardScreenViewModel()) {
	val defaultViewConfiguration = LocalViewConfiguration.current
	val viewConfiguration = remember {
		CustomViewConfiguration(defaultViewConfiguration)
	}
	CompositionLocalProvider(LocalViewConfiguration provides viewConfiguration) {
		Column(Modifier.fillMaxWidth()) {
			Row {
				KeyboardKey(view, arrayOf("戻"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("あ", "い", "う", "え", "お"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("か", "き", "く", "け", "こ"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("さ", "し", "す", "せ", "そ"), Modifier.weight(1f))
				KeyboardDeleteKey(view, Modifier.weight(1f))
			}
			Row {
				KeyboardLeftKey(view, Modifier.weight(1f))
				KeyboardKey(view, arrayOf("た", "ち", "つ", "て", "と"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("な", "に", "ぬ", "ね", "の"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("は", "ひ", "ふ", "へ", "ほ"), Modifier.weight(1f))
				KeyboardRightKey(view, Modifier.weight(1f))
			}
			Row {
				KeyboardKey(view, arrayOf("絵"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("ま", "み", "む", "め", "も"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("や", "(", "ゆ", ")", "よ"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("ら", "り", "る", "れ", "ろ"), Modifier.weight(1f))
				KeyboardSpaceKey(view, Modifier.weight(1f))
			}
			Row {
				KeyboardKey(view, arrayOf("A"), Modifier.weight(1f))
				KeyboardTranslateKey(view, Modifier.weight(1f))
				KeyboardKey(view, arrayOf("わ", "を", "ん", "ー", "〜"), Modifier.weight(1f))
				KeyboardKey(view, arrayOf("、", "。", "？", "！", "…"), Modifier.weight(1f))
				KeyboardEnterKey(view, Modifier.weight(1f))
			}
		}
	}
}

