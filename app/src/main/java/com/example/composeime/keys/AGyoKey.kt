package com.example.composeime.keys
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.DragCounter
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel


private object AGyoKey {

	const val A = "あ"
	const val I = "い"
	const val U = "う"
	const val E = "え"
	const val O = "お"
	const val VOICED_A = "ぁ"
	const val VOICED_I = "ぃ"
	const val VOICED_U = "ぅ"
	const val VOICED_E = "ぇ"
	const val VOICED_O = "ぉ"

}


@Composable
fun AGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(AGyoKey.A) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = AGyoKey.A
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
						selectedStr = when (selectedStr) {
							AGyoKey.A -> when {
								(counter.offsetX / counter.offsetY) in 0.25f..4f && counter.offsetX > 0 && counter.offsetY > 0 -> AGyoKey.VOICED_A
								counter.isLeft() -> AGyoKey.I //左
								counter.isUp() -> AGyoKey.U //上
								counter.isRight() -> AGyoKey.E //右
								counter.isDown() -> AGyoKey.O //下
								else -> selectedStr
							}
							AGyoKey.I -> when {
								counter.isAccDown() -> AGyoKey.VOICED_I //左 -> 左下
								else -> selectedStr
							}
							AGyoKey.U -> when {
								counter.isAccLeft() -> AGyoKey.VOICED_U //上 -> 左
								else -> selectedStr
							}
							AGyoKey.E -> when {
								counter.isAccDown() -> AGyoKey.VOICED_E //右 -> 下
								else -> selectedStr
							}
							AGyoKey.O -> when {
								counter.isAccRight() -> AGyoKey.VOICED_O //下 -> 右
								counter.isRight() && counter.offsetY > 0 -> selectedStr + "う" //右上
								else -> selectedStr
							}
							else -> selectedStr
						}
					}
				},
				onDragEnd = onEnd
			) },
		string = selectedStr,
		onEnd = onEnd
	)

}
