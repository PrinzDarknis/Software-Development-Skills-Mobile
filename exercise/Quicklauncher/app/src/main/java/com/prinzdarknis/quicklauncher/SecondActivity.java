package com.prinzdarknis.quicklauncher;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get Extra information
        if (getIntent().hasExtra("com.prinzdarknis.quicklauncher.SOMETHING")) {
            TextView tv = findViewById(R.id.textView);
            String text = getIntent().getStringExtra("com.prinzdarknis.quicklauncher.SOMETHING");
            tv.setText(text);
        }
    }
}
