package com.tender.team08.cs246.tender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayTherapistActivity extends AppCompatActivity {

    private Therapist you;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_therapist);
        you = (Therapist) getIntent().getSerializableExtra("Therapist");

        TextView textView = findViewById(R.id.FullNameView);
        textView.setText(you.getFullName());
        textView = findViewById(R.id.FullAddressView);
        textView.setText(you.getAddress());
        textView = findViewById(R.id.PhoneView);
        textView.setText(you.getPhone());
        textView = findViewById(R.id.EmailView);
        textView.setText(you.getEmail());
    }

    public void goBack(View view) {
        finish();
    }
}
