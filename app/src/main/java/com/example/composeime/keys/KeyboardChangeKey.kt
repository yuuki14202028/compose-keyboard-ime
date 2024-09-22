package com.example.composeime.keys

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.example.composeime.DragCounter
import com.example.composeime.KeyboardScreenViewModel
import com.example.composeime.KeyboardType

@Composable
fun KeyboardChangeKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	val defaultChangeType = when (view.keyboardType) {
		KeyboardType.FLICK -> KeyboardType.ALPHABET
		KeyboardType.ALPHABET -> KeyboardType.FLICK
		KeyboardType.NUMBER -> KeyboardType.FLICK
	}
	var selectedType by remember { mutableStateOf(defaultChangeType) }
	val onEnd = {
		counter.reset()
		view.keyboardType = selectedType
		selectedType = when (view.keyboardType) {
			KeyboardType.FLICK -> KeyboardType.ALPHABET
			KeyboardType.ALPHABET -> KeyboardType.FLICK
			KeyboardType.NUMBER -> KeyboardType.FLICK
		}
		keyModel.pressed = false
	}
	AbsbstractKey(
		view = keyModel,
		modifier = modifier.pointerInput(Unit) {
			detectDragGestures(
				onDrag = { change, dragAmount ->
					change.consume()
					counter.count(dragAmount.x, dragAmount.y)
					if (counter.isDistant(100f)) {
						selectedType = when {
								counter.isLeft() -> KeyboardType.ALPHABET
								counter.isDown() -> KeyboardType.NUMBER
							else -> selectedType
						}
					}
				},
				onDragEnd = onEnd
			) },
		string = selectedType.str,
		onEnd = onEnd
	)
}
