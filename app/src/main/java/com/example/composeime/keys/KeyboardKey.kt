package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel
import kotlinx.coroutines.channels.ticker
import kotlin.math.absoluteValue
import kotlin.math.pow



@Composable
fun KeyboardKey(
	view: KeyboardScreenViewModel,
	keyboardKey: Array<String>,
	modifier: Modifier
) {
	val keyModel = remember { KeyScreenViewModel() }
	var offsetX by remember { mutableFloatStateOf(0f) }
	var offsetY by remember { mutableFloatStateOf(0f) }
	val ctx = LocalContext.current
	var selectedStr by remember { mutableStateOf(keyboardKey.first().toString()) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		(ctx as IMEService).currentInputConnection.apply {
			setComposingText(view.composingText, +1)
			val extractedText = getExtractedText(ExtractedTextRequest(), 0)
			setSelection(extractedText.selectionEnd - view.composingText.length, extractedText.selectionEnd)
		}
		offsetX = 0f
		offsetY = 0f
		selectedStr = keyboardKey.first().toString()
		keyModel.pressed = false
	}
	KeyboardAbstractKey(
		view = keyModel,
		modifier = modifier.pointerInput(Unit) {
			detectDragGestures(
				onDrag = { change, dragAmount ->
					change.consume()
					offsetX += dragAmount.x
					offsetY -= dragAmount.y
					println(offsetX to offsetY to (offsetX / offsetY))
					val charIndex = when {
						offsetX.absoluteValue + offsetY.absoluteValue < 100 -> 0
						offsetX < -offsetY.absoluteValue -> 1 //左
						offsetY > offsetX.absoluteValue -> 2 //上
						offsetX > offsetY.absoluteValue -> 3 //右
						offsetY < -offsetX.absoluteValue -> 4 //下
						else -> 0 //中
					}
					keyboardKey.getOrNull(charIndex)?.let { str ->
						selectedStr = str
					}
				},
				onDragEnd = onEnd
			) },
		string = selectedStr,
		onEnd = onEnd
	)
}