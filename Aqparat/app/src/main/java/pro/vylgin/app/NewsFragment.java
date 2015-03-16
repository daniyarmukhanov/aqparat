package pro.vylgin.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jsonparser.JSONParser;

/**
 * Created by Doszhan on 06.02.2015.
 */
public class NewsFragment extends SherlockFragment {

    ListView feedView;
    List feedList;
    String type;
    private ActionBar actionBar;

    public NewsFragment(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.best_news_fragment, container, false);
        if (type.equalsIgnoreCase("bookmark")) {
            actionBar = getSherlockActivity().getSupportActionBar();
            actionBar.removeAllTabs();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setTitle("Bookmarks");
        }
        feedView = (ListView) view.findViewById(R.id.feed);
        new ShowFeed().execute();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    public class ShowFeed extends AsyncTask<String, String, String> {

        private JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        JSONObject jsonObject;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading news...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... str) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", "email"));

            jsonObject = jsonParser.makeHttpRequest("http://mukhanov.me/aqparat/allnews.php", "POST", params);

            try {
                jsonArray=jsonObject.getJSONArray("news");
                feedList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("title", jsonObject.getString("title"));
                    map.put("source", jsonObject.getString("resource_id"));
                    map.put("text", jsonObject.getString("text"));
                    map.put("category", "general");
                    map.put("date", jsonObject.getString("time"));
                    map.put("photo", jsonObject.getString("photo"));
                    feedList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            FeedAdapter feedAdapter = new FeedAdapter(getActivity().getBaseContext(), (ArrayList<HashMap<String, String>>) feedList, type);
            feedView.setAdapter(feedAdapter);


            //((PullToRefresh_Master) adsListView).onRefreshComplete();

        }
    }

}