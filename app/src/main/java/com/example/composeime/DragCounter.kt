package com.example.composeime

import kotlin.math.absoluteValue

class DragCounter() {

	var offsetX = 0f
	var offsetY = 0f
	var accX = 0f
	var accY = 0f

	fun count(x: Float, y: Float) {
		offsetX += x
		offsetY -= y
		accX = accX * 0.3f + x
		accY = accY * 0.3f - y
	}

	fun reset() {
		offsetX = 0f
		offsetY = 0f
		accX = 0f
		accY = 0f
	}

	fun isDistant(amount: Float) = offsetX.absoluteValue + offsetY.absoluteValue > amount

	fun isLeft(): Boolean = offsetX < -offsetY.absoluteValue
	fun isUp(): Boolean = offsetY > offsetX.absoluteValue
	fun isRight(): Boolean = offsetX > offsetY.absoluteValue
	fun isDown(): Boolean = offsetY < -offsetX.absoluteValue

	fun isAccLeft(): Boolean = accX < -accY.absoluteValue
	fun isAccUp(): Boolean = accY > accX.absoluteValue
	fun isAccRight(): Boolean = accX > accY.absoluteValue
	fun isAccDown(): Boolean = accY < -accX.absoluteValue

}