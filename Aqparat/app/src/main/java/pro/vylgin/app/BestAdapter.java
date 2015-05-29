package pro.vylgin.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

/**
 * Created by Данияр on 21.05.2015.
 */
public class BestAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    RelativeLayout relativeLayout;
    ImageLoader imageLoader;

    public BestAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        data = arrayList;
        imageLoader = new ImageLoader(context);
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
        TextView title, resource, shared;
        final ImageView image, resourceImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.best_layout, parent, false);

        resultp = data.get(position);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.white);
        title = (TextView) itemView.findViewById(R.id.title);
        shared=(TextView)itemView.findViewById(R.id.shared);
        shared.setText("Shared: "+resultp.get("shared"));
        resource = (TextView) itemView.findViewById(R.id.resourceText);
        resourceImage = (ImageView) itemView.findViewById(R.id.resourceImage);
        // category=(TextView)itemView.findViewById(R.id.category);
        image = (ImageView) itemView.findViewById(R.id.image);
        Resources res = context.getResources(); // need this to fetch the drawable
        Drawable draw = res.getDrawable(R.drawable.unavailable);

        title.setText(resultp.get("title"));
        String resource_id = resultp.get("source");
        String photo=resultp.get("photo");
        imageLoader.DisplayImage(photo, image);
        imageLoader.DisplayImage(resultp.get("res_img"), resourceImage);
        resource.setText(resource_id);
        //Log.d("IMAGELINK", photo);
        if(photo.length()<2){
            image.setVisibility(View.GONE);
        }

        // category.setText(resultp.get("category"));


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultp = data.get(position);
                Intent intent = new Intent(context, BestNewsViewer.class);
                //intent.setClass(context,NewsItem.class);
                intent.putExtra("text", resultp.get("text"));
                intent.putExtra("title", resultp.get("title"));
                intent.putExtra("photo", resultp.get("photo"));
                intent.putExtra("res_id", resultp.get("source"));
                intent.putExtra("res_img",resultp.get("res_img"));
                String resource_id = resultp.get("source");
                intent.putExtra("source", resource_id);
                intent.putExtra("date", resultp.get("date"));
                intent.putExtra("link", resultp.get("link"));

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return itemView;
    }
}
