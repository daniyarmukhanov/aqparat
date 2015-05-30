package pro.vylgin.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.parse.Parse;
import com.parse.ParseInstallation;

import java.util.ArrayList;

public class MainActivity extends SherlockFragmentActivity {

    private static int currentMenuPosition = -1;

    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   Parse.initialize(this, "Zqebwike5ccyVwYAJ0jNjEi5ExiQU33YJuhBkq35", "gLiolY6HqajLgC8i8rDHZhQe9JGJQ9Gdcz5OLprV");
       // ParseInstallation.getCurrentInstallation().saveInBackground();
        setContentView(R.layout.activity_main);

        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBackgroundColor(Color.WHITE);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);
        menu.setSelectorDrawable(R.drawable.sidemenu_items_background);
        changeFragment(0);

        ((ListView) findViewById(R.id.sidemenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuToggle();

                if (currentMenuPosition != position)
                    changeFragment(position);

                currentMenuPosition = position;
            }
        });

        if (currentMenuPosition != -1) {
            ((ListView) findViewById(R.id.sidemenu)).setItemChecked(currentMenuPosition, true);
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sp.contains("first")) {
            TinyDB tinydb = new TinyDB(getApplicationContext());
            ArrayList<String> channels = new ArrayList<String>();
            tinydb.putListString("channels", channels);
            SharedPreferences.Editor ed=sp.edit();
            ed.putBoolean("first", true);
            ed.commit();
        }
        String[] items = {"Главная","Каналы"};
        ((ListView) findViewById(R.id.sidemenu)).setAdapter(
                new ArrayAdapter<Object>(
                        this,
                        R.layout.sidemenu_item,
                        R.id.text,
                        items
                )
        );

        this.menu = menu;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(menu.isMenuShowing()){
                menu.toggle(true);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void changeFragment(int position) {
        switch (position) {
            case 0:
                showFragment(new MainFragment());
                break;
            case 1:
                showFragment(new ChannelsFragment());
                break;
            case 2:
                showFragment(new BookmarksFragment());
                break;
            case 3:
                showFragment(new TopicsFragment());
                break;
        }
    }

    private void showFragment(Fragment currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, currentFragment)
                .commit();
    }

    public SlidingMenu getMenu() {
        return menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuToggle();
                return true;



        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void menuToggle(){
        if(menu.isMenuShowing())
            menu.showContent();
        else
            menu.showMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
