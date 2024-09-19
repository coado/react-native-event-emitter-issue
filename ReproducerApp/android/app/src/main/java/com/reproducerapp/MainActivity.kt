package com.reproducerapp

import android.os.Bundle
import android.util.Log
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.ReactApplication
import com.facebook.react.ReactInstanceManager
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.facebook.react.modules.core.DeviceEventManagerModule


class MainActivity : ReactActivity() {
    private val TAG = "MyEvent"
    private val INITIAL_EVENT = "InitialEvent"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mReactInstanceManager = (applicationContext as ReactApplication).reactNativeHost.reactInstanceManager
        val context = mReactInstanceManager.currentReactContext
        val params = Arguments.createMap()
        if (context != null) {
            sendEvent(INITIAL_EVENT, params, context)
        } else {
            val reactInstanceListener = object : ReactInstanceManager.ReactInstanceEventListener {
                override fun onReactContextInitialized(context: ReactContext) {
                    sendEvent(
                        INITIAL_EVENT, params,
                        context
                    )
                    mReactInstanceManager.removeReactInstanceEventListener(this)
                }
            }
            mReactInstanceManager.addReactInstanceEventListener(reactInstanceListener);
            if (!mReactInstanceManager.hasStartedCreatingInitialContext()) {
                mReactInstanceManager.createReactContextInBackground()
            }
        }
    }

    private fun sendEvent(eventName: String, params: Any, context: ReactContext) {
        try {
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
            Log.e(this.TAG, "Sending event $eventName")
        } catch (t: Throwable) {
            Log.e(TAG, t.localizedMessage ?: "Error sending event")
        }
    }
  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "ReproducerApp"

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)
}
