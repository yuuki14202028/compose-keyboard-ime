package com.example.composeime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.composeime.keys.AGyoKey
import com.example.composeime.keys.DeleteKey
import com.example.composeime.keys.EnterKey
import com.example.composeime.keys.KaGyoKey
import com.example.composeime.keys.DefaultInputKey
import com.example.composeime.keys.LeftKey
import com.example.composeime.keys.RightKey
import com.example.composeime.keys.SpaceKey
import com.example.composeime.keys.TranslateKey
import com.example.composeime.keys.KigouKey
import com.example.composeime.keys.MaGyoKey
import com.example.composeime.keys.NaGyoKey
import com.example.composeime.keys.RaGyoKey
import com.example.composeime.keys.SaGyoKey
import com.example.composeime.keys.HaGyoKey
import com.example.composeime.keys.TaGyoKey
import com.example.composeime.keys.KeyboardChangeKey
import com.example.composeime.keys.ExprKey
import com.example.composeime.keys.WaGyoKey
import com.example.composeime.keys.YaGyoKey

class KeyboardScreenViewModel : ViewModel() {

	var keyboardType by mutableStateOf(KeyboardType.FLICK)

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
fun FlickKeyboardScreen(view: KeyboardScreenViewModel = KeyboardScreenViewModel()) {
	val defaultViewConfiguration = LocalViewConfiguration.current
	val viewConfiguration = remember {
		CustomViewConfiguration(defaultViewConfiguration)
	}
	CompositionLocalProvider(LocalViewConfiguration provides viewConfiguration) {
		Column(Modifier.fillMaxWidth().padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				DefaultInputKey(view, "戻", Modifier.weight(1f))
				AGyoKey(view, Modifier.weight(1f))
				KaGyoKey(view, Modifier.weight(1f))
				SaGyoKey(view, Modifier.weight(1f))
				DeleteKey(view, Modifier.weight(1f))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				LeftKey(view, Modifier.weight(1f))
				TaGyoKey(view, Modifier.weight(1f))
				NaGyoKey(view, Modifier.weight(1f))
				HaGyoKey(view, Modifier.weight(1f))
				RightKey(view, Modifier.weight(1f))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				DefaultInputKey(view, "次", Modifier.weight(1f))
				MaGyoKey(view, Modifier.weight(1f))
				YaGyoKey(view, Modifier.weight(1f))
				RaGyoKey(view, Modifier.weight(1f))
				SpaceKey(view, Modifier.weight(1f))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				KeyboardChangeKey(view, Modifier.weight(1f))
				TranslateKey(view, Modifier.weight(1f))
				WaGyoKey(view, Modifier.weight(1f))
				KigouKey(view, Modifier.weight(1f))
				EnterKey(view, Modifier.weight(1f))
			}
		}
	}
}

