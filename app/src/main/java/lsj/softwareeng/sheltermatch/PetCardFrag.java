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

    public static int totalHeight = -1;

    private View root;

    private PetObject petObject;


    private MainActivity ma;

    //public static PetCardFrag newInstance() {
    //return new PetCardFrag();
    //}


    public PetCardFrag() {
    }


    public PetCardFrag(PetObject petObject, MainActivity ma) {
        this.petObject = petObject;
        this.ma = ma;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pet_card_fragment, container, false);

        String sex = "Error!";
        switch (petObject.getSex()) {
            case 0:
                sex = "unknown";
                break;
            case 1:
                sex = "Male";
                break;
            case 2:
                sex = "Female";
                break;
        }

        ImageButton imageButton = ((ImageButton) root.findViewById(R.id.petMainImage));

        if (petObject.getImgURLs().size() > 0) {
            //new ImageRetriver(petObject.getImages(), imageButton).execute(petObject.getImgURLs().get(0));
            new ImageRetriver(petObject.getImages(), imageButton).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, petObject.getImgURLs().get(0));
        }




        ((TextView) root.findViewById(R.id.petName)).setText(petObject.getName());
        ((TextView) root.findViewById(R.id.petCardInfo)).setText(petObject.getType() + "\n" + sex + "\n" + petObject.getCity());

        if (totalHeight == -1) {
            root.post(new Runnable() {
                @Override
                public void run() {
                    int measuredHeight = root.getMeasuredHeight();
                    if (measuredHeight != 0) {
                        totalHeight = measuredHeight;
                        totalHeight += MainActivity.dpToPixel(20);
                    }
                }
            });
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageButton.getDrawable()!=null)
                    ma.newPetInfo(petObject);
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


