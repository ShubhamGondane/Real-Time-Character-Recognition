package rtcr.admin.com.test;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import rtcr.admin.com.test.Main2Activity;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by B D Gondane on 18-11-2016.
 */
public class AppsDropdownOnItemClickListener  implements AdapterView.OnItemClickListener {
    String selectedItemTag;
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

        // get the context and main activity to access variables
        Context mContext = v.getContext();
        Main2Activity mainActivity = ((Main2Activity) mContext);

        // add some animation when a list item was clicked
        Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
        fadeInAnimation.setDuration(10);
        v.startAnimation(fadeInAnimation);

        // dismiss the pop up
        mainActivity.popupWindowApps.dismiss();

        // get the text and set it as the button text
        String selectedItemText = ((TextView) v).getText().toString();
        mainActivity.Training.setText(selectedItemText);

        // get the id
        selectedItemTag = ((TextView) v).getTag().toString();
        Toast.makeText(mContext, "App ID is: " + selectedItemTag, Toast.LENGTH_SHORT).show();
        MainActivity ma=new MainActivity();
        ma.setAppId(Integer.parseInt( selectedItemTag));

        //new MainActivity().setAppId(Integer.parseInt( selectedItemTag));;
        Intent I = new Intent(mContext, MainActivity.class);
        Log.d("Tag id:",selectedItemTag);
        I.putExtra("App Id", selectedItemTag);
        mContext.startActivity(I);







    }



}

