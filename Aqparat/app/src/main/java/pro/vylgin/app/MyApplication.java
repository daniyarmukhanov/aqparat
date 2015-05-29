package pro.vylgin.app;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;

/**
 * Created by Данияр on 21.05.2015.
 */
public class MyApplication extends Application
{
    public MyApplication() {
    }
    public void onCreate() {
        Parse.initialize(this, "Zqebwike5ccyVwYAJ0jNjEi5ExiQU33YJuhBkq35", "gLiolY6HqajLgC8i8rDHZhQe9JGJQ9Gdcz5OLprV");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}