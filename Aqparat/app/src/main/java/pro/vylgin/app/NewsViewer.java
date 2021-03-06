package pro.vylgin.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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


public class NewsViewer extends Activity {
    RelativeLayout relativeLayout;
    String textlong,id, link;
    TextView title, resource, text, date;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_viewer);
          getActionBar().setHomeButtonEnabled(true);
          getActionBar().setDisplayShowCustomEnabled(true);
          getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        //String type;
        final ImageView image, resimage;
        Intent recieve=getIntent();
        relativeLayout=(RelativeLayout)findViewById(R.id.white);
        resimage=(ImageView)findViewById(R.id.resourceImage);
        title=(TextView)findViewById(R.id.title);
        resource=(TextView)findViewById(R.id.resourceText);
        id=recieve.getStringExtra("id");

        image=(ImageView)findViewById(R.id.image);

        //type=recieve.getStringExtra("type");
        text=(TextView)findViewById(R.id.text);
        date=(TextView)findViewById(R.id.date);
        link=recieve.getStringExtra("link");
        Resources res = getResources(); // need this to fetch the drawable
        Drawable draw = res.getDrawable( R.drawable.news_fish );


        if(recieve.getStringExtra("photo").length()>4)
            new ImageLoader(this).DisplayImage(recieve.getStringExtra("photo"),image);
        else
        image.setVisibility(View.GONE);
        title.setText(recieve.getStringExtra("title"));
        resource.setText(recieve.getStringExtra("source"));
        String resource_id=recieve.getStringExtra("res_id");
        if (resource_id.equals("4"))
            resource_id = "Tengrinews.kz";
        if (resource_id.equals("3")) {
            resimage.setImageDrawable(res.getDrawable(R.drawable.zakon));
                   }
        if (resource_id.equals("2")) {
               resimage.setImageDrawable(res.getDrawable(R.drawable.vlast));
        }
        new GetText().execute();

        date.setText(recieve.getStringExtra("date"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_viewer, menu);
        return true;
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
    public class GetText extends AsyncTask<String, String, String> {

        private JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray;
        JSONObject jsonObject;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewsViewer.this);
            progressDialog.setMessage("Loading text...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... str) {



            jsonObject = jsonParser.getJSONFromUrl("http://mukhanov.me/aqparat/get_news.php?pid="+id);

            try {
                jsonArray=jsonObject.getJSONArray("news");
                JSONObject newsobject=jsonArray.getJSONObject(0);
                textlong=newsobject.getString("text");

            } catch (JSONException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            text.setText(textlong);



            //((PullToRefresh_Master) adsListView).onRefreshComplete();

        }
    }

}
