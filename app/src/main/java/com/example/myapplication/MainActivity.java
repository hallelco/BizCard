package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView cardText;
    private EditText name, role, phone, website;
    private Button createButton, odotButton;
    public static final String KEY_NAME = "name";
    public static final String KEY_ROLE = "role";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_WEBSITE = "website";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cardText = findViewById(R.id.cardText);
        name = findViewById(R.id.name);
        role = findViewById(R.id.role);
        phone = findViewById(R.id.phone);
        website = findViewById(R.id.website);
        createButton = findViewById(R.id.createButton);
        odotButton = findViewById(R.id.odotButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                String nameText = name.getText().toString();
                String roleText = role.getText().toString();
                String phoneText = phone.getText().toString();
                String websiteText = website.getText().toString();

                if(nameText.isEmpty()){
                    Toast.makeText(MainActivity.this, "יש להזין שם מלא", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(roleText.isEmpty()){
                    Toast.makeText(MainActivity.this, "יש להזין תפקיד", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneText.isEmpty()){
                    Toast.makeText(MainActivity.this, "יש להזין מספר טלפון", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(websiteText.isEmpty()){
                    Toast.makeText(MainActivity.this, "יש להזין כתובת אתר", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra(KEY_NAME,nameText);
                intent.putExtra(KEY_ROLE, roleText);
                intent.putExtra(KEY_PHONE, phoneText);
                intent.putExtra(KEY_WEBSITE, websiteText);
                startActivity(intent);
            }
        });

        odotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

    }
}