package yankov.tsvetilian.watchit.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import yankov.tsvetilian.watchit.Adapters.WatchItemAdapter;
import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.R;
import yankov.tsvetilian.watchit.Utilities.SignOutContract;
import yankov.tsvetilian.watchit.ViewModels.UserViewModel;
import yankov.tsvetilian.watchit.ViewModels.WatchViewModel;


public class WatchLaterFragment extends Fragment {

    private View view;
    private WatchViewModel viewModel;
    private RecyclerView recyclerView;
    private UserViewModel userViewModel;

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
        viewModel = ViewModelProviders.of(this).get(WatchViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        recyclerView = view.findViewById(R.id.watch_later_rv);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final WatchItemAdapter adapter = new WatchItemAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        viewModel.getWatchLater().observe(this, new Observer<List<WatchModel>>() {
            @Override
            public void onChanged(@Nullable List<WatchModel> watches) {
                if (watches != null && !watches.isEmpty()) {
                    adapter.setData(watches);
                    view.findViewById(R.id.no_watch_later).setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.no_watch_later).setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.watche_later_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clear_all:
                viewModel.deleteAllWatchLater();
                break;
            case R.id.sign_out:
                userViewModel.userSignOut(new SignOutContract() {
                    @Override
                    public void onSignOut(String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
        recyclerView = null;
    }
}
