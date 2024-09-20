package com.example.composeime.keys

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.composeime.KeyboardScreenViewModel


class KeyScreenViewModel : ViewModel() {
	var pressed by mutableStateOf(false)
}

@Composable
fun KeyboardAbstractKey(view: KeyScreenViewModel = remember { KeyScreenViewModel() }, modifier: Modifier, string: String, onEnd:() -> Unit) {
	Box(modifier = modifier
		.height(52.dp)
		.background(if (view.pressed) Color.Red else Color.White)
		.border(1.dp, Color.Black)
		.pointerInput(Unit) {
			detectTapGestures(
				onTap = {
					onEnd()
					view.pressed = false
				},
				onPress = {
					view.pressed = true
				}
			)
		}, contentAlignment = Alignment.Center) {
		Text(string, Modifier)
	}
}
