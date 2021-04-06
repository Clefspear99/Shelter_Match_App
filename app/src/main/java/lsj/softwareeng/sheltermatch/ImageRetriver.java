package lsj.softwareeng.sheltermatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageRetriver extends AsyncTask<String, Void, Bitmap> {


    ImageButton imageButton;
    LinearLayout lLayout;
    ImageView iV;
    ArrayList<Bitmap> arr;
    Boolean setImageButton = false;
    Boolean addToLinearLayout = false;

    public ImageRetriver(ArrayList<Bitmap> arr) {
        this.arr = arr;
    }

    public ImageRetriver(ArrayList<Bitmap> arr, ImageButton imageButton) {
        this.imageButton = imageButton;
        this.arr = arr;
        setImageButton = true;
    }

    public ImageRetriver(ArrayList<Bitmap> arr, LinearLayout lLin, ImageView iV) {
        this.lLayout = lLin;
        this.arr = arr;
        this.iV=iV;
        addToLinearLayout = true;
    }


    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            bitmap = getBitMapFromURL(params[0]);
        } catch (Exception e) {
            Log.d("IMAGE_ERROR", e.toString());
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        arr.add(result);
        if (setImageButton)
            imageButton.setImageBitmap(result);

        if (addToLinearLayout)
            PetInfoFragment.loadImage(lLayout, iV, result);
    }

    private Bitmap getBitMapFromURL(String url) throws MalformedURLException, IOException {
        URL myURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setDoInput(true);
        connection.connect();
        return BitmapFactory.decodeStream(connection.getInputStream());
    }
}

