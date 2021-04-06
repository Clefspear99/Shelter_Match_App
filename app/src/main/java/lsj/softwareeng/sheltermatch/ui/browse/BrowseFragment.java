package lsj.softwareeng.sheltermatch.ui.browse;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import lsj.softwareeng.sheltermatch.MainActivity;
import lsj.softwareeng.sheltermatch.PetCardFrag;
import lsj.softwareeng.sheltermatch.PetObject;
import lsj.softwareeng.sheltermatch.R;
import lsj.softwareeng.sheltermatch.SearchFilterFragment;


public class BrowseFragment extends Fragment {

    private BrowseViewModel browseViewModel;
    private FragmentActivity context;

    public SearchFilterFragment searchFrag;

    private View root;
    private MainActivity ma;

    private ArrayList<PetObject> petsToShow;

    private LinearLayout fragLinearLayout;
    private List<PetCardFrag> petCardFragmentList;
    private List<FragmentContainerView> petCardFragmentContainerList;
    
    private int petCardHeight=-1, lastSeen=0, loadNewCount=1, initialLoadCount=10;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        browseViewModel =
                ViewModelProviders.of(this).get(BrowseViewModel.class);
        root = inflater.inflate(R.layout.fragment_browse, container, false);



        fragLinearLayout = root.findViewById(R.id.browse_linear_layout);
        petCardFragmentList = new ArrayList<>();
        petCardFragmentContainerList = new ArrayList<FragmentContainerView>();


        petsToShow=PetObject.genPets(100);

        addPets(initialLoadCount);

        
        
        ScrollView sView = root.findViewById(R.id.browse_scroll_view);

        sView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollPos=sView.getScrollY();


                Log.d("BROWSE_POS","PetCardHeight "+petCardHeight+" last seen "+lastSeen+" last seen * pet card height "+lastSeen*petCardHeight+" scroll Pos+bottom: "+(scrollPos+sView.getBottom()));
                if(petCardHeight==-1)
                    petCardHeight= PetCardFrag.getTotalHeight();

                int portionOfPetCard=(int) ((1/5.0) * petCardHeight);

                while(true){
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



        Button newSearchButton= (Button) root.findViewById(R.id.browseFilterSearchbutton);
        newSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.newSearch();
                searchFrag.setNotifySearchSpinnerPos(0);
            }
        });


        return root;
    }


    public void setMA(MainActivity ma){
        this.ma = ma;
    }

    public void setSearchFrag(SearchFilterFragment in){
        this.searchFrag=in;
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

    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;
        super.onAttach(contextIn);
    }



}

