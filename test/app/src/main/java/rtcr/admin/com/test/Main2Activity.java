package rtcr.admin.com.test;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends ActionBarActivity {

    String popUpContents[];
    PopupWindow popupWindowApps;
    Button Training;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //MainActivity ma =new MainActivity();
       // ma.mainact();
        List<String> appList = new ArrayList<String>();
        appList.add("Facebook::1");
        appList.add("Whatsapp::2");
        appList.add("Twitter::3");
        appList.add("Youtube::4");
        appList.add("Instagram::5");

        popUpContents = new String[appList.size()];
        appList.toArray(popUpContents);

        popupWindowApps = popupWindowApps();

        View.OnClickListener handler = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.button3:
                        // show the list view as dropdown
                        popupWindowApps.showAsDropDown(v, -6, 0);

                        break;
                }
            }
        };

        // our button
        Training = (Button) findViewById(R.id.button3);
        Training.setOnClickListener(handler);

    }

    private PopupWindow popupWindowApps() {
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewDogs = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(appAdapter(popUpContents));

        // set the item click listener
        listViewDogs.setOnItemClickListener(new AppsDropdownOnItemClickListener());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(250);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);

        return popupWindow;
    }
    private ArrayAdapter<String> appAdapter(String dogsArray[]) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[1];

                // visual settings for the list item
                TextView listItem = new TextView(Main2Activity.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(22);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.WHITE);

                return listItem;
            }
        };

        return adapter;
    }
}
