package com.example.composeime

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlin.math.abs
import kotlin.math.pow

class KeyboardScreenViewModel : ViewModel() {

	var composingText by mutableStateOf("")

}

class CustomViewConfiguration(
	private val defaultViewConfiguration: ViewConfiguration
) : ViewConfiguration by defaultViewConfiguration {

	override val minimumTouchTargetSize: DpSize = DpSize((1200).dp, (1200).dp)
	override val touchSlop: Float = -5f
}

@Composable
fun KeyboardScreen(view: KeyboardScreenViewModel = KeyboardScreenViewModel()) {
	val keysMatrix = arrayOf(
		arrayOf("戻", "a", "k", "s", "徐"),
		arrayOf("左", "t", "n", "h", "右"),
		arrayOf("絵", "m", "y", "r", "空"),
		arrayOf("ア", "変", "w", "、。？！…・：", "決")
	)
	val defaultViewConfiguration = LocalViewConfiguration.current
	val viewConfiguration = remember {
		CustomViewConfiguration(defaultViewConfiguration)
	}
	CompositionLocalProvider(LocalViewConfiguration provides viewConfiguration) {
		Column(Modifier.fillMaxWidth()) {
			keysMatrix.forEach { row ->
				Row(Modifier) {
					row.forEach { key ->
						KeyboardKey(view = view, keyboardKey = key, modifier = Modifier.weight(1f))
					}
				}
			}
		}
	}

}

@Composable
fun KeyboardKey(
	view: KeyboardScreenViewModel,
	keyboardKey: String,
	modifier: Modifier
) {
	var pressed by remember { mutableStateOf(false) }
	var offsetX by remember { mutableFloatStateOf(0f) }
	var offsetY by remember { mutableFloatStateOf(0f) }
	val ctx = LocalContext.current
	var selectChar by remember { mutableStateOf(keyboardKey.first().toString()) }
	val onEnd = {
		when (keyboardKey) {
			"徐" -> {
				if (view.composingText.isNotEmpty()) {
					view.composingText = view.composingText.dropLast(1)
					(ctx as IMEService).currentInputConnection.setComposingText(
						view.composingText,
						view.composingText.length
					)
				} else {
					(ctx as IMEService).currentInputConnection.deleteSurroundingText(1, 0)
				}
			}
			"変" -> {
				if (view.composingText.isNotEmpty()) {
					val str = when (view.composingText.last().toString()) {
						"あ" -> "ぁ"
						"い" -> "ぃ"
						"う" -> "う"
						"え" -> "ぇ"
						"お" -> "ぉ"
						"ぁ" -> "あ"
						"ぃ" -> "い"
						"ぅ" -> "う"
						"ぇ" -> "え"
						"ぉ" -> "お"

						"か" -> "が"
						"き" -> "ぎ"
						"く" -> "ぐ"
						"け" -> "げ"
						"こ" -> "ご"
						"が" -> "か"
						"ぎ" -> "き"
						"ぐ" -> "く"
						"げ" -> "け"
						"ご" -> "こ"

						"さ" -> "ざ"
						"し" -> "じ"
						"す" -> "ず"
						"せ" -> "ぜ"
						"そ" -> "ぞ"
						"ざ" -> "さ"
						"じ" -> "し"
						"ず" -> "す"
						"ぜ" -> "せ"
						"ぞ" -> "そ"

						"た" -> "だ"
						"ち" -> "ぢ"
						"つ" -> "っ"
						"っ" -> "づ"
						"て" -> "で"
						"と" -> "ど"
						"だ" -> "た"
						"ぢ" -> "ち"
						"づ" -> "つ"
						"で" -> "て"
						"ど" -> "と"

						"は" -> "ば"
						"ひ" -> "び"
						"ふ" -> "ぶ"
						"へ" -> "べ"
						"ほ" -> "ぼ"
						"ば" -> "ぱ"
						"び" -> "ぴ"
						"ぶ" -> "ぷ"
						"べ" -> "ぺ"
						"ぼ" -> "ぽ"
						"ぱ" -> "は"
						"ぴ" -> "ひ"
						"ぷ" -> "ふ"
						"ぺ" -> "へ"
						"ぽ" -> "ほ"

						"や" -> "ゃ"
						"ゆ" -> "ゅ"
						"よ" -> "ょ"
						"ゃ" -> "や"
						"ゅ" -> "ゆ"
						"ょ" -> "よ"
						else -> view.composingText.last().toString()
					}
					view.composingText = view.composingText.dropLast(1) + str
					(ctx as IMEService).currentInputConnection.setComposingText(
						view.composingText,
						view.composingText.length
					)
				}
			}
			"決" -> {
				(ctx as IMEService).currentInputConnection.finishComposingText()
				view.composingText = ""
			}
			else -> {
				view.composingText += selectChar
				(ctx as IMEService).currentInputConnection.setComposingText(view.composingText, view.composingText.length)
			}
		}
		pressed = false
		offsetX = 0f
		offsetY = 0f
		selectChar = keyboardKey.first().toString()
	}
	Box(modifier = modifier
		.height(52.dp)
		.background(if (pressed) Color.Red else Color.White)
		.border(1.dp, Color.Black)
		.pointerInput(Unit) {
			detectTapGestures(
				onTap = {
					onEnd()
				},
				onPress = {
					pressed = true
				}
			)
		}
		.pointerInput(Unit) {
			detectDragGestures(
				onDrag = { change, dragAmount ->
					change.consume()
					offsetX += dragAmount.x
					offsetY += dragAmount.y
					println(offsetX to offsetY to (offsetX / offsetY))
					if (offsetX.pow(2) + offsetY.pow(2) < 1000) {
						offsetX = 0f
						offsetY = 0f
						val newIndex = when {

						}
						selectChar = keyboardKey.getOrNull(newIndex)?.toString() ?: keyboardKey.first().toString()
					}
				},
				onDragEnd = onEnd
			)
		}, contentAlignment = Alignment.Center) {
		Text(selectChar, Modifier)
	}
}
