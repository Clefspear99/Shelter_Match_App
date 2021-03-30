package lsj.softwareeng.sheltermatch.ui.browse;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
import lsj.softwareeng.sheltermatch.R;
import lsj.softwareeng.sheltermatch.SearchFilterFragment;


public class BrowseFragment extends Fragment {

    private BrowseViewModel browseViewModel;
    private FragmentActivity context;

    public SearchFilterFragment searchFrag;

    private View root;
    private MainActivity ma;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        browseViewModel =
                ViewModelProviders.of(this).get(BrowseViewModel.class);
        root = inflater.inflate(R.layout.fragment_browse, container, false);



        LinearLayout fragLinearLayout = root.findViewById(R.id.browse_linear_layout);
        List<PetCardFrag> notificationEditFragmentList = new ArrayList<>();
        List<FragmentContainerView> notificationEditFragmentContainerList = new ArrayList<FragmentContainerView>();
        FragmentTransaction trans=context.getSupportFragmentManager().beginTransaction();

        for (int i =0; i<10; i++){
            notificationEditFragmentList.add(new PetCardFrag());
            notificationEditFragmentContainerList.add(new FragmentContainerView(context));
            notificationEditFragmentContainerList.get(i).setId(ViewCompat.generateViewId());
            trans.add(notificationEditFragmentContainerList.get(i).getId(), notificationEditFragmentList.get(i));
            fragLinearLayout.addView(notificationEditFragmentContainerList.get(i));
        }
        trans.commit();
        Button newSearchButton= (Button) root.findViewById(R.id.browseFilterSearchbutton);
        newSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.newSearch(view);
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


    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;
        super.onAttach(contextIn);
    }



}

