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


private object NaGyoKey {

	val aDan = "な"
	val iDan = "に"
	val uDan = "ぬ"
	val eDan = "ね"
	val oDan = "の"
	val yaDan = "にゃ"
	val yuDan = "にゅ"
	val yoDan = "にょ"

}


@Composable
fun NaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(NaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = NaGyoKey.aDan
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
							NaGyoKey.aDan -> when {
								counter.isLeft() -> NaGyoKey.iDan //左
								counter.isUp() -> NaGyoKey.uDan //上
								counter.isRight() -> NaGyoKey.eDan //右
								counter.isDown() -> NaGyoKey.oDan //下
								else -> selectedStr
							}
							NaGyoKey.iDan -> when {
								counter.isUp() && counter.offsetX > 0 -> NaGyoKey.yuDan //上
								counter.isRight() -> NaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> NaGyoKey.yoDan //下
								else -> selectedStr
							}
							in NaGyoKey.yuDan, NaGyoKey.yoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							NaGyoKey.uDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> NaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							NaGyoKey.eDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							NaGyoKey.oDan -> when {
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
