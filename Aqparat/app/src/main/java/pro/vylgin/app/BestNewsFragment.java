package pro.vylgin.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
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
public class BestNewsFragment extends SherlockFragment {


    ListView feedView;
    List feedList;
    private ActionBar actionBar;
    int page = 0;
    TinyDB tinyDB;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public BestNewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.best_news_fragment, container, false);


        feedView = (ListView) view.findViewById(R.id.feed);
        feedList = new ArrayList<HashMap<String, String>>();
        tinyDB=new TinyDB(getActivity().getApplicationContext());
        if(tinyDB.getString("best").length()>0)
            new ShowFeedCache().execute();
        else
            new ShowFeed().execute();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                feedList = new ArrayList<HashMap<String, String>>();
                new ShowFeed().execute();
            }
        });
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                }

        @Override
        protected String doInBackground(String... str) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("page", "" + page));

            jsonObject = jsonParser.makeHttpRequest("http://mukhanov.me/aqparat/bestnews.php", "POST", params);
            tinyDB.putString("best", jsonObject.toString());
            try {
                jsonArray = jsonObject.getJSONArray("news");
//
//                if(feedList != null && feedList.size() > 0){
//                    for(int i=0;i < feedList.size();i++)
//                      adapter.add(feedList.get(i));
//
//                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("title", jsonObject.getString("title"));
                    map.put("source", jsonObject.getString("resource"));
                    map.put("text", jsonObject.getString("fulltext"));
                    map.put("date", jsonObject.getString("time"));
                    map.put("photo", jsonObject.getString("imagelink"));
                    map.put("res_img", jsonObject.getString("res_image"));
                    map.put("shared", jsonObject.getString("shared"));
                    map.put("link", jsonObject.getString("link"));

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
            BestAdapter feedAdapter = new BestAdapter(getActivity().getBaseContext(), (ArrayList<HashMap<String, String>>) feedList);
            feedView.setAdapter(feedAdapter);
            mSwipeRefreshLayout.setRefreshing(false);

            //((PullToRefresh_Master) adsListView).onRefreshComplete();

        }
    }


    public class ShowFeedCache extends AsyncTask<String, String, String> {

        JSONArray jsonArray;
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... str) {


            try {
                jsonObject = new JSONObject(tinyDB.getString("best"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                jsonArray = jsonObject.getJSONArray("news");
//
//                if(feedList != null && feedList.size() > 0){
//                    for(int i=0;i < feedList.size();i++)
//                      adapter.add(feedList.get(i));
//
//                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("title", jsonObject.getString("title"));
                    map.put("source", jsonObject.getString("resource"));
                    map.put("text", jsonObject.getString("fulltext"));
                    map.put("date", jsonObject.getString("time"));
                    map.put("photo", jsonObject.getString("imagelink"));
                    map.put("res_img", jsonObject.getString("res_image"));
                    map.put("shared", jsonObject.getString("shared"));
                    map.put("link", jsonObject.getString("link"));

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
            BestAdapter feedAdapter = new BestAdapter(getActivity().getBaseContext(), (ArrayList<HashMap<String, String>>) feedList);
            feedView.setAdapter(feedAdapter);

            //((PullToRefresh_Master) adsListView).onRefreshComplete();

        }
    }

}