package com.example.admin.rtcr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new SimpleDrawingView(this));
    }

    public class SimpleDrawingView extends View {

        private final int paintColor = Color.BLACK;

        private Paint drawPaint;

        private Path path = new Path();

        public SimpleDrawingView(Context context) {
            super(context);
            setFocusable(true);
            setFocusableInTouchMode(true);
            setupPaint();
        }

        private void setupPaint() {

            drawPaint = new Paint();
            drawPaint.setColor(paintColor);
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(5);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, drawPaint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float pointX = event.getX();
            float pointY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(pointX, pointY);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(pointX, pointY);
                    break;
                default:
                    return false;
            }

            postInvalidate();
            return true;
        }
    }

}
