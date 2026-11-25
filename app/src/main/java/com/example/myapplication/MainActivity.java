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
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity {
    private TextView cardText;
    private String editKey = null;
    private EditText etname, etrole, etphone, etwebsite;
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
        etname = findViewById(R.id.etname);
        etrole = findViewById(R.id.etrole);
        etphone = findViewById(R.id.etphone);
        etwebsite = findViewById(R.id.etwebsite);
        createButton = findViewById(R.id.createButton);

        // בדיקה אם הטופס נפתח במצב עריכה
        editKey = getIntent().getStringExtra("key");

        if (editKey != null) {
            etname.setText(getIntent().getStringExtra(KEY_NAME));
            etrole.setText(getIntent().getStringExtra(KEY_ROLE));
            etphone.setText(getIntent().getStringExtra(KEY_PHONE));
            etwebsite.setText(getIntent().getStringExtra(KEY_WEBSITE));

            createButton.setText("שמור שינויים");
            Toast.makeText(this, "עריכת כרטיס", Toast.LENGTH_SHORT).show();
        }

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard();          // כל העבודה עברה למתודה אחת
            }
        });
    }

    private void saveCard() {

        String name  = etname.getText().toString().trim();
        String role   = etrole.getText().toString().trim();
        String phone = etphone.getText().toString().trim();
        String site  = etwebsite.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "חובה למלא שם", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this,
                "Firebase configured: " + FirebaseConfig.isConfigured(),
                Toast.LENGTH_LONG).show();

        if (!FirebaseConfig.isConfigured()) {
            Toast.makeText(this, "חסרים ערכים ב-FirebaseConfig.java", Toast.LENGTH_LONG).show();
            return;
        }

        final Card card = new Card(name, role, phone, site);
        DatabaseReference ref = CardsDb.cards(this);
        Toast.makeText(this,
                "Firebase reference: " + ref,
                Toast.LENGTH_LONG).show();
        if (editKey == null) {

            String key = ref.push().getKey();

            if (key == null) {
                Toast.makeText(this, "שגיאה ביצירת מפתח", Toast.LENGTH_SHORT).show();
                return;
            }
            card.setKey(key);
            ref.child(key).setValue(card)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(MainActivity.this, "נשמר בענן", Toast.LENGTH_SHORT).show();
                    clearForm();
                    openCard(card);
                }
                     })
                    .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(MainActivity.this, "השמירה נכשלה: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            // ===== עדכון (Update) =====
            ref.child(editKey).setValue(card)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivity.this,
                                    "הכרטיס עודכן", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,
                                    "העדכון נכשל: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void openCard(Card card) {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("key", card.getKey());
        intent.putExtra(KEY_NAME, card.getName());
        intent.putExtra(KEY_ROLE, card.getRole());
        intent.putExtra(KEY_PHONE, card.getPhone());
        intent.putExtra(KEY_WEBSITE, card.getSite());

        startActivity(intent);
    }

    // מרוקן את ארבעת השדות, כדי שאפשר יהיה להקליד מיד כרטיס חדש
    private void clearForm() {
        etname.setText("");
        etrole.setText("");
        etphone.setText("");
        etwebsite.setText("");
        etname.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            if(etname.getText().equals("") && etrole.getText().equals("") && etphone.getText().equals("") && etwebsite.getText().equals("")){
                Toast.makeText(this, "הטופס כבק ריק", Toast.LENGTH_SHORT).show();
            }
            else{
                etname.setText("");
                etrole.setText("");
                etphone.setText("");
                etwebsite.setText("");
                Toast.makeText(this, "הטופס נוקה", Toast.LENGTH_SHORT).show();
                return true;
            }

        }
        else if (item.getItemId() == R.id.action_fill){
            etname.setText("שחר אבני");
            etrole.setText("סופרת");
            etphone.setText("0525381648");
            etwebsite.setText("ort.org.il");
            Toast.makeText(this, "מוצגת דוגמא", Toast.LENGTH_SHORT).show();
            return true;
        }

        else if (item.getItemId() == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_list) {
            startActivity(new Intent(this, CardsListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}