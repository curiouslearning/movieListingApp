package org.curiouslearning.videoplayertest;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;


public class MainActivity extends ActionBarActivity {

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
                    playVideo(moviesFolder + "/" + file.getName());
                }
            });

            LinearLayout ll = (LinearLayout)findViewById(R.id.layout1);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
        }
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
