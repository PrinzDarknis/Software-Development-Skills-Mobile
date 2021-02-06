package com.prinzdarknis.basicbooks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.view.Display;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Book implements Serializable {
    String name = "";
    String description = "";
    String image_link = null;
    String isbn;

    public Book (String name) {
        this.name = name;
    }

    public void setImage(ImageView imageView) {
        new DownloadAndScaleImageTask(imageView).execute(image_link);
    }

    static Book[] getDemoData(Context context) {
        String [] names = context.getResources().getStringArray(R.array.demo_names);
        String [] descriptions = context.getResources().getStringArray(R.array.demo_description);
        String [] image_links = context.getResources().getStringArray(R.array.demo_image);
        String [] isbns = context.getResources().getStringArray(R.array.demo_isbn);

        Book[] books = new Book[names.length];
        for (int i = 0; i < names.length; i++) {
           Book book = new Book(names[i]);
           book.description = descriptions[i];
           book.image_link = image_links[i];
           book.isbn = isbns[i];
           books[i] = book;
        }

        return books;
    }

    private class DownloadAndScaleImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView img;

        public DownloadAndScaleImageTask(ImageView imageView) {
            img = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Context context = img.getContext();
            Display screen = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            // open Stream (Source: https://stackoverflow.com/questions/2313148/imageview-setimageuri-does-not-work-when-trying-to-assign-a-r-drawable-x-uri)
            try {

                InputStream in = new URL(strings[0]).openStream();
                BufferedInputStream bin = new BufferedInputStream(in);
                bin.mark(0);
                BitmapFactory.decodeStream(bin, null, options);

                int imgWidth = options.outWidth;
                int screenWidth = screen.getWidth();

                if (imgWidth > screenWidth) {
                    int ratio = Math.round((float)imgWidth / (float)screenWidth);
                    options.inSampleSize = ratio; // set Scale
                }

                options.inJustDecodeBounds = false;

                bin.reset(); // otherwise bitmap is null
                Bitmap scaledImg = BitmapFactory.decodeStream(bin, null, options);
                return scaledImg;

            } catch (Exception e) {
                Log.e("Book:setImage", "couldn't load Image", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                img.setImageBitmap(result);
            }
        }
    }
}
