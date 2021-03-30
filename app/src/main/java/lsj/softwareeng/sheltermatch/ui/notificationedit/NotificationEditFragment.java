package lsj.softwareeng.sheltermatch.ui.notificationedit;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lsj.softwareeng.sheltermatch.R;

public class NotificationEditFragment extends Fragment {

    private NotifcationEditViewModel mViewModel;

    public static NotificationEditFragment newInstance() {
        return new NotificationEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notifcation_edit_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotifcationEditViewModel.class);
        // TODO: Use the ViewModel
    }

}
