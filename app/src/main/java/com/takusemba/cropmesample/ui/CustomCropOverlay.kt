package com.takusemba.cropmesample.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.takusemba.cropme.CropOverlay
import com.takusemba.cropmesample.R

/**
 * Custom overlay which has a rounded rectangle frame.
 *
 * To create a custom overlay, you need to extend [CropOverlay] class and override [CropOverlay.drawCrop].
 * You can optionally override [CropOverlay.drawBackground], [CropOverlay.drawBorder] too.
 */
class CustomCropOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    cropOverlayAttrs: AttributeSet? = attrs
) : CropOverlay(context, attrs, defStyleAttr, cropOverlayAttrs) {


  override fun drawBackground(canvas: Canvas, paint: Paint) {
    // No Background
  }

  override fun drawBorder(canvas: Canvas, paint: Paint) {
    val frameRect = frame ?: return
    paint.color = ContextCompat.getColor(context, R.color.white)
    canvas.drawRect(frameRect, paint)

    drawFaceOval(canvas)
    drawBlurredOverlay(canvas)
  }

  override fun drawCrop(canvas: Canvas, paint: Paint) {
    // Nothing
  }

  private fun drawFaceOval(canvas: Canvas) {
    val frameRect = frame ?: return
    val cropPaddingTop = (frameRect.height() * 0.1).toFloat()
    val cropPaddingSide = (frameRect.width() * 0.2).toFloat()
    val cropPaddingBottom = (frameRect.height() * 0.25).toFloat()

    val ovalPaint = Paint().apply {
      strokeWidth = 7f
      style = Paint.Style.STROKE
      color = ContextCompat.getColor(context, R.color.white)
      pathEffect = DashPathEffect(floatArrayOf(40f, 40f), 80f)
    }
    canvas.drawOval(frameRect.left + cropPaddingSide, frameRect.top + cropPaddingTop, frameRect.right - cropPaddingSide, frameRect.bottom - cropPaddingBottom, ovalPaint)
  }

  private fun drawBlurredOverlay(canvas: Canvas) {
    val overlayPaint = Paint().apply {
      color = ContextCompat.getColor(context, R.color.overlay)
    }

    val frameRect = frame ?: return

    canvas.drawRect(0f, 0f, measuredWidth.toFloat(), frameRect.top, overlayPaint)
    canvas.drawRect(0f, frameRect.top, frameRect.left, measuredHeight.toFloat(), overlayPaint)
    canvas.drawRect(frameRect.left, frameRect.bottom, measuredWidth.toFloat(), measuredHeight.toFloat(), overlayPaint)
    canvas.drawRect(frameRect.right, frameRect.top, measuredWidth.toFloat(), frameRect.bottom, overlayPaint)
  }
}