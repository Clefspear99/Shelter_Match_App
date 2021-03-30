package lsj.softwareeng.sheltermatch;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFilterFragment extends Fragment {


    private FragmentActivity context;
    private View root;

    private MainActivity ma;

    public SearchFilterFragment() {
        // Required empty public constructor
    }

    public static SearchFilterFragment newInstance() {
        SearchFilterFragment fragment = new SearchFilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_search, container, false);
        msSpinnerFunction(R.array.breed_array, R.id.breedMSSpinner);
        msSpinnerFunction(R.array.age_array, R.id.ageMSSpinner);


        Button searchButton= (Button) root.findViewById(R.id.applySearchButton);
        Spinner notifyOrSearch = (Spinner) root.findViewById(R.id.notifyOrSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notifyOrSearch.getSelectedItemPosition()==1)
                    ma.switchToNotifications();
                else
                    ma.switchToBrowse();
            }
        });


        return root;
    }

    public void setMA(MainActivity ma){
        this.ma = ma;
    }

    public void onAttach(Context contextIn){
        this.context = (FragmentActivity) contextIn;
        super.onAttach(contextIn);
    }

    public void setNotifySearchSpinnerPos(int in){
        Spinner spin = (Spinner) root.findViewById(R.id.notifyOrSearch);
        spin.setSelection(in);
    }


    private void msSpinnerFunction(int array, int id){
        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */

        final List<String> list = Arrays.asList(getResources().getStringArray(array));
        final List<KeyPairBoolData> listArray1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray1.add(h);
        }


        MultiSpinnerSearch multiSelectSpinnerWithSearch = root.findViewById(id);

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        multiSelectSpinnerWithSearch.setSearchEnabled(false);

        multiSelectSpinnerWithSearch.setSearchHint("");

        // If you will set the limit, this button will not display automatically.
        multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

        //A text that will display in clear text button
        multiSelectSpinnerWithSearch.setClearText("Never mind");

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // pass true in setSelected of any item that you want to preselect
        multiSelectSpinnerWithSearch.setItems(listArray1, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });



    }

}