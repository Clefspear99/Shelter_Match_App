package lsj.softwareeng.sheltermatch.ui.browse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BrowseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BrowseViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}