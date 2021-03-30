package lsj.softwareeng.sheltermatch;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import lsj.softwareeng.sheltermatch.ui.browse.BrowseFragment;
import lsj.softwareeng.sheltermatch.ui.fav.FavFragment;
import lsj.softwareeng.sheltermatch.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    final Fragment browse = new BrowseFragment();
    final Fragment favs = new FavFragment();
    final Fragment notifications = new NotificationsFragment();
    final Fragment search = SearchFilterFragment.newInstance();
    final FragmentManager fragManager = getSupportFragmentManager();
    Fragment active = browse;

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelectedListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.navigation_browse:
                            switchToBrowse();
                            return true;

                        case R.id.navigation_fav:
                            fragManager.beginTransaction().hide(active).show(favs).commit();
                            active = favs;
                            return true;

                        case R.id.navigation_notifications:
                            switchToNotifications();
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setup based on: https://medium.com/@oluwabukunmi.aluko/bottom-navigation-view-with-fragments-a074bfd08711
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(bottomNavSelectedListner);
        navView.setSelectedItemId(R.id.navigation_browse);


        fragManager.beginTransaction().add(R.id.nav_host_fragment, notifications, "notifications").hide(notifications).commit();
        fragManager.beginTransaction().add(R.id.nav_host_fragment, favs, "favs").hide(favs).commit();
        fragManager.beginTransaction().add(R.id.nav_host_fragment, search, "search").hide(search).commit();
        fragManager.beginTransaction().add(R.id.nav_host_fragment, browse, "browse").commit();



        ((BrowseFragment) browse).setMA(this);
        ((NotificationsFragment) notifications).setMA(this);
        ((SearchFilterFragment) search).setMA(this);

        ((BrowseFragment) browse).setSearchFrag((SearchFilterFragment) search);
        ((NotificationsFragment) notifications).setSearchFrag((SearchFilterFragment) search);




    }

    public void switchToBrowse(){
        this.fragManager.beginTransaction().hide(active).show(browse).commit();
        this.active = browse;
    }

    public void switchToNotifications(){
        fragManager.beginTransaction().hide(active).show(notifications).commit();
        active = notifications;
    }

    public void newSearch(View view) {
        fragManager.beginTransaction().hide(active).show(search).commit();
        active = search;
    }


}
