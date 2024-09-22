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
import com.example.composeime.keys.AlphabetKey
import com.example.composeime.keys.DefaultInputKey
import com.example.composeime.keys.DeleteKey
import com.example.composeime.keys.EnterKey
import com.example.composeime.keys.LeftKey
import com.example.composeime.keys.RightKey
import com.example.composeime.keys.SpaceKey
import com.example.composeime.keys.KanaAlphabetKey
import com.example.composeime.keys.KeyboardChangeKey

@Composable
fun AlphabetKeyboardScreen(view: KeyboardScreenViewModel = KeyboardScreenViewModel()) {
	val defaultViewConfiguration = LocalViewConfiguration.current
	val viewConfiguration = remember {
		CustomViewConfiguration(defaultViewConfiguration)
	}
	CompositionLocalProvider(LocalViewConfiguration provides viewConfiguration) {
		Column(Modifier.fillMaxWidth().padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
			Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
				("qwertyuiop" zip "QWERTYUIOP").map { (c, b) ->
					AlphabetKey(view, c.toString(), "", "", b.toString(), Modifier.weight(1f))
				}
			}
			Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
				AlphabetKey(view, "a", "", "", "A", Modifier.weight(1.5f).padding(start = 16.dp))
				("sdfghjk" zip "SDFGHJK").map { (c, b) ->
					AlphabetKey(view, c.toString(), "", "", b.toString(), Modifier.weight(1f))
				}
				AlphabetKey(view, "l", "", "", "L", Modifier.weight(1.5f).padding(end = 16.dp))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
				DefaultInputKey(view, "大", Modifier.weight(1.5f).padding(end = 8.dp))
				("zxcvbnm" zip "ZXCVBNM").map { (c, b) ->
					AlphabetKey(view, c.toString(), "", "", b.toString(), Modifier.weight(1f))
				}
				DeleteKey(view, Modifier.weight(1.5f).padding(start = 8.dp))
			}
			Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
				KeyboardChangeKey(view, Modifier.weight(1.5f))
				DefaultInputKey(view, "1", Modifier.weight(1.5f))
				DefaultInputKey(view, ",", Modifier.weight(1f))
				SpaceKey(view, Modifier.weight(2.5f))
				DefaultInputKey(view, ".", Modifier.weight(1f))
				LeftKey(view, Modifier.weight(1f))
				RightKey(view, Modifier.weight(1f))
				EnterKey(view, Modifier.weight(1.5f))
			}
		}
	}
}

