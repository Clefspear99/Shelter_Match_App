package lsj.softwareeng.sheltermatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class PetInfoFragment extends Fragment {


    private View root;
    private FragmentActivity context;

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

    public void setupForPet(PetObject petIn, ArrayList<Bitmap> imageArr){
        TextView infoTV=root.findViewById(R.id.pet_info_fragment_textView);
        String text="Name: "+petIn.getName()+"\n"+
                "Age: "+petIn.getAge()+"\n";
        infoTV.setText(text);

        LinearLayout imageLL= root.findViewById(R.id.pet_info_fragment_linearLayout);
        ImageView iV;


        for(int i =0; i<imageArr.size(); i++){
            iV=new ImageView(context);
            iV.setImageBitmap(imageArr.get(i));
            iV.setAdjustViewBounds(true);
            iV.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iV.setMaxHeight(MainActivity.dpToPixel(300));
            iV.setMaxWidth(MainActivity.dpToPixel(350));
            iV.setPadding(MainActivity.dpToPixel(20), 0,MainActivity.dpToPixel(20),0);

            imageLL.addView(iV);
        }



    }
    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;
        super.onAttach(contextIn);
    }
}
