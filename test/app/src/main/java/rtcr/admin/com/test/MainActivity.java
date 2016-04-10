package rtcr.admin.com.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    View mView;

    private Paint mPaint;
    //Button clear=(Button) findViewById(R.id.button);

//    Button submit= (Button) findViewById(R.id.button2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.myDrawing1);
        mView = new DrawingView(this);
        layout.addView(mView, new LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        init();



    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(0xff000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
    }

    class DrawingView extends View {
        private Path path;
        private Bitmap mBitmap;
        private Canvas mCanvas;

        public DrawingView(Context context) {
            super(context);
            path = new Path();
            mBitmap = Bitmap.createBitmap(820, 400, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            this.setBackgroundColor(Color.WHITE);
        }

        private ArrayList<PathWithPaint> _graphics1 = new ArrayList<PathWithPaint>();

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            PathWithPaint pp = new PathWithPaint();
            mCanvas.drawPath(path, mPaint);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(event.getX(), event.getY());
                pp.setPath(path);
                pp.setmPaint(mPaint);
                _graphics1.add(pp);
            }
            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (_graphics1.size() > 0) {
                canvas.drawPath(
                        _graphics1.get(_graphics1.size() - 1).getPath(),
                        _graphics1.get(_graphics1.size() - 1).getmPaint());
            }

        }



    }
}
