package me.ousunny.donut

import android.accessibilityservice.AccessibilityButtonController
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class DonutService : AccessibilityService() {

    private val TAG = DonutService::class.simpleName

    private var abController : AccessibilityButtonController? = null
    private var abCallback : AccessibilityButtonController.AccessibilityButtonCallback? = null
    private var isAbAvailable : Boolean = false

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        TODO("Not yet implemented")
    }

    override fun onServiceConnected() {
        abController = accessibilityButtonController
        isAbAvailable = abController?.isAccessibilityButtonAvailable ?: false

        if (!isAbAvailable) return

        serviceInfo = serviceInfo.apply {
            flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON
        }

        abCallback =
            object: AccessibilityButtonController.AccessibilityButtonCallback() {
                override fun onClicked(controller: AccessibilityButtonController?) {
                    Log.d(TAG, "AB pressed")
                }

                override fun onAvailabilityChanged(
                    controller: AccessibilityButtonController?,
                    available: Boolean
                ) {
                    if (controller == abController) {
                        isAbAvailable = available
                    }
                }
            }

        abCallback?.also {
            abController?.registerAccessibilityButtonCallback(it)
        }
    }
}