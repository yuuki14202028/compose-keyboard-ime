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


private object MaGyoKey {

	val aDan = "ま"
	val iDan = "み"
	val uDan = "む"
	val eDan = "め"
	val oDan = "も"
	val yaDan = "みゃ"
	val yuDan = "みゅ"
	val yoDan = "みょ"

}


@Composable
fun MaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(MaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = MaGyoKey.aDan
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
							MaGyoKey.aDan -> when {
								counter.isLeft() -> MaGyoKey.iDan //左
								counter.isUp() -> MaGyoKey.uDan //上
								counter.isRight() -> MaGyoKey.eDan //右
								counter.isDown() -> MaGyoKey.oDan //下
								else -> selectedStr
							}
							MaGyoKey.iDan -> when {
								counter.isUp() && counter.offsetX > 0 -> MaGyoKey.yuDan //上
								counter.isRight() -> MaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> MaGyoKey.yoDan //下
								else -> selectedStr
							}
							in MaGyoKey.yuDan, MaGyoKey.yoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							MaGyoKey.uDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> MaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							MaGyoKey.eDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							MaGyoKey.oDan -> when {
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
