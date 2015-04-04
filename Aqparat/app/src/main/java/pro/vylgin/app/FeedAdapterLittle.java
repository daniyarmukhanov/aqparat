package pro.vylgin.app;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static pro.vylgin.app.R.drawable.vlast;

/**
 * Created by Doszhan on 05.02.2015.
 */
public class FeedAdapterLittle extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    RelativeLayout relativeLayout;
    LinearLayout greyer;
    String type;
    ImageLoader imageLoader;

    public FeedAdapterLittle(Context context, ArrayList<HashMap<String, String>> arrayList, String type) {
        this.context = context;
        data = arrayList;
        this.type = type;
        imageLoader=new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView title, resource, category;
        final ImageView image, resourceImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.feed_layout_little, parent, false);

        resultp = data.get(position);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.white);
        title = (TextView) itemView.findViewById(R.id.title);
        resource = (TextView) itemView.findViewById(R.id.resourceText);
        resourceImage = (ImageView) itemView.findViewById(R.id.resourceImage);
        // category=(TextView)itemView.findViewById(R.id.category);
        image = (ImageView) itemView.findViewById(R.id.image);
        Resources res = context.getResources(); // need this to fetch the drawable
        Drawable draw = res.getDrawable(R.drawable.unavailable);
        if (type.equalsIgnoreCase("subscription")) {
            image.setImageDrawable(draw);
        }
        title.setText(resultp.get("title"));
        String resource_id = resultp.get("source");
        if(resultp.get("photo").length()>4)
        imageLoader.DisplayImage(resultp.get("photo"),image);
        if (resource_id.equals("4"))
            resource_id = "Tengrinews.kz";
        if (resource_id.equals("3")) {
            resourceImage.setImageDrawable(res.getDrawable(R.drawable.zakon));
            resource_id = "zakon.kz";
        }
        if (resource_id.equals("2")) {
            resource_id = "vlast.kz";

            resourceImage.setImageDrawable(res.getDrawable(R.drawable.vlast));
        }
        resource.setText(resource_id);

        // category.setText(resultp.get("category"));


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultp = data.get(position);
                Intent intent = new Intent(context, NewsViewer.class);
                //intent.setClass(context,NewsItem.class);
                intent.putExtra("id", resultp.get("text"));
                intent.putExtra("title", resultp.get("title"));
                intent.putExtra("photo", resultp.get("photo"));
                intent.putExtra("res_id", resultp.get("source"));

                String resource_id = resultp.get("source");
                if (resource_id.equals("4"))
                    resource_id = "Tengrinews.kz";
                if (resource_id.equals("3"))
                    resource_id = "zakon.kz";
                if (resource_id.equals("2"))
                    resource_id = "vlast.kz";
                intent.putExtra("source", resource_id);
                intent.putExtra("date", resultp.get("date"));
                intent.putExtra("type", type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return itemView;
    }
}
