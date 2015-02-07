package pro.vylgin.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Doszhan on 06.02.2015.
 */
public class ChannelsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    Boolean done;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ChannelsAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
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
            itemView = inflater.inflate(R.layout.channels_layout, viewGroup, false);
        } else {
            itemView = view;
        }
        resultp = data.get(i);
        TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(resultp.get("title"));
        final ImageView sub = (ImageView) itemView.findViewById(R.id.subs);
        done = false;
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
