package com.prinzdarknis.basicbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    static void openActivity(Context context, Book book) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("com.prinzdarknis.basicbooks.BOOK", book);
        context.startActivity(intent);
    }

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // activate Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get Data
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("com.prinzdarknis.basicbooks.BOOK");
        if (book != null) {
            finishActivity(Activity.RESULT_CANCELED); // Close Activity
        }

        // Show Data
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView descTextView = findViewById(R.id.descTextView);
        TextView isbnTextView = findViewById(R.id.isbnTextView);
        ImageView imageView = findViewById(R.id.imageView);

        nameTextView.setText(book.name);
        descTextView.setText(book.description);
        isbnTextView.setText(book.isbn);
        book.setImage(imageView);
    }
}
