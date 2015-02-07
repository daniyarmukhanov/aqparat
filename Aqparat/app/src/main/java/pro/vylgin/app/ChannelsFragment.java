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

public class ChannelsFragment extends SherlockFragment {

    private ActionBar actionBar;
    GridView channelsListView;
    ArrayList<HashMap<String, String>> channelList;
    ChannelsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.channels_fragment, container, false);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle("Channels");
        channelsListView=(GridView)view.findViewById(R.id.channels);
        new GetChannels().execute();

        return view;
    }
    public class GetChannels extends AsyncTask<String, String, String> {


        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            channelList=new ArrayList<HashMap<String, String>>();
            for (int i = 1; i <20 ; i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("title", "Channel"+i);
                 channelList.add(map);
            }



            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            ChannelsAdapter channelsAdapter=new ChannelsAdapter(getActivity().getBaseContext(), (ArrayList<HashMap<String, String>>) channelList);
            channelsListView.setAdapter(channelsAdapter);



            //((PullToRefresh_Master) adsListView).onRefreshComplete();

        }
    }
}
