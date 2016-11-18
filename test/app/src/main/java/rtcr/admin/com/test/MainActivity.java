package rtcr.admin.com.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/*import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;*/

//import static org.opencv.core.Core.getOptimalDFTSize;

public class MainActivity extends Activity implements OnClickListener{
    View mView;
    Button clearbutton;
    String text;
    String text1;
    int time=0;
    int sampleNo=0,appid;
    String pixel[]=new String[1000];
    int pixelCount=0;

    StringBuilder s=new StringBuilder(1000);
    private Context context;
    Button submit;
    private Paint mPaint;
    StringBuffer sbuffer = new StringBuffer();
    Double coord[][] = new Double[1000][2];
    int i=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //Button clear=(Button) findViewById(R.id.button);


    LinearLayout layout;

   /* static {
        if(!OpenCVLoader.initDebug()) {
            Log.d("ERROR", "Unable to load OpenCV");
        } else {
            Log.d("SUCCESS", "OpenCV loaded");
        }
    }*/
    void setAppId(int ii)
    {
        appid=ii;

        Log.d("SET appid:",String.valueOf(appid));

    }
    public void onClick(View v)
    {

        layout.removeAllViews();
        mView = new DrawingView(this);
        layout.addView(mView, new LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        init();
        appIdentify();
        /*Mat mat=new Mat(2,2, CvType.CV_32F);

        mat.put(1,1,1);
        mat.put(0,0,25);
        int m=getOptimalDFTSize(mat.rows());
        int n=getOptimalDFTSize(mat.cols());

        Log.d("mat check", mat.dump()+","+m+","+n);

        Core.dft(mat, mat);
        Log.d("mat dft check", mat.dump());*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Main2Activity ma2=new Main2Activity();
        layout = (LinearLayout) findViewById(R.id.myDrawing1);
        mView = new DrawingView(this);
        layout.addView(mView, new LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //MainActivity.context=getApplicationContext();
        context = this;
        submit= (Button) findViewById(R.id.button);
        clearbutton= (Button) findViewById(R.id.button2);
        clearbutton.setOnClickListener(this);

        init();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        /*if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }*/



    }
    public void appIdentify()
    {
      //  appid=id;
      //  Log.d("App id:", String.valueOf(appid));
        //onCreate();

            String value = getIntent().getExtras().getString("App Id");
            appid=Integer.parseInt(value);
            //The key argument here must match that used in the other activity

        String app="com.google.android.talk";
        Log.d("appid:",String.valueOf(appid));
        switch(appid)
        {
            case 1:
                app="com.facebook.katana";
                break;
            case 2:
                app="com.whatsapp";
                break;
            case 3:
                app="com.twitter.android";
                break;
            case 4:
                app="com.google.android.youtube";
                break;
            case 5:
                app="com.instagram.android";
                break;
        }


        Intent LaunchIntent =     getPackageManager().getLaunchIntentForPackage(app);
        startActivity(LaunchIntent);
    }



    private void init() {
        time=0;
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(0xff000000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        pixel=new String[1000];
        pixelCount=0;
        if(sampleNo==5)
        {
            sampleNo=0;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://rtcr.admin.com.test/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://rtcr.admin.com.test/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
            Float x1,y1,x2,y2;
            mCanvas.drawPath(path, mPaint);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x1=event.getX();
                y1=event.getY();
                time++;
                text=x1+","+y1+","+time;
                s.append(text);
                s.append("\n");

                pixel[pixelCount]=text;
                pixelCount++;

                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(event.getX(), event.getY());
                x1=event.getX();
                y1=event.getY();
                text1=x1+","+y1+","+time;

                pixel[pixelCount]=text1;
                pixelCount++;


                //text1.concat("\n"+text1);
                s.append(text1);
                s.append("\n");
                pp.setPath(path);
                pp.setmPaint(mPaint);
                _graphics1.add(pp);
            }

            invalidate();

            submit.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    // write on text file
                    try {
                        File myFile = new File(Environment.getExternalStorageDirectory().toString()+"/data.txt");

                        if(!myFile.exists())
                        {
                            myFile.createNewFile();

                        }
                        FileWriter fw =new FileWriter(myFile,true);
                        //fw.append(text);
                        //fw.append(text1);
                        fw.append("=======\n");

                        fw.append(s);
                        fw.flush();
                        fw.close();
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                        Dft dft=new Dft();
                        dft.normalize(MainActivity.this,pixel,pixelCount,sampleNo);
                        sampleNo++;

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
