package org.curiouslearning.videoplayertest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    private final static String TAG = "videoplayer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String moviesFolder = Environment.getExternalStorageDirectory() + "/Movies";

        int numberOfFiles = 0;
        for(final File file : new File(moviesFolder).listFiles())
        {
            Button myButton = new Button(this);
            myButton.setId(numberOfFiles++);
            myButton.setText(file.getName().replace(".wmv", "").replace(".mp4", "")
                    .replace("_", " "));
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try
                    {
                        sendLog("CL_VideoPlayer", file.getName());
                        playVideo(moviesFolder + "/" + file.getName());
                    }
                    catch(ActivityNotFoundException e)
                    {
                        Log.e(TAG, "Activity not found when trying to play a movie: " + e);
                    }
                }
            });

            LinearLayout ll = (LinearLayout)findViewById(R.id.layout1);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
        }
    }

    public static final String DATABASE_NAME_KEY = "DATABASE_NAME";
    public static final String NAME_KEY = "NAME";
    public static final String VALUE_KEY = "VALUE";
    public static final String TIMESTAMP_KEY = "TIMESTAMP";
    public static final String ACTION_RECORD = "edu.mit.media.funf.RECORD";
    public void sendLog(String name, String value) {
        Intent i = new Intent();
        i.setAction(ACTION_RECORD);
        Bundle b = new Bundle();
        b.putString(DATABASE_NAME_KEY, "mainPipeline");
        b.putLong(TIMESTAMP_KEY, System.currentTimeMillis()/1000);
        b.putString(NAME_KEY, name);
        b.putString(VALUE_KEY, value);
        i.putExtras(b);
        sendBroadcast(i);
        Log.i(TAG, "Funf Record: " + name + " = " + value);
    }

    private void playVideo(String movieToPlay)
    {
        Intent moviePlayerIntent = new Intent(Intent.ACTION_VIEW);
        moviePlayerIntent.setDataAndType(Uri.parse(movieToPlay), "video/mp4");
        startActivity(moviePlayerIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
