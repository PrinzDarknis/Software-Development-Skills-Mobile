package com.prinzdarknis.quicklauncher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attempt to launch an activity within our own app
        Button secondActivityBtn = findViewById(R.id.secondActivityBtn);
        secondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);

                // show how to pass information to another activity
                startIntent.putExtra("com.prinzdarknis.quicklauncher.SOMETHING", "HELLO WORLD!");

                startActivity(startIntent);
            }
        });

        // Attempt to launch an activity outside our app
        Button googleBtn = findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String google = "http://www.google.com";
                Uri webaddress = Uri.parse(google);

                Intent goToGoogle = new Intent(Intent.ACTION_VIEW, webaddress); // Command open the URI
                if (goToGoogle.resolveActivity(getPackageManager()) != null) { // Exists an App that can do this
                    startActivity(goToGoogle);
                }
            }
        });
    }
}
