package com.example.myapplication;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        setTitle(R.string.main_screen);
        name = findViewById(R.id.name);
        role = findViewById(R.id.role);
        phone = findViewById(R.id.phone);
        website = findViewById(R.id.website);
        createButton = findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                String nameText = name.getText().toString();
                String roleText = role.getText().toString();
                String phoneText = phone.getText().toString();
                String websiteText = website.getText().toString();

                if (nameText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "יש להזין שם מלא", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (roleText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "יש להזין תפקיד", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "יש להזין מספר טלפון", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (websiteText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "יש להזין כתובת אתר", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra(KEY_NAME, nameText);
                intent.putExtra(KEY_ROLE, roleText);
                intent.putExtra(KEY_PHONE, phoneText);
                intent.putExtra(KEY_WEBSITE, websiteText);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            if(name.getText().equals("") && role.getText().equals("") && phone.getText().equals("") && website.getText().equals("")){
                Toast.makeText(this, "הטופס כבק ריק", Toast.LENGTH_SHORT).show();
            }
            name.setText("");
            role.setText("");
            phone.setText("");
            website.setText("");
            Toast.makeText(this, "הטופס נוקה", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(item.getItemId() == R.id.action_fill){
            name.setText("שחר אבני");
            role.setText("סופרת");
            phone.setText("0525381648");
            website.setText("ort.org.il");
            Toast.makeText(this, "מוצגת דוגמא", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}