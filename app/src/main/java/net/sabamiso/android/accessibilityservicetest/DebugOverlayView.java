package net.sabamiso.android.accessibilityservicetest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

public class DebugOverlayView extends View {
    static WindowManager window_manager;

    public static DebugOverlayView createView(Context context) {
        if (window_manager == null) {
            window_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        DebugOverlayView view = new DebugOverlayView(context);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                        | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                ,
                PixelFormat.TRANSPARENT);

        window_manager.addView(view, layoutParams);

        return view;
    }

    public static void destroyView(DebugOverlayView view) {
        if (view == null) return;
        window_manager.removeView(view);
    }
    //////////////////////////////////////////////////////////////////////

    List<Rect> rects;
    Paint p_background;
    Paint p_line;

    protected DebugOverlayView(Context context) {
        super(context);

        p_background = new Paint();
        p_background.setStyle(Paint.Style.FILL);
        p_background.setColor(Color.argb(30, 0, 255, 0));

        p_line = new Paint();
        p_line.setStyle(Paint.Style.STROKE);
        p_line.setColor(Color.argb(255, 0, 255, 0));
        p_line.setStrokeWidth(3);
        p_line.setAntiAlias(true);
    }

    public synchronized void setRects(List<Rect> rects) {
        this.rects = rects;
        invalidate();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        int offset_y;

        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), p_background);

        if (rects != null) {
            for(Rect r : rects) {
                canvas.drawRect(r, p_line);
            }
        }
    }
}
