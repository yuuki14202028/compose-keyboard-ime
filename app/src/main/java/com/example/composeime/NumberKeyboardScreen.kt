package com.example.composeime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import com.example.composeime.keys.DeleteKey
import com.example.composeime.keys.EnterKey
import com.example.composeime.keys.DefaultInputKey
import com.example.composeime.keys.ExprKey
import com.example.composeime.keys.LeftKey
import com.example.composeime.keys.RightKey
import com.example.composeime.keys.SpaceKey
import com.example.composeime.keys.KeyboardChangeKey

@Composable
fun NumberKeyboardScreen(view: KeyboardScreenViewModel = KeyboardScreenViewModel()) {
	val defaultViewConfiguration = LocalViewConfiguration.current
	val viewConfiguration = remember {
		CustomViewConfiguration(defaultViewConfiguration)
	}
	CompositionLocalProvider(LocalViewConfiguration provides viewConfiguration) {
		Column(Modifier.fillMaxWidth().padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				DefaultInputKey(view, "戻", Modifier.weight(1f))
				DefaultInputKey(view, "1", Modifier.weight(1f))
				DefaultInputKey(view, "2", Modifier.weight(1f))
				DefaultInputKey(view, "3", Modifier.weight(1f))
				DeleteKey(view, Modifier.weight(1f))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				LeftKey(view, Modifier.weight(1f))
				DefaultInputKey(view, "4", Modifier.weight(1f))
				DefaultInputKey(view, "5", Modifier.weight(1f))
				DefaultInputKey(view, "6", Modifier.weight(1f))
				RightKey(view, Modifier.weight(1f))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				ExprKey(view, Modifier.weight(1f))
				DefaultInputKey(view, "7", Modifier.weight(1f))
				DefaultInputKey(view, "8", Modifier.weight(1f))
				DefaultInputKey(view, "9", Modifier.weight(1f))
				SpaceKey(view, Modifier.weight(1f))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
				KeyboardChangeKey(view, Modifier.weight(1f))
				DefaultInputKey(view, "*", Modifier.weight(1f))
				DefaultInputKey(view, "0", Modifier.weight(1f))
				DefaultInputKey(view, "#", Modifier.weight(1f))
				EnterKey(view, Modifier.weight(1f))
			}
		}
	}
}

