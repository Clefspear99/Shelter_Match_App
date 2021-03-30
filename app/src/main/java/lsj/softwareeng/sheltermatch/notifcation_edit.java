package lsj.softwareeng.sheltermatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import lsj.softwareeng.sheltermatch.ui.notificationedit.NotificationEditFragment;

public class notifcation_edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifcation_edit_fragment);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NotificationEditFragment.newInstance())
                    .commitNow();
        }
    }
}
