package com.example.myapplication

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewParent
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * TextInputEditText, который расширяет фокус
 *
 * Основано на https://gist.github.com/siyamed/e7276009121d775c0d74ea7d6b01fc25
 */
class MyTextInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    private val buttonHeight = resources.getDimensionPixelSize(R.dimen.button_height)

    private val parentRect = Rect()

    override fun getFocusedRect(rect: Rect?) {
        super.getFocusedRect(rect)

        rect?.let {
            getMyParent().getFocusedRect(parentRect)

            parentRect.bottom = parentRect.bottom + buttonHeight

            rect.bottom = parentRect.bottom
        }
    }

    override fun getGlobalVisibleRect(rect: Rect?, globalOffset: Point?): Boolean {
        val result = super.getGlobalVisibleRect(rect, globalOffset)
        rect?.let {
            getMyParent().getGlobalVisibleRect(parentRect, globalOffset)

            parentRect.bottom = parentRect.bottom + buttonHeight

            rect.bottom = parentRect.bottom
        }
        return result
    }

    override fun requestRectangleOnScreen(rect: Rect?): Boolean {
        val result = super.requestRectangleOnScreen(rect)

        val parent = getMyParent()

        parentRect.set(0, parent.height, parent.right, parent.height)
        parent.requestRectangleOnScreen(parentRect, true)

        return result
    }

    private fun getMyParent(): View {
        var myParent: ViewParent? = parent

        while (myParent !is TextInputLayout && myParent != null) {
            myParent = myParent.parent
        }

        return if (myParent == null) {
            this
        } else {
            myParent as View
        }
    }
}