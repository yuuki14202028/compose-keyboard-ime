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


private object RaGyoKey {

	val aDan = "ら"
	val iDan = "り"
	val uDan = "る"
	val eDan = "れ"
	val oDan = "ろ"
	val yaDan = "りゃ"
	val yuDan = "りゅ"
	val yoDan = "りょ"

}


@Composable
fun RaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(RaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = RaGyoKey.aDan
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
							RaGyoKey.aDan -> when {
								counter.isLeft() -> RaGyoKey.iDan //左
								counter.isUp() -> RaGyoKey.uDan //上
								counter.isRight() -> RaGyoKey.eDan //右
								counter.isDown() -> RaGyoKey.oDan //下
								else -> selectedStr
							}
							RaGyoKey.iDan -> when {
								counter.isUp() && counter.offsetX > 0 -> RaGyoKey.yuDan //上
								counter.isRight() -> RaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> RaGyoKey.yoDan //下
								else -> selectedStr
							}
							in RaGyoKey.yuDan, RaGyoKey.yoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							RaGyoKey.uDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> RaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							RaGyoKey.eDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							RaGyoKey.oDan -> when {
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
