package lsj.softwareeng.sheltermatch.ui.fav;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import lsj.softwareeng.sheltermatch.PetCardFrag;
import lsj.softwareeng.sheltermatch.R;






public class FavFragment extends Fragment {

    private FavViewModel favViewModel;
    private FragmentActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favViewModel =
                ViewModelProviders.of(this).get(FavViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fav, container, false);
        LinearLayout fragLinearLayout = root.findViewById(R.id.fav_linear_layout);

        List<PetCardFrag> notificationEditFragmentList = new ArrayList<>();
        List<FragmentContainerView> notificationEditFragmentContainerList = new ArrayList<FragmentContainerView>();

        FragmentTransaction trans=context.getSupportFragmentManager().beginTransaction();

        for (int i =0; i<3; i++){
            notificationEditFragmentList.add(new PetCardFrag());
            notificationEditFragmentContainerList.add(new FragmentContainerView(context));
            notificationEditFragmentContainerList.get(i).setId(ViewCompat.generateViewId());
            trans.add(notificationEditFragmentContainerList.get(i).getId(), notificationEditFragmentList.get(i));
            fragLinearLayout.addView(notificationEditFragmentContainerList.get(i));
        }
        trans.commit();

        return root;
    }

    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;

        super.onAttach(contextIn);
    }
}
