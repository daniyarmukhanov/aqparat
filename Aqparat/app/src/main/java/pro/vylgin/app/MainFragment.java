package pro.vylgin.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainFragment extends SherlockFragment {

    private ActionBar     actionBar;
    private ViewPager     pager;
    private ActionBar.Tab tab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("Главная");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.show();

        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actionBar.setSelectedNavigationItem(position);
            }
        };

        pager = (ViewPager) view.findViewById(R.id.pager_color);
        pager.setOnPageChangeListener(ViewPagerListener);

        FragmentManager fm = getChildFragmentManager();
        ViewPagerColorAdapter viewPagerScheduleAdapter = new ViewPagerColorAdapter(fm);
        pager.setAdapter(viewPagerScheduleAdapter);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());

                SherlockFragmentActivity sherlockFragmentActivity = getSherlockActivity();
                if (sherlockFragmentActivity != null) {
                    SlidingMenu slidingMenu = ((MainActivity) sherlockFragmentActivity).getMenu();
                    if (tab.getPosition() == 0) {
                        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    } else {
                        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                    }
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) { }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) { }
        };


        tab = actionBar.newTab().setText("Лента").setTabListener(tabListener);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Популярное").setTabListener(tabListener);
        actionBar.addTab(tab);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(sharedPreferences.contains("push")) {
            pager.setCurrentItem(1);
            editor.remove("push");
            editor.commit();
        }
            return view;
    }
}
