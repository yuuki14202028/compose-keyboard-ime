package com.example.composeime.keys

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel


class KeyScreenViewModel : ViewModel() {
	var pressed by mutableStateOf(false)
}

@Composable
fun AbsbstractKey(view: KeyScreenViewModel = remember { KeyScreenViewModel() }, modifier: Modifier, string: String, onPress: suspend PressGestureScope.(Offset) -> Unit = {}, onEnd:() -> Unit) {
	Box(modifier = modifier
		.height(52.dp)
		.clip(RoundedCornerShape(10.dp))
		.background(if (view.pressed) Color.hsl(331f, 0.2f, 0.25f) else Color.hsl(0f, 0.0f, 0.25f))
		.pointerInput(Unit) {
			detectTapGestures(
				onTap = {
					view.pressed = false
					onEnd()
				},
				onPress = { offset ->
					view.pressed = true
					onPress(offset)
				}
			)
		}, contentAlignment = Alignment.Center) {
		Text(string, color = Color.White, fontSize = 22.sp)
	}
}
