package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {
    private TextView secondTextText, getName, getRole, getPhone, getWebsite;
    private Button buttonBack;
    private String gotName, gotRole, gotPhone, gotWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cardactivity);
        setTitle(R.string.card_screen);
        Intent cIntent = getIntent();
        buttonBack = findViewById(R.id.buttonBack);
        getName = findViewById(R.id.getName);
        getRole = findViewById(R.id.getRole);
        getPhone = findViewById(R.id.getPhone);
        getWebsite = findViewById(R.id.getWebsite);

        gotName = getIntent().getStringExtra(MainActivity.KEY_NAME);
        getName.setText(gotName);
        gotRole = getIntent().getStringExtra(MainActivity.KEY_ROLE);
        getRole.setText(gotRole);
        gotPhone = getIntent().getStringExtra(MainActivity.KEY_PHONE);
        getPhone.setText(gotPhone);
        gotWebsite = getIntent().getStringExtra(MainActivity.KEY_WEBSITE);
        getWebsite.setText(gotWebsite);

        buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_dial)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + gotPhone));
                startActivity(intent);
            }
            catch (ActivityNotFoundException exception) {
                toast("לא נמצאה אפליקציה לביצוע שיחה");
            }
            return true;
        }
        else if(item.getItemId() == R.id.action_share)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        gotName + "\n" + gotRole + "\n" + gotPhone + "\n" + gotWebsite);
                startActivity(Intent.createChooser(intent, "שתף כרטיס ביקור"));
            }
            catch (ActivityNotFoundException exception) {
                toast("לא נמצאה אפליקציה לשיתוף");
            }
            return true;
        }

        else if (item.getItemId() == R.id.action_web) {
            try {
                String site = gotWebsite;
                if (!gotWebsite.toLowerCase().startsWith("http")){
                    site = "https://" + gotWebsite;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                startActivity(intent);
            }
            catch (ActivityNotFoundException exception) {
                toast("לא נמצאה אפליקציה לפתיחת האתר");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String toast)
    {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}