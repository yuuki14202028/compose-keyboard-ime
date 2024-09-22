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


private object KigouKey {

	val aDan = "、"
	val iDan = "。"
	val uDan = "？"
	val eDan = "！"
	val oDan = "…"
	val voicedUDan = "："
	val semiVoicedUDan = "・"

}


@Composable
fun KigouKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val keyModel = remember { KeyScreenViewModel() }
	var counter = remember { DragCounter() }
	var selectedStr by remember { mutableStateOf(KigouKey.aDan) }
	val onEnd = {
		view.composingText += selectedStr
		view.selectedText = view.composingText
		counter.reset()
		selectedStr = KigouKey.aDan
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
							KigouKey.aDan -> when {
								counter.isLeft() -> KigouKey.iDan //左
								counter.isUp() -> KigouKey.uDan //上
								counter.isRight() -> KigouKey.eDan //右
								counter.isDown() -> KigouKey.oDan //下
								else -> selectedStr
							}
							KigouKey.uDan -> when {
								counter.isAccLeft() -> KigouKey.voicedUDan //上 -> 左下
								counter.isAccRight() -> KigouKey.semiVoicedUDan //上 -> 左下
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
