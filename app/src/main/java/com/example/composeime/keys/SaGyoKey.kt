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


internal object SaGyoKey {

	val aDan = "さ"
	val iDan = "し"
	val uDan = "す"
	val eDan = "せ"
	val oDan = "そ"
	val yaDan = "しゃ"
	val yuDan = "しゅ"
	val yoDan = "しょ"
	val voicedADan = "ざ"
	val voicedIDan = "じ"
	val voicedUDan = "ず"
	val voicedEDan = "ぜ"
	val voicedODan = "ぞ"
	val voicedYaDan = "じゃ"
	val voicedYuDan = "じゅ"
	val voicedYoDan = "じょ"

}


@Composable
fun SaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(SaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = SaGyoKey.aDan
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
							SaGyoKey.aDan -> when {
								(counter.offsetX / counter.offsetY) in 0.25f..4f && counter.offsetX > 0 && counter.offsetY > 0 -> SaGyoKey.voicedADan
								counter.isLeft() -> SaGyoKey.iDan //左
								counter.isUp() -> SaGyoKey.uDan //上
								counter.isRight() -> SaGyoKey.eDan //右
								counter.isDown() -> SaGyoKey.oDan //下
								else -> selectedStr
							}
							SaGyoKey.iDan -> when {
								counter.isAccDown() && dragAmount.x < 0 -> SaGyoKey.voicedIDan //左 -> 左下
								counter.isUp() && counter.offsetX > 0 -> SaGyoKey.yuDan //上
								counter.isRight() -> SaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> SaGyoKey.yoDan //下
								else -> selectedStr
							}
							SaGyoKey.voicedIDan -> when {
								counter.isUp() && counter.offsetX > 0 -> SaGyoKey.voicedYuDan //上
								counter.isRight() -> SaGyoKey.voicedYaDan //右
								counter.isDown() && counter.offsetX > 0 -> SaGyoKey.voicedYoDan //下
								else -> selectedStr
							}
							in SaGyoKey.yuDan, SaGyoKey.yoDan, SaGyoKey.voicedYuDan, SaGyoKey.voicedYoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							SaGyoKey.uDan -> when {
								counter.isAccLeft() -> SaGyoKey.voicedUDan //上 -> 左
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> SaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							SaGyoKey.voicedUDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								else -> selectedStr
							}
							SaGyoKey.eDan -> when {
								counter.isAccDown() -> SaGyoKey.voicedEDan //右 -> 下
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							SaGyoKey.voicedEDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							SaGyoKey.oDan -> when {
								counter.isAccRight() -> SaGyoKey.voicedODan //下 -> 右
								counter.isRight() && counter.offsetY > 0 -> selectedStr + "う" //右上
								else -> selectedStr
							}
							SaGyoKey.voicedODan -> when {
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
