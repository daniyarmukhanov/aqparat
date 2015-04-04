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
        done = false;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View itemView = null;


        if (view == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.topics_layout, viewGroup, false);
        } else {
            itemView = view;
        }
        resultp = data.get(i);
        final ImageView sub;
        final ImageView topic;
        topic=(ImageView)itemView.findViewById(R.id.image_ch);
        sub=(ImageView)itemView.findViewById(R.id.subs);
        if(resultp.get("title").equalsIgnoreCase(""+0))
        topic.setImageDrawable(context.getResources().getDrawable(R.drawable.daily));
        if(resultp.get("title").equalsIgnoreCase(""+1))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.politics));
        if(resultp.get("title").equalsIgnoreCase(""+2))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.world));
        if(resultp.get("title").equalsIgnoreCase(""+3))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.men));
        if(resultp.get("title").equalsIgnoreCase(""+4))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.women));
        if(resultp.get("title").equalsIgnoreCase(""+5))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.sport));
        if(resultp.get("title").equalsIgnoreCase(""+6))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.society));
        if(resultp.get("title").equalsIgnoreCase(""+7))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.health));
        if(resultp.get("title").equalsIgnoreCase(""+8))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.animal));
        if(resultp.get("title").equalsIgnoreCase(""+9))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.history));
        if(resultp.get("title").equalsIgnoreCase(""+10))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.realstate));
        if(resultp.get("title").equalsIgnoreCase(""+11))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.tourism));
        if(resultp.get("title").equalsIgnoreCase(""+12))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.edu));
        if(resultp.get("title").equalsIgnoreCase(""+13))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.auto));
        if(resultp.get("title").equalsIgnoreCase(""+14))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.afisha));
        if(resultp.get("title").equalsIgnoreCase(""+15))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.business));
        if(resultp.get("title").equalsIgnoreCase("" + 16))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.economic));
        if(resultp.get("title").equalsIgnoreCase(""+17))
            topic.setImageDrawable(context.getResources().getDrawable(R.drawable.culture));

        topic.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Resources res = context.getResources(); // need this to fetch the drawable
                Drawable draw = res.getDrawable(R.drawable.done);
                Drawable draw1 = res.getDrawable(R.drawable.subscribe);
                 if (!done) {
                    sub.setImageDrawable(draw);
                    done = true;
                } else {
                    sub.setImageDrawable(draw1);
                    done=false;
                }

            }
        });



        return itemView;
    }
}
