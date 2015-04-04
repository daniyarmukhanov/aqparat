package pro.vylgin.app;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicsFragment extends SherlockFragment {

    private ActionBar actionBar;
    GridView channelsListView;
    ArrayList<HashMap<String, String>> channelList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.topics_fragment, container, false);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Topics");
        channelsListView=(GridView)view.findViewById(R.id.channels);
        new GetChannels().execute();

        return view;
    }
    public class GetChannels extends AsyncTask<String, String, String> {


        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            channelList=new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < 18 ; i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("title", ""+i);
                channelList.add(map);
            }



            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TopicsAdapter channelsAdapter=new TopicsAdapter(getActivity().getBaseContext(),  channelList);
            channelsListView.setAdapter(channelsAdapter);



            //((PullToRefresh_Master) adsListView).onRefreshComplete();

        }
    }
}
