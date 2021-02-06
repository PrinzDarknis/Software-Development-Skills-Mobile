package com.prinzdarknis.basicbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class BookAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    ArrayList<Book> books = new ArrayList<Book>();

    public BookAdapter(Context context, Book[] books) {
        Collections.addAll(this.books, books);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return books.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) { // create only if not exist
            convertView = mInflater.inflate(R.layout.list_item, null);
            TagLoad tagLoad = new TagLoad();
            tagLoad.nameTextView = convertView.findViewById(R.id.nameTextView);
            tagLoad.descTextView = convertView.findViewById(R.id.descTextView);
            tagLoad.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(tagLoad);
        }

        // Set Data
        TagLoad tagLoad = (TagLoad) convertView.getTag();
        tagLoad.nameTextView.setText(books.get(position).name);
        tagLoad.descTextView.setText(books.get(position).description);
        books.get(position).setImage(tagLoad.imageView);

        return convertView;
    }

    public void delete(int position) {
        books.remove(position);
        notifyDataSetChanged();
    }

    public void save(int position, Book book) {
        if (position >= 0) books.set(position, book);
        else books.add(book);
        notifyDataSetChanged();
    }

    private class TagLoad {
        TextView nameTextView;
        TextView descTextView;
        ImageView imageView;
    }
}
