package yankov.tsvetilian.watchit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yankov.tsvetilian.watchit.R;


public class WatchedFragment extends Fragment {

    private View view;

    public WatchedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_watched, container, false);
        bindView();
        return view;
    }

    private void bindView() {
        Log.d("WATCHIT", "ALREADY WATCHED");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }
}
