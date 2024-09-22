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


private object HaGyoKey {

	val aDan = "は"
	val iDan = "ひ"
	val uDan = "ふ"
	val eDan = "へ"
	val oDan = "ほ"
	val yaDan = "ひゃ"
	val yuDan = "ひゅ"
	val yoDan = "ひょ"
	val voicedADan = "ば"
	val voicedIDan = "び"
	val voicedUDan = "ぶ"
	val voicedEDan = "べ"
	val voicedODan = "ぼ"
	val voicedYaDan = "びゃ"
	val voicedYuDan = "びゅ"
	val voicedYoDan = "びょ"
	val semiVoicedADan = "ぱ"
	val semiVoicedIDan = "ぴ"
	val semiVoicedUDan = "ぷ"
	val semiVoicedEDan = "ぺ"
	val semiVoicedODan = "ぽ"
	val semiVoicedYaDan = "ぴゃ"
	val semiVoicedYuDan = "ぴゅ"
	val semiVoicedYoDan = "ぴょ"

}


@Composable
fun HaGyoKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(HaGyoKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = HaGyoKey.aDan
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
							HaGyoKey.aDan -> when {
								(counter.offsetX / counter.offsetY) in 0.25f..4f && counter.offsetX > 0 && counter.offsetY > 0 -> HaGyoKey.voicedADan
								(counter.offsetX / counter.offsetY) in -4f..-0.25f && counter.offsetX > 0 && counter.offsetY > 0 -> HaGyoKey.semiVoicedADan
								counter.isLeft() -> HaGyoKey.iDan //左
								counter.isUp() -> HaGyoKey.uDan //上
								counter.isRight() -> HaGyoKey.eDan //右
								counter.isDown() -> HaGyoKey.oDan //下
								else -> selectedStr
							}
							HaGyoKey.iDan -> when {
								counter.isAccDown() && dragAmount.x < 0 -> HaGyoKey.voicedIDan //左 -> 左下
								counter.isAccUp() && dragAmount.x < 0 -> HaGyoKey.semiVoicedIDan //左 -> 左上
								counter.isUp() && counter.offsetX > 0 -> HaGyoKey.yuDan //上
								counter.isRight() -> HaGyoKey.yaDan //右
								counter.isDown() && counter.offsetX > 0 -> HaGyoKey.yoDan //下
								else -> selectedStr
							}
							HaGyoKey.voicedIDan -> when {
								counter.isUp() && counter.offsetX > 0 -> HaGyoKey.voicedYuDan //上
								counter.isRight() -> HaGyoKey.voicedYaDan //右
								counter.isDown() && counter.offsetX > 0 -> HaGyoKey.voicedYoDan //下
								else -> selectedStr
							}
							HaGyoKey.semiVoicedIDan -> when {
								counter.isUp() && counter.offsetX > 0 -> HaGyoKey.semiVoicedYuDan //上
								counter.isRight() -> HaGyoKey.semiVoicedYaDan //右
								counter.isDown() && counter.offsetX > 0 -> HaGyoKey.semiVoicedYoDan //下
								else -> selectedStr
							}
							in HaGyoKey.yuDan, HaGyoKey.yoDan, HaGyoKey.voicedYuDan, HaGyoKey.voicedYoDan, HaGyoKey.semiVoicedYuDan, HaGyoKey.semiVoicedYoDan -> when {
								counter.offsetX < 0 -> selectedStr + "う"
								else -> selectedStr
							}
							HaGyoKey.uDan -> when {
								counter.isAccLeft() -> HaGyoKey.voicedUDan //上 -> 左
								counter.isAccRight() -> HaGyoKey.semiVoicedUDan //上 -> 右
								counter.isRight() -> selectedStr + "う" //右
								counter.offsetY < 0 -> HaGyoKey.aDan + "ん" //下
								else -> selectedStr
							}
							HaGyoKey.voicedUDan, HaGyoKey.semiVoicedUDan -> when {
								counter.isRight() -> selectedStr + "う" //右
								else -> selectedStr
							}
							HaGyoKey.eDan -> when {
								counter.isAccDown() -> HaGyoKey.voicedEDan //右 -> 下
								counter.isAccUp() -> HaGyoKey.semiVoicedEDan // 右 -> 上
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							HaGyoKey.voicedEDan, HaGyoKey.semiVoicedEDan -> when {
								counter.offsetX < 0 -> selectedStr + "ん" //左
								else -> selectedStr
							}
							HaGyoKey.oDan -> when {
								counter.isAccRight() -> HaGyoKey.voicedODan //下 -> 右
								counter.isAccLeft() -> HaGyoKey.semiVoicedODan //下 -> 左
								counter.isRight() && counter.offsetY > 0 -> selectedStr + "う" //右上
								else -> selectedStr
							}
							HaGyoKey.voicedODan, HaGyoKey.semiVoicedODan -> when {
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
