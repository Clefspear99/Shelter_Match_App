package lsj.softwareeng.sheltermatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


public class PetInfoFragment extends Fragment {


    private View root;
    private FragmentActivity context;

    private int currPet = -1;

    public PetInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_pet_info, container, false);

        return root;
    }

    public void setupForPet(PetObject petIn) {
        if (currPet == petIn.getId())
            return;

        TextView infoTV = root.findViewById(R.id.pet_info_fragment_textView);
        LinearLayout imageLL = root.findViewById(R.id.pet_info_fragment_linearLayout);
        HorizontalScrollView horizontalScrollView = root.findViewById(R.id.horz_scroll_view);

        imageLL.removeAllViews();
        horizontalScrollView.setScrollX(0);

        String text = "Name: " + petIn.getName() + "\n" +
                "Age: " + petIn.getAge() + "\n";
        infoTV.setText(text);


        loadImage(imageLL, new ImageView(context), petIn.getImages().get(0));

        if (!petIn.allImagesLoaded) {
            for (int i = 1; i < petIn.getImgURLs().size(); i++) {
                new ImageRetriver(petIn.images, imageLL, new ImageView(context)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, petIn.getImgURLs().get(i));
            }
            petIn.allImagesLoaded = true;
        }else{
            for(int i =1; i<petIn.getImages().size(); i++)
                loadImage(imageLL, new ImageView(context), petIn.getImages().get(i));
        }





        currPet = petIn.getId();

    }

    public void onAttach(Context contextIn) {
        this.context = (FragmentActivity) contextIn;
        super.onAttach(contextIn);
    }

    public static void loadImage(LinearLayout lLin, ImageView iV, Bitmap image){
        iV.setImageBitmap(image);
        iV.setAdjustViewBounds(true);
        iV.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iV.setMaxHeight(MainActivity.dpToPixel(300));
        iV.setMaxWidth(MainActivity.dpToPixel(350));
        iV.setPadding(MainActivity.dpToPixel(20), 0, MainActivity.dpToPixel(20), 0);
        lLin.addView(iV);
    }

}
