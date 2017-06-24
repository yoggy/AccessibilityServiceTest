package net.sabamiso.android.accessibilityservicetest;

import android.accessibilityservice.AccessibilityService;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.HashMap;

public class TestAccessibilityService extends AccessibilityService {
    public final String TAG = getClass().getSimpleName();

    static TestAccessibilityService singleton;
    static HashMap<Integer, String> map = new HashMap<Integer, String>();

    PowerManager.WakeLock wake_lock;

    static {
        map.put(AccessibilityEvent.TYPE_ANNOUNCEMENT, "AccessibilityEvent.TYPE_ANNOUNCEMENT");
        map.put(AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT, "AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT");
        map.put(AccessibilityEvent.TYPE_GESTURE_DETECTION_END, "AccessibilityEvent.TYPE_GESTURE_DETECTION_END");
        map.put(AccessibilityEvent.TYPE_GESTURE_DETECTION_START, "AccessibilityEvent.TYPE_GESTURE_DETECTION_START");
        map.put(AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED, "AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED");
        map.put(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END, "AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END");
        map.put(AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START, "AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START");
        map.put(AccessibilityEvent.TYPE_TOUCH_INTERACTION_END, "AccessibilityEvent.TYPE_TOUCH_INTERACTION_END");
        map.put(AccessibilityEvent.TYPE_TOUCH_INTERACTION_START, "AccessibilityEvent.TYPE_TOUCH_INTERACTION_START");
        map.put(AccessibilityEvent.	TYPE_VIEW_ACCESSIBILITY_FOCUSED, "AccessibilityEvent.\tTYPE_VIEW_ACCESSIBILITY_FOCUSED");
        map.put(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED, "AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED");
        map.put(AccessibilityEvent.TYPE_VIEW_CLICKED, "AccessibilityEvent.TYPE_VIEW_CLICKED");
        map.put(AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED, "AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED");
        map.put(AccessibilityEvent.TYPE_VIEW_FOCUSED, "AccessibilityEvent.TYPE_VIEW_FOCUSED");
        map.put(AccessibilityEvent.TYPE_VIEW_HOVER_ENTER, "AccessibilityEvent.TYPE_VIEW_HOVER_ENTER");
        map.put(AccessibilityEvent.TYPE_VIEW_HOVER_EXIT, "AccessibilityEvent.TYPE_VIEW_HOVER_EXIT");
        map.put(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED, "AccessibilityEvent.TYPE_VIEW_LONG_CLICKED");
        map.put(AccessibilityEvent.TYPE_VIEW_SCROLLED, "AccessibilityEvent.TYPE_VIEW_SCROLLED");
        map.put(AccessibilityEvent.TYPE_VIEW_SELECTED, "AccessibilityEvent.TYPE_VIEW_SELECTED");
        map.put(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED, "AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED");
        map.put(AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED, "AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED");
        map.put(AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY, "AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY");
        map.put(AccessibilityEvent.TYPE_WINDOWS_CHANGED, "AccessibilityEvent.TYPE_WINDOWS_CHANGED");
        map.put(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED, "AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED");
        map.put(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED, "AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED");
    }

    public static TestAccessibilityService getInstance() {
        return singleton;
    }

    void debug(String str) {
        Log.d(TAG, str);
    }

    void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    void err(String str) {
        Log.e(TAG, str);
        toast(str);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");

        super.onCreate();
        singleton = this;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onServiceConnected() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wake_lock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "TestAccessibilityService");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");

        super.onDestroy();
    }

    public void onLockedBootCompleted() {
        Log.d(TAG, "onLockedBootCompleted()");
    }

    public void onUnlockedBootCompleted() {
        Log.d(TAG, "onUnlockedBootCompleted()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged()");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int type = event.getEventType();
        String type_str = "";

        for (int k : map.keySet()) {
            if ((k & type) != 0) {
                type_str += map.get(k) + ":";
            }
        }
        debug("onAccessibilityEvent() : type=" + type_str);
    }

    @Override
    public void onInterrupt() {
        debug("onInterrupt()");
    }

    @Override
    protected boolean onKeyEvent(KeyEvent keyEvent) {
        debug("onKeyEvent() : keycode=" + keyEvent.getKeyCode());

        // Aキーを押したらフックしてドロアーを表示するアクションを実行
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_A ) {
            toast("KeyEvent.KEYCODE_A : handled!");
            performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
            wake_lock.acquire();
            wake_lock.release();
            return true; // handled
        }

        return false; // not handled
    }

}
