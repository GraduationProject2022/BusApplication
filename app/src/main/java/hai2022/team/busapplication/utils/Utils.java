package hai2022.team.busapplication.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.ui.ui.fragments.HomeFragment;

public class Utils {
    public static void setLanguage(Activity activity, String lang){
        Locale locale = new Locale(lang);
        Resources  resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    public static Locale getLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    public static SharedPreferences getSettingsPreferences(Context context){
        return context.getSharedPreferences("SettingsPreferences", Context.MODE_PRIVATE);
    }


    public static void moveIntoActivity(Context context,Class<? extends Activity> activity) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
