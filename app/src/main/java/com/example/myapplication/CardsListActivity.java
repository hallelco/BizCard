package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CardsListActivity extends AppCompatActivity {

    private ListView lvCards;
    private TextView tvCount;

    private ArrayList<Card> cards = new ArrayList<>();   // הכרטיסים בזיכרון
    private ArrayAdapter<Card> adapter;                  // מחבר בין הרשימה ל-ListView

    private DatabaseReference ref;                       // הצומת cards
    private ValueEventListener listener;                 // שומרים אותו כדי להסיר בסוף

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_list);
        setTitle("הכרטיסים שלי");

        lvCards = findViewById(R.id.lvCards);
        tvCount = findViewById(R.id.tvCount);

        if (cards.isEmpty()) {
            tvCount.setText("אין עדיין כרטיסים. צרו את הראשון!");
        } else {
            tvCount.setText("מספר הכרטיסים: " + cards.size());
        }

        // ➕ כרטיס חדש: סוגרים את הרשימה וחוזרים לטופס (הוא מתחת לרשימה, והוא כבר ריק)
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cards);
        lvCards.setAdapter(adapter);

        if (!FirebaseConfig.isConfigured()) {
            tvCount.setText("חסרים ערכים ב-FirebaseConfig.java");
            return;
        }

        ref = CardsDb.cards(this);

        // ===== קריאה (Read) =====
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // TODO 4: ריקון הרשימה
                cards.clear();

                // TODO 5: מעבר על כל הילדים של snapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    Card card = child.getValue(Card.class);

                    if (card != null) {
                        card.setKey(child.getKey());
                        cards.add(card);
                    }
                }

                // TODO 6: עדכון המתאם ומספר הכרטיסים
                adapter.notifyDataSetChanged();
                tvCount.setText("מספר הכרטיסים: " + cards.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CardsListActivity.this,
                        "הקריאה נכשלה: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        // TODO 7: הרשמה להאזנה לשינויים בצומת ref
        ref.addValueEventListener(listener);

        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO 9: קבלת הכרטיס שנבחר מהרשימה
                Card card = cards.get(position);

                // יצירת Intent למסך CardActivity
                Intent intent = new Intent(CardsListActivity.this, CardActivity.class);

                // העברת חמשת הנתונים של הכרטיס
                intent.putExtra("key", card.getKey());
                intent.putExtra(MainActivity.KEY_NAME, card.getName());
                intent.putExtra(MainActivity.KEY_ROLE, card.getRole());
                intent.putExtra(MainActivity.KEY_PHONE, card.getPhone());
                intent.putExtra(MainActivity.KEY_WEBSITE, card.getSite());

                // פתיחת המסך
                startActivity(intent);
            }
        });

        lvCards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = cards.get(position);
                askDelete(card);
                Toast.makeText(CardsListActivity.this,"טיפלתי בלחיצה",Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    private void askDelete(final Card card) {
        new AlertDialog.Builder(this)
                .setTitle("מחיקת כרטיס")
                .setMessage("למחוק את הכרטיס של " + card.getName() + "?")
                .setPositiveButton("מחק", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.child(card.getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CardsListActivity.this,
                                                "נמחק", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .setNegativeButton("ביטול", null)
                .show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO 8: ביטול ההרשמה של listener
        if (ref != null && listener != null) {
            ref.removeEventListener(listener);
        }
    }
}