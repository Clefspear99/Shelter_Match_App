package lsj.softwareeng.sheltermatch.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import lsj.softwareeng.sheltermatch.MainActivity;
import lsj.softwareeng.sheltermatch.R;
import lsj.softwareeng.sheltermatch.SearchFilterFragment;
import lsj.softwareeng.sheltermatch.ui.notificationedit.NotificationEditFragment;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentActivity context;

    public SearchFilterFragment searchFrag;

    private MainActivity ma;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        int height=0;

        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        //Bundle bundle=new Bundle();

        //FragmentContainerView fragmentContainer = context.findViewById(R.id.notification_edits_fragment_container_view);
        //ConstraintSet constraintSet = new ConstraintSet();
       //onstraintSet.clone(fragmentContainer);
        LinearLayout fragLinearLayout = root.findViewById(R.id.frag_linear_layout);

        List<NotificationEditFragment> notificationEditFragmentList = new ArrayList<NotificationEditFragment>();
        List<FragmentContainerView> notificationEditFragmentContainerList = new ArrayList<FragmentContainerView>();

        FragmentTransaction trans=context.getSupportFragmentManager().beginTransaction();

        for (int i =0; i<10; i++){
            notificationEditFragmentList.add(new NotificationEditFragment());
            notificationEditFragmentContainerList.add(new FragmentContainerView(context));
            notificationEditFragmentContainerList.get(i).setId(ViewCompat.generateViewId());
            trans.add(notificationEditFragmentContainerList.get(i).getId(), notificationEditFragmentList.get(i));
            fragLinearLayout.addView(notificationEditFragmentContainerList.get(i));
        }
        trans.commit();

        Button newSearchButton= (Button) root.findViewById(R.id.newNotificationButton);
        newSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFrag.setNotifySearchSpinnerPos(1);
                ma.newSearch();
            }
        });

        return root;
    }


    public void setSearchFrag(SearchFilterFragment in){
        this.searchFrag=in;
    }

    public void setMA(MainActivity ma){
        this.ma = ma;
    }

    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;
        super.onAttach(contextIn);
    }
}
