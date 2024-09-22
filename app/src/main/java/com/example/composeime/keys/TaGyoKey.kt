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


private object TaGyoKey {

	val aDan = "た"
	val iDan = "ち"
	val uDan = "つ"
	val eDan = "て"
	val oDan = "と"
	val yaDan = "ちゃ"
	val yuDan = "ちゅ"
	val yoDan = "ちょ"
	val voicedADan = "だ"
	val voicedIDan = "ぢ"
	val voicedUDan = "づ"
	val voicedEDan = "で"
	val voicedODan = "ど"
	val voicedYaDan = "ぢゃ"
	val voicedYuDan = "ぢゅ"
	val voicedYoDan = "ぢょ"

}


@Composable
fun TaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(TaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = TaGyoKey.aDan
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
							TaGyoKey.aDan -> when {
								(counter.offsetX / counter.offsetY) in 0.25f..4f && counter.offsetX > 0 && counter.offsetY > 0 -> TaGyoKey.voicedADan
								counter.isLeft() -> TaGyoKey.iDan //左
								counter.isUp() -> TaGyoKey.uDan //上
								counter.isRight() -> TaGyoKey.eDan //右
								counter.isDown() -> TaGyoKey.oDan //下
								else -> selectedStr
							}
							TaGyoKey.iDan -> when {
								counter.isAccDown() && dragAmount.x < 0 -> TaGyoKey.voicedIDan //左 -> 左下
								counter.isUp() && counter.offsetX > 0 -> TaGyoKey.yuDan //上
								counter.isRight() -> TaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> TaGyoKey.yoDan //下
								else -> selectedStr
							}
							TaGyoKey.voicedIDan -> when {
								counter.isUp() && counter.offsetX > 0 -> TaGyoKey.voicedYuDan //上
								counter.isRight() -> TaGyoKey.voicedYaDan //右
								counter.isDown() && counter.offsetX > 0 -> TaGyoKey.voicedYoDan //下
								else -> selectedStr
							}
							in TaGyoKey.yuDan, TaGyoKey.yoDan, TaGyoKey.voicedYuDan, TaGyoKey.voicedYoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							TaGyoKey.uDan -> when {
								counter.isAccLeft() -> TaGyoKey.voicedUDan //上 -> 左
								counter.isAccRight() -> "っ" //上 -> 右
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> TaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							TaGyoKey.voicedUDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								else -> selectedStr
							}
							TaGyoKey.eDan -> when {
								counter.isAccDown() -> TaGyoKey.voicedEDan //右 -> 下
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							TaGyoKey.voicedEDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							TaGyoKey.oDan -> when {
								counter.isAccRight() -> TaGyoKey.voicedODan //下 -> 右
								counter.isRight() && counter.offsetY > 0 -> selectedStr + "う" //右上
								else -> selectedStr
							}
							TaGyoKey.voicedODan -> when {
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
