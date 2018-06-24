package yankov.tsvetilian.watchit.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yankov.tsvetilian.watchit.Adapters.DashboardTabsAdapter;
import yankov.tsvetilian.watchit.R;


public class DashboardFragment extends Fragment {


    private View view;
    private AppCompatActivity activity;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = ((AppCompatActivity) context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        bindView();
        return view;
    }

    private void bindView() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ViewPager tabsPager = view.findViewById(R.id.dashboard_tabs_pager);
        PagerAdapter pagerAdapter = new DashboardTabsAdapter(getChildFragmentManager());
        tabsPager.setAdapter(pagerAdapter);

        TabLayout tabs = view.findViewById(R.id.dashboard_tabs);
        tabs.setupWithViewPager(tabsPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
        activity = null;
    }
}
