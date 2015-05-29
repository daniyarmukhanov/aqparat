package pro.vylgin.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    TinyDB tinydb;
    static Boolean subs[];
    ArrayList<String>channels;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ChannelsAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.context = context;
        data = arrayList;
        tinydb = new TinyDB(context);
        subs=new Boolean[data.size()];
        channels=tinydb.getListString("channels");
        for(int j=0;j<subs.length;j++){
            subs[j]=false;
        }
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


       inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = inflater.inflate(R.layout.channels_layout, viewGroup, false);
        resultp = data.get(i);
        ImageView channel=(ImageView)itemView.findViewById(R.id.image_ch);
        if(resultp.get("title").equalsIgnoreCase("tengrinews.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.tengri));
        if(resultp.get("title").equalsIgnoreCase("zakon.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.zakon_kz));
        if(resultp.get("title").equalsIgnoreCase("vlast.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.vlastbig));
        if(resultp.get("title").equalsIgnoreCase("baq.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.baq));
        if(resultp.get("title").equalsIgnoreCase("kapital.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.kapital));
        if(resultp.get("title").equalsIgnoreCase("forbes.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.forbes));
        if(resultp.get("title").equalsIgnoreCase("vesti.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.vesti));
        if(resultp.get("title").equalsIgnoreCase("inform.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.inform));
        if(resultp.get("title").equalsIgnoreCase("nur.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.nur));
        if(resultp.get("title").equalsIgnoreCase("sports.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.sports));
        if(resultp.get("title").equalsIgnoreCase("makala.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.makala));
        if(resultp.get("title").equalsIgnoreCase("bnews.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.bnews));
        if(resultp.get("title").equalsIgnoreCase("liter.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.liter));
        if(resultp.get("title").equalsIgnoreCase("today.kz"))
            channel.setImageDrawable(context.getResources().getDrawable(R.drawable.today));
        final Button sub=(Button)itemView.findViewById(R.id.subbutton);
        if(channels.contains(resultp.get("title"))){
            sub.setText("Отписаться");
            subs[i]=true;}
        else {
            sub.setText("Подписаться");
            subs[i]=false;
        }sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("position",""+i);
                resultp = data.get(i);
                if(!subs[i]){
                    channels=tinydb.getListString("channels");
                    channels.add(resultp.get("title"));
                    tinydb.putListString("channels",channels);
                    subs[i]=true;
                    sub.setText("Отписаться");
                    for (String s : channels) {
                        Log.d("Channels",s);
                    }
                }else{
                    channels=tinydb.getListString("channels");
                    channels.remove(resultp.get("title"));
                    tinydb.putListString("channels",channels);
                    subs[i]=false;
                    sub.setText("Подписаться");
                }
            }
        });
        return itemView;
    }
}
