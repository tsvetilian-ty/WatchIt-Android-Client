package yankov.tsvetilian.watchit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yankov.tsvetilian.watchit.R;


public class WatchLaterFragment extends Fragment {

    private View view;

    public WatchLaterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_watch_later, container, false);
        bindView();
        return view;
    }

    private void bindView() {
      //TODO
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }
}
