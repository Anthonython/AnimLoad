package com.example.coroutinanim

import android.animation.ObjectAnimator
import android.view.View
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Animator(private val view: View, private val duration: Long) {
    private var animator: ObjectAnimator? = null
    private var job: Job? = null

    fun startAnimation() {
        job = GlobalScope.launch(Dispatchers.Main) {
            animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 500f).apply {
                this.duration = this@Animator.duration
                start()
            }

            while (isActive) {
                delay(1000) // Обновление позиции каждую секунду
                updatePosition()
            }
        }
    }

    fun stopAnimation() {
        animator?.cancel()
        job?.cancel()
    }

    private fun updatePosition() {
        val currentPosition = view.translationX
        val newPosition = currentPosition + 100 // Измените значение на сколько пикселей нужно переместить объект по оси X
        animator?.setFloatValues(currentPosition, newPosition)
        animator?.start()
    }
}
