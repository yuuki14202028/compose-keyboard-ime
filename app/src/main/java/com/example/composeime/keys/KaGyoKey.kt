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


private object KaGyoKey {

	val aDan = "か"
	val iDan = "き"
	val uDan = "く"
	val eDan = "け"
	val oDan = "こ"
	val yaDan = "きゃ"
	val yuDan = "きゅ"
	val yoDan = "きょ"
	val voicedADan = "が"
	val voicedIDan = "ぎ"
	val voicedUDan = "ぐ"
	val voicedEDan = "げ"
	val voicedODan = "ご"
	val voicedYaDan = "ぎゃ"
	val voicedYuDan = "ぎゅ"
	val voicedYoDan = "ぎょ"

}


@Composable
fun KaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(KaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = KaGyoKey.aDan
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
							KaGyoKey.aDan -> when {
								(counter.offsetX / counter.offsetY) in 0.25f..4f && counter.offsetX > 0 && counter.offsetY > 0 -> KaGyoKey.voicedADan
								counter.isLeft() -> KaGyoKey.iDan //左
								counter.isUp() -> KaGyoKey.uDan //上
								counter.isRight() -> KaGyoKey.eDan //右
								counter.isDown() -> KaGyoKey.oDan //下
								else -> selectedStr
							}
							KaGyoKey.iDan -> when {
								counter.isAccDown() && dragAmount.x < 0 -> KaGyoKey.voicedIDan //左 -> 左下
								counter.isUp() && counter.offsetX > 0 -> KaGyoKey.yuDan //上
								counter.isRight() -> KaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> KaGyoKey.yoDan //下
								else -> selectedStr
							}
							KaGyoKey.voicedIDan -> when {
								counter.isUp() && counter.offsetX > 0 -> KaGyoKey.voicedYuDan //上
								counter.isRight() -> KaGyoKey.voicedYaDan //右
								counter.isDown() && counter.offsetX > 0 -> KaGyoKey.voicedYoDan //下
								else -> selectedStr
							}
							in KaGyoKey.yuDan, KaGyoKey.yoDan, KaGyoKey.voicedYuDan, KaGyoKey.voicedYoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							KaGyoKey.uDan -> when {
								counter.isAccLeft() -> KaGyoKey.voicedUDan //上 -> 左
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> KaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							KaGyoKey.voicedUDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								else -> selectedStr
							}
							KaGyoKey.eDan -> when {
								counter.isAccDown() -> KaGyoKey.voicedEDan //右 -> 下
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							KaGyoKey.voicedEDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							KaGyoKey.oDan -> when {
								counter.isAccRight() -> KaGyoKey.voicedODan //下 -> 右
								counter.isRight() && counter.offsetY > 0 -> selectedStr + "う" //右上
								else -> selectedStr
							}
							KaGyoKey.voicedODan -> when {
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
