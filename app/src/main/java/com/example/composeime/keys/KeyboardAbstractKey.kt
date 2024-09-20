package com.example.composeime.keys

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.composeime.KeyboardScreenViewModel


class KeyScreenViewModel : ViewModel() {
	var pressed by mutableStateOf(false)
}

@Composable
fun KeyboardAbstractKey(view: KeyScreenViewModel = remember { KeyScreenViewModel() }, modifier: Modifier, string: String, onEnd:() -> Unit) {
	Box(modifier = modifier
		.height(52.dp)
		.clip(RoundedCornerShape(10.dp))
		.background(if (view.pressed) Color.hsl(331f, 0.2f, 0.25f) else Color.hsl(0f, 0.0f, 0.25f))
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
		Text(string, color = Color.White, fontSize = 22.sp)
	}
}
