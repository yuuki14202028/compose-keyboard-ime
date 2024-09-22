package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.composeime.IMEService
import com.example.composeime.KeyboardScreenViewModel
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun DeleteKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	var timer = remember { Timer() }
	val ctx = LocalContext.current
	val on = {
		if (view.composingText.isNotEmpty()) {
			view.composingText = view.composingText.dropLast(1)
			view.selectedText = view.composingText
		} else {
			(ctx as IMEService).currentInputConnection.deleteSurroundingText(1, 0)
		}
	}
	AbsbstractKey(
		modifier = modifier,
		string = "除",
		onPress = {
			timer.schedule(250, 75) { on() }
			tryAwaitRelease()
			timer.cancel()
			timer = Timer()
		},
		onEnd = { on() }
	)
}
