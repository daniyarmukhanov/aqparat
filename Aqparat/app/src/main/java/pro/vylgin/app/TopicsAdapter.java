package pro.vylgin.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Doszhan on 06.02.2015.
 */
public class TopicsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    Boolean done;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public TopicsAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        data = arrayList;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = null;


        if (view == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.topics_layout, viewGroup, false);
        } else {
            itemView = view;
        }
        resultp = data.get(i);
        TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(resultp.get("title"));
        final LinearLayout sub = (LinearLayout) itemView.findViewById(R.id.subscription);
        final TextView subs = (TextView) itemView.findViewById(R.id.textsub);
        done = false;
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!done) {
                    sub.setBackgroundColor(Color.GREEN);
                    subs.setText("Subscribed");
                    subs.setTextColor(Color.BLACK);
                    done = true;
                } else {
                    sub.setBackgroundColor(Color.BLUE);
                    subs.setText("Subscribe");
                    subs.setTextColor(Color.WHITE);
                    done = false;
                    Log.d("unsubscribe", "true");
                }
            }
        });
        return itemView;
    }
}
