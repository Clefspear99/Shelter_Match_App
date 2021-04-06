package lsj.softwareeng.sheltermatch.ui.fav;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import lsj.softwareeng.sheltermatch.MainActivity;
import lsj.softwareeng.sheltermatch.PetCardFrag;
import lsj.softwareeng.sheltermatch.PetInfoFragment;
import lsj.softwareeng.sheltermatch.PetObject;
import lsj.softwareeng.sheltermatch.R;






public class FavFragment extends Fragment {

    private FavViewModel favViewModel;
    private FragmentActivity context;

    private ArrayList<PetObject> petsToShow;

    private LinearLayout fragLinearLayout;
    private List<PetCardFrag> petCardFragmentList;
    private List<FragmentContainerView> petCardFragmentContainerList;

    private MainActivity ma;

    private int petCardHeight=-1, lastSeen=0, loadNewCount =0, initialLoadCount=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favViewModel =
                ViewModelProviders.of(this).get(FavViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fav, container, false);


        fragLinearLayout = root.findViewById(R.id.fav_linear_layout);

        petCardFragmentList = new ArrayList<>();
        petCardFragmentContainerList = new ArrayList<FragmentContainerView>();

        petsToShow=PetObject.genPets(5);

        addPets(initialLoadCount);
        ScrollView sView = root.findViewById(R.id.fav_scroll_view);

        sView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollPos=sView.getScrollY();


                Log.d("FAV_POS","PetCardHeight "+petCardHeight+" last seen "+lastSeen+" last seen * pet card height "+lastSeen*petCardHeight+" scroll Pos+bottom: "+(scrollPos+sView.getBottom()));
                if(petCardHeight==-1)
                    petCardHeight= PetCardFrag.getTotalHeight();

                int portionOfPetCard=(int) ((1/5.0) * petCardHeight);


                while(true){
                    Log.d("FAV_POS","PetCardHeight "+petCardHeight+" last seen "+lastSeen+" last seen * pet card height "+lastSeen*petCardHeight+" scroll Pos+bottom: "+(scrollPos+sView.getBottom()));
                    if(scrollPos+sView.getBottom()>((lastSeen+1)*petCardHeight)-portionOfPetCard){
                        lastSeen++;
                        if(lastSeen>=petCardFragmentList.size()-initialLoadCount)
                            addPets(loadNewCount);
                    }
                    else
                        break;
                }
            }
        });


        return root;
    }

    public void addPets(int count){
        int curr=petCardFragmentContainerList.size();
        FragmentTransaction  trans=context.getSupportFragmentManager().beginTransaction();

        if(petsToShow.size()-curr<count)
            count=petsToShow.size()-curr;

        for (int i =0; i<count; i++){


            petCardFragmentList.add(new PetCardFrag(petsToShow.get(curr+i), ma));

            petCardFragmentContainerList.add(new FragmentContainerView(context));
            petCardFragmentContainerList.get(curr+i).setId(ViewCompat.generateViewId());
            trans.add(petCardFragmentContainerList.get(curr+i).getId(), petCardFragmentList.get(curr+i));
            fragLinearLayout.addView(petCardFragmentContainerList.get(curr+i));
        }
        trans.commit();
    }



    public void removeFromFavs(PetCardFrag petCardFragment){
        petsToShow.remove(petCardFragment.getPetObject());
        petCardFragmentList.remove(petCardFragment);
        petCardFragmentContainerList.remove(petCardFragment.getContainer());

        fragLinearLayout.removeView(petCardFragment.getContainer());
    }

    public void addPetCardFragmentContainer(PetCardFrag petCardFrag){


        petsToShow.add(petCardFrag.getPetObject());
        petCardFragmentList.add(petCardFrag);
        petCardFragmentContainerList.add(petCardFrag.getContainer());
        fragLinearLayout.addView(petCardFrag.getContainer());


    }



    public void addToFavs(PetCardFrag petCardFragment){
        FragmentTransaction  trans=context.getSupportFragmentManager().beginTransaction();
        FragmentContainerView fragContainer= new FragmentContainerView(context);
        fragContainer.setId(ViewCompat.generateViewId());
        petCardFragment.setContainer(fragContainer);
        trans.add(fragContainer.getId(), petCardFragment);
        trans.commit();


        addPetCardFragmentContainer(petCardFragment);
    }

    public void addToFavsExisting(PetCardFrag petCardFrag){
        addPetCardFragmentContainer(petCardFrag);
    }


    public void setMA(MainActivity ma){
        this.ma = ma;
    }

    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;

        super.onAttach(contextIn);
    }
}
