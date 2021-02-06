package com.prinzdarknis.basicbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    Book book;
    int index;
    EditText nameEditText;
    EditText descEditText;
    EditText isbnEditText;
    EditText imageEditText;

    static Intent createIntent(Context context) {
        return createIntent(context, null, -1);
    }

    static Intent createIntent(Context context, Book book, int index) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("com.prinzdarknis.basicbooks.BOOK", book);
        intent.putExtra("com.prinzdarknis.basicbooks.INDEX", index);
        return intent; // because of Result
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Views
        nameEditText = findViewById(R.id.nameEditText);
        descEditText = findViewById(R.id.descEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        imageEditText = findViewById(R.id.imageEditText);

        Button cancleBtn = findViewById(R.id.cancleBtn);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancle();
            }
        });
        Button deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        // activate Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get Data
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("com.prinzdarknis.basicbooks.BOOK");
        index = intent.getIntExtra("com.prinzdarknis.basicbooks.INDEX", -1);

        if (book != null) {
            // Write Data
            nameEditText.setText(book.name);
            descEditText.setText(book.description);
            isbnEditText.setText(book.isbn);
            imageEditText.setText(book.image_link);
        }
    }

    protected void save() {
        String name = nameEditText.getText().toString().trim();
        if (name.length() < 1) return;

        // Read Data
        if (book == null) book = new Book(name);
        else book.name = name;
        book.description = descEditText.getText().toString();
        book.isbn = isbnEditText.getText().toString();
        book.image_link = imageEditText.getText().toString();

        // send Data
        Intent resultData = new Intent();
        resultData.putExtra("com.prinzdarknis.basicbooks.BOOK", book);
        resultData.putExtra("com.prinzdarknis.basicbooks.INDEX", index);
        setResult(RESULT_OK, resultData);
        finish();
    }

    protected void delete() {
        Intent resultData = new Intent();
        resultData.putExtra("com.prinzdarknis.basicbooks.DELETE", true);
        resultData.putExtra("com.prinzdarknis.basicbooks.INDEX", index);
        setResult(RESULT_OK, resultData);
        finish();
    }

    protected void cancle() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
