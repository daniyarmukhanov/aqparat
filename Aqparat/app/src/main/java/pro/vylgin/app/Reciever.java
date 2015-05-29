package pro.vylgin.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.parse.ParsePushBroadcastReceiver;

public class Reciever extends ParsePushBroadcastReceiver {


    @Override
    public void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("push", true);
        editor.commit();
        Intent i = new Intent(context, BestNewsViewer.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("push", "true");
        context.startActivity(i);
    }

}