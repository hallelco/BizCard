package com.example.myapplication;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConfig {

    // ↓↓↓ מעתיקים את שלושת הערכים מ-FirebaseConfig.java של פרויקט פרק 11 ↓↓↓
    public static final String API_KEY = "AIzaSyCLIxzQ9xlxI-YtHmvVpyX263Xv_G2_sJ4";
    public static final String APPLICATION_ID = "1:728751598151:android:43823433bf02fc85f25f62";
    public static final String DATABASE_URL = "https://bizcard-238d5-default-rtdb.firebaseio.com/";
    // ↑↑↑ בלי רווחים, ובתוך המרכאות ↑↑↑

    // האם מילאו את שלושת הערכים?
    public static boolean isConfigured() {
        return !API_KEY.isEmpty() && !APPLICATION_ID.isEmpty() && !DATABASE_URL.isEmpty();
    }

    // מחבר את האפליקציה ל-Firebase. מספיק פעם אחת: בפעם השנייה יוצאים מיד
    public static void ensureInitialized(Context context) {
        if (!FirebaseApp.getApps(context).isEmpty()) {
            return;                                     // כבר מחובר
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey(API_KEY)
                .setApplicationId(APPLICATION_ID)
                .setDatabaseUrl(DATABASE_URL)
                .build();
        FirebaseApp.initializeApp(context.getApplicationContext(), options);
    }
}
