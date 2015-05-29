package pro.vylgin.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jsonparser.JSONParser;


public class BestNewsViewer extends Activity {
    RelativeLayout relativeLayout;
    String textlong, id, link;
    TextView title, resource, text, date;
    ImageView image, resimage;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_viewer);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        //String type;

        Intent recieve = getIntent();
        link=recieve.getStringExtra("link");
        relativeLayout = (RelativeLayout) findViewById(R.id.white);
        relativeLayout.setVisibility(View.INVISIBLE);
        resimage = (ImageView) findViewById(R.id.resourceImage);
        title = (TextView) findViewById(R.id.title);
        resource = (TextView) findViewById(R.id.resourceText);
        id = recieve.getStringExtra("id");

        image = (ImageView) findViewById(R.id.image);


        //type=recieve.getStringExtra("type");
        text = (TextView) findViewById(R.id.text);
        date = (TextView) findViewById(R.id.date);

        if (getIntent().hasExtra("push")) {

            new ShowFeed().execute();
        } else {
            new ImageLoader(this).DisplayImage(recieve.getStringExtra("photo"), image);
            new ImageLoader(this).DisplayImage(recieve.getStringExtra("res_img"), resimage);
            title.setText(recieve.getStringExtra("title"));
            resource.setText(recieve.getStringExtra("source"));
            date.setText(recieve.getStringExtra("date"));
            text.setText(recieve.getStringExtra("text"));
            relativeLayout.setVisibility(View.VISIBLE);
            if(recieve.getStringExtra("photo").length()<2){
                image.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_viewer, menu);
        return true;
    }

    public class ShowFeed extends AsyncTask<String, String, String> {

        private JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        JSONObject jsonObject;
        String title1, date1, text1, image1, res, resimg;

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(BestNewsViewer.this);
            progressDialog.setMessage("Подождите...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... str) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("page", ""));

            jsonObject = jsonParser.makeHttpRequest("http://mukhanov.me/aqparat/bestnews.php", "POST", params);

            try {
                jsonArray = jsonObject.getJSONArray("news");
//
//                if(feedList != null && feedList.size() > 0){
//                    for(int i=0;i < feedList.size();i++)
//                      adapter.add(feedList.get(i));
//
//                }
                for (int i = 0; i < 1; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    title1 = jsonObject.getString("title");
                    res = jsonObject.getString("resource");
                    text1 = jsonObject.getString("fulltext");
                    date1 = jsonObject.getString("time");
                    image1 = jsonObject.getString("imagelink");
                    resimg = jsonObject.getString("res_image");
                    link=jsonObject.getString("link");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            title.setText(title1);
            resource.setText(res);
            text.setText(text1);
            date.setText(date1);
            new ImageLoader(getApplicationContext()).DisplayImage(image1, image);
            new ImageLoader(getApplicationContext()).DisplayImage(resimg, resimage);
            relativeLayout.setVisibility(View.VISIBLE);
            progressDialog.dismiss();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
            return true;
        }
        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, link);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
                    super.onBackPressed();


    }
}
