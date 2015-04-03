package pro.vylgin.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerColorAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    public ViewPagerColorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                return new NewsFragment("Subscription");
            case 1:
                return new BestNewsFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
