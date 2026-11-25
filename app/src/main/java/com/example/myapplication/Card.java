package com.example.myapplication;

import com.google.firebase.database.Exclude;

public class Card {

    // ----- 4 השדות שנשמרים בענן -----
    private String name;
    private String role;
    private String phone;
    private String site;

    // ----- הכתובת של הכרטיס בעץ. לא נשמרת כשדה! -----
    private String key;

    // בנאי ריק: חובה! Firebase בונה דרכו את העצם כשהוא קורא מהמסד
    public Card() {
    }

    // בנאי נוח: בשבילנו, כשאנחנו יוצרים כרטיס מהטופס
    public Card(String name, String role, String phone, String site) {
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.site = site;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role ;}

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    // @Exclude = "אל תשמור את זה בעץ"
    @Exclude
    public String getKey() { return key; }

    @Exclude
    public void setKey(String key) { this.key = key; }

    // מה שיופיע בשורה של הרשימה (ArrayAdapter קורא ל-toString)
    @Override
    public String toString() {
        return name + " · " + role;
    }
}