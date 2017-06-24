package net.sabamiso.android.accessibilityservicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.os.BuildCompat;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    public final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive()");

        TestAccessibilityService service = TestAccessibilityService.getInstance();
        if (service == null) {
            return;
        }

        switch (intent.getAction()) {
            case Intent.ACTION_LOCKED_BOOT_COMPLETED:
                service.onLockedBootCompleted();
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                if (!BuildCompat.isAtLeastN()) {
                    service.onLockedBootCompleted();
                }
                service.onUnlockedBootCompleted();
                break;
        }
    }
}
