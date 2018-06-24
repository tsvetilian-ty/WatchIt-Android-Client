package yankov.tsvetilian.watchit.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import yankov.tsvetilian.watchit.Fragments.WatchLaterFragment;
import yankov.tsvetilian.watchit.Fragments.WatchedFragment;

public class DashboardTabsAdapter extends FragmentPagerAdapter {

    public DashboardTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new WatchedFragment();
            case 1:
                return new WatchLaterFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Watched";
            case 1:
                return "Watch Later";
            default:
                return null;
        }
    }
}
