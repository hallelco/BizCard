package com.example.myapplication;

import android.content.ActivityNotFoundException;
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
    private Button buttonBack,callButton,openWebsite,shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cardactivity);
        Intent cIntent = getIntent();
        secondTextText = findViewById(R.id.secondTextText);
        getName = findViewById(R.id.getName);
        getRole = findViewById(R.id.getRole);
        getPhone = findViewById(R.id.getPhone);
        getWebsite = findViewById(R.id.getWebsite);

        callButton = findViewById(R.id.callButton);
        openWebsite = findViewById(R.id.openWebsite);
        shareButton = findViewById(R.id.shareButton);
        buttonBack = findViewById(R.id.buttonBack);

        String gotName = getIntent().getStringExtra(MainActivity.KEY_NAME);
        getName.setText(gotName);
        String gotRole = getIntent().getStringExtra(MainActivity.KEY_ROLE);
        getRole.setText(gotRole);
        String gotPhone = getIntent().getStringExtra(MainActivity.KEY_PHONE);
        getPhone.setText(gotPhone);
        String gotWebsite = getIntent().getStringExtra(MainActivity.KEY_WEBSITE);
        getWebsite.setText(gotWebsite);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + gotPhone));
                    startActivity(intent);
                }
                catch (ActivityNotFoundException exception){
                    toast("לא נמצאה אפליקציה לביצוע שיחה");
                }
            }
        });

        openWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String site = gotWebsite;
                    if (!gotWebsite.toLowerCase().startsWith("http")){
                        site = "https://" + gotWebsite;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
                    startActivity(intent);
                } catch (ActivityNotFoundException exception){
                    toast("לא נמצאה אפליקציה לפתיחת האתר");
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,
                            gotName + "\n" + gotRole + "\n" + gotPhone + "\n" + gotWebsite);
                    startActivity(Intent.createChooser(intent, "שתף כרטיס ביקור"));
                } catch (ActivityNotFoundException exception){
                    toast("לא נמצאה אפליקציה לשיתוף");
                }
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}