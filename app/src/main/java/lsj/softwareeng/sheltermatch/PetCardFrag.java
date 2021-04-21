package lsj.softwareeng.sheltermatch;

import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lsj.softwareeng.sheltermatch.ui.fav.FavFragment;

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

    public FragmentContainerView container;

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



        ImageButton imageButton = ((ImageButton) root.findViewById(R.id.petMainImage));

        if (petObject.getImgURLs().size() > 0) {
            if(petObject.images.size()<1)
                new ImageRetriver(petObject.getImages(), imageButton).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, petObject.getImgURLs().get(0));
            else{
                imageButton.setImageBitmap(petObject.getImages().get(0));
            }
        }




        ((TextView) root.findViewById(R.id.petName)).setText(petObject.getName());
        ((TextView) root.findViewById(R.id.petCardInfo)).setText(petObject.getType() + "\n" + petObject.getSex() + "\n" + petObject.getCity());

        if(petObject!=null)
            changeFavColor();

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

        ImageButton favButton = root.findViewById(R.id.pet_card_fav);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(petObject.getFav()==false)
                    petObject.setFav(true);
                else
                    petObject.setFav(false);
                petObject.addRemoveFromFav(ma);
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

    public void changeFavColor(){
        ImageButton button= root.findViewById(R.id.pet_card_fav);
        if(petObject.getFav())
            button.setImageResource(R.drawable.ic_fav_red_24dp);
        else
            button.setImageResource(R.drawable.ic_fav_black_24dp);
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

    public FragmentContainerView getContainer() {
        return container;
    }

    public void setContainer(FragmentContainerView container) {
        this.container = container;
    }
}


