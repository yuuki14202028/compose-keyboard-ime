package com.example.composeime.keys
import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.DragCounter
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel
import kotlin.math.absoluteValue


private object YaGyoKey {

	val aDan = "や"
	val iDan = "("
	val uDan = "ゆ"
	val eDan = ")"
	val oDan = "よ"
	val voicedADan = "ゃ"
	val voicedIDan = "「"
	val voicedUDan = "ゅ"
	val voicedEDan = "」"
	val voicedODan = "ょ"

}


@Composable
fun YaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(YaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = YaGyoKey.aDan
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
							YaGyoKey.aDan -> when {
								(counter.offsetX / counter.offsetY) in 0.25f..4f && counter.offsetX > 0 && counter.offsetY > 0 -> YaGyoKey.voicedADan
								counter.isLeft() -> YaGyoKey.iDan //左
								counter.isUp() -> YaGyoKey.uDan //上
								counter.isRight() -> YaGyoKey.eDan //右
								counter.isDown() -> YaGyoKey.oDan //下
								else -> selectedStr
							}
							YaGyoKey.iDan -> when {
								counter.isAccDown() -> YaGyoKey.voicedIDan //左 -> 左下
								else -> selectedStr
							}
							YaGyoKey.uDan -> when {
								counter.isAccLeft() -> YaGyoKey.voicedUDan //上 -> 左
								counter.isRight() -> selectedStr + "う" //右
								else -> selectedStr
							}
							YaGyoKey.eDan -> when {
								counter.isAccDown() -> YaGyoKey.voicedEDan //右 -> 下
								else -> selectedStr
							}
							YaGyoKey.oDan -> when {
								counter.isAccRight() -> YaGyoKey.voicedODan //下 -> 右
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
