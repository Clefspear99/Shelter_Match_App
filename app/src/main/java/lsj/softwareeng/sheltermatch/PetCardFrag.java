package lsj.softwareeng.sheltermatch;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PetCardFrag extends Fragment {

    private PetCardViewModel mViewModel;

    public static int totalHeight=-1;

    private View root;

    private PetObject petObject;

    private ArrayList<Bitmap> images= new ArrayList<>();

    private MainActivity ma;

    //public static PetCardFrag newInstance() {
        //return new PetCardFrag();
    //}


    public PetCardFrag(){}



    public PetCardFrag(PetObject petObject, MainActivity ma){
        this.petObject=petObject;
        this.ma = ma;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.pet_card_fragment, container, false);

        String sex="Error!";
        switch(petObject.getSex()){
            case 0:
                sex="unknown";
                break;
            case 1:
                sex="Male";
                break;
            case 2:
                sex="Female";
                break;
        }

        ImageButton imageButton = ((ImageButton) root.findViewById(R.id.petMainImage));

        if(petObject.getImgURLs().size()>0)
            new networkTask(images, imageButton).execute(petObject.getImgURLs().get(0));

        for(int i =1; i<petObject.getImgURLs().size(); i++)
            new networkTask(images).execute(petObject.getImgURLs().get(i));



        ((TextView) root.findViewById(R.id.petName)).setText(petObject.getName());
        ((TextView) root.findViewById(R.id.petCardInfo)).setText(petObject.getType()+"\n"+sex+"\n"+petObject.getCity());

        if(totalHeight==-1) {
            root.post(new Runnable() {
                @Override
                public void run() {
                    int measuredHeight=root.getMeasuredHeight();
                    if(measuredHeight!=0) {
                        totalHeight = measuredHeight;
                        totalHeight+=MainActivity.dpToPixel(20);
                    }
                }
            });
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.newPetInfo(petObject, images);
            }
        });



        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PetCardViewModel.class);
        // TODO: Use the ViewModel
    }




    public void setPetObject(PetObject petObject) {
        this.petObject = petObject;
    }

    public PetObject getPetObject() {
        return petObject;
    }

    public static int getTotalHeight(){
        return totalHeight;
    }
}


class networkTask extends AsyncTask<String, Void, Bitmap> {


    ImageButton imageButton;
    ArrayList<Bitmap> arr;
    Boolean setImageButton=false;

    public networkTask(ArrayList<Bitmap> arr){
        this.arr=arr;
    }

    public networkTask(ArrayList<Bitmap> arr, ImageButton imageButton){
        this.imageButton=imageButton;
        this.arr=arr;
        setImageButton=true;
    }

    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap=null;
        try{
            bitmap=getBitMapFromURL(params[0]);
        }
        catch(Exception e){
            Log.d("IMAGE_ERROR", e.toString());
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        arr.add(result);
        if(setImageButton)
            imageButton.setImageBitmap(result);
    }

    private Bitmap getBitMapFromURL(String url) throws MalformedURLException, IOException {
        URL myURL= new URL(url);
        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setDoInput(true);
        connection.connect();
        return BitmapFactory.decodeStream(connection.getInputStream());
    }
}