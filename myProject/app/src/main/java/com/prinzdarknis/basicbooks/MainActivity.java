package com.prinzdarknis.basicbooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    static final int EDIT_REQUEST = 5;
    static final int CREATE_REQUEST = 6;

    ListView bookListView;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activate Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // set ListAdapter
        bookListView = findViewById(R.id.bookListView);
        adapter = new BookAdapter(this, Book.getDemoData(this));
        bookListView.setAdapter(adapter);

        //set listener
        final Context context = this;

        bookListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = EditActivity.createIntent(context, (Book) adapter.getItem(position), position);
                startActivityForResult(intent, EDIT_REQUEST);
                return true; // handled
            }
        });

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailActivity.openActivity(context, (Book) adapter.getItem(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // Add new Book
                Intent intent = EditActivity.createIntent(this);
                startActivityForResult(intent, CREATE_REQUEST);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            Book book;
            switch (requestCode) {
                case EDIT_REQUEST:
                    book = (Book) data.getSerializableExtra("com.prinzdarknis.basicbooks.BOOK");
                    int index = data.getIntExtra("com.prinzdarknis.basicbooks.INDEX", -1);
                    boolean del = data.getBooleanExtra("com.prinzdarknis.basicbooks.DELETE", false);
                    if (book != null) adapter.save(index, book);
                    else if (del) adapter.delete(index);
                    break;
                case CREATE_REQUEST:
                    book = (Book) data.getSerializableExtra("com.prinzdarknis.basicbooks.BOOK");
                    if (book != null) adapter.save(-1, book);
                    break;
            }
        }
    }
}
