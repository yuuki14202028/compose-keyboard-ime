package com.example.composeime.keys

import android.view.inputmethod.ExtractedTextRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composeime.IMEService
import com.example.composeime.KanaUtils
import com.example.composeime.KeyboardScreenViewModel

@Composable
fun KeyboardTranslateKey(view: KeyboardScreenViewModel, modifier: Modifier) {
	val ctx = LocalContext.current
	KeyboardAbstractKey(modifier = modifier, string =  "変") {
		if (view.composingText.isNotEmpty()) {
			val translatedKana = KanaUtils.translateKana(view.composingText.last())
			view.composingText = view.composingText.dropLast(1) + translatedKana
			view.selectedText = view.composingText
			(ctx as IMEService).currentInputConnection.apply {
				setComposingText(view.composingText, 1)
				val extractedText = getExtractedText(ExtractedTextRequest(), 0)
				setSelection(extractedText.selectionEnd - view.composingText.length, extractedText.selectionEnd)
			}
		}
	}
}
