package hai2022.team.busapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databases.firebase.Realtime;
import hai2022.team.busapplication.databinding.ActivityMainBinding;
import hai2022.team.busapplication.interfaces.UserListiner;
import hai2022.team.busapplication.models.User;
import hai2022.team.busapplication.ui.ui.fragments.HomeFragment;
import hai2022.team.busapplication.ui.ui.fragments.SettingsFragment;
import hai2022.team.busapplication.utils.Constants;
import hai2022.team.busapplication.utils.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private Authentication authentication;
    private SettingsFragment settingsFragment;
    private DrawerLayout mDrawerLayout;
    private  ActionBarDrawerToggle drawerToggle;
    private HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To Apply Settings on the application
        applySettings();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);

        Toolbar toolbar = binding.maintoolbar.maintoolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(10.0F);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        //To show toggle (nav drawer icon) option
        drawerToggle=new ActionBarDrawerToggle(this,binding.drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        binding.drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.MainActivityNavdrawer.setNavigationItemSelectedListener(this);


        authentication = new Authentication();
        settingsFragment = new SettingsFragment(getApplicationContext());

        Realtime realtime = new Realtime(getBaseContext(), new UserListiner() {
            @Override
            public void ceateuser(@NonNull Task<Void> task) {
                /**/
            }

            @Override
            public void getUser(User user) {
                binding.maintoolbar.maintoolbarTvTitle.setText("Welcome " + user.getType() + " " + user.getUsername());
                Bundle b = new Bundle();
                b.putSerializable("User", user);
                settingsFragment.setArguments(b);
            }

            @Override
            public void getAdmins(ArrayList<User> users) {
                /**/
            }

            @Override
            public void getDrivers(ArrayList<User> users) {

            }

            @Override
            public void getStudents(ArrayList<User> users) {

            }

            @Override
            public void updateUser(boolean state) {

            }
        });
        realtime.getUser(authentication.firebaseUser().getDisplayName(), authentication.firebaseUser().getUid());

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity_layout_container, homeFragment).commit();
    }

    private void itemSelection(int mSelectedId) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNav();

    }

    private void applySettings() {
        if (Utils.getSettingsPreferences(getBaseContext()).getBoolean("isDarkMode", false)) {
            setTheme(R.style.Theme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            setTheme(R.style.Theme_Light);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

//        if (Utils.getSettingsPreferences(MainActivity.class))
//        if (Utils.getSettingsPreferences(getBaseContext()).getString("lang", null).equalsIgnoreCase("en")) {
//            Utils.setLanguage((Activity) getApplicationContext(),"en");
//        } else if (Utils.getSettingsPreferences(getBaseContext()).getString("lang", null).equalsIgnoreCase("ar")) {
//            Utils.setLanguage((Activity) getApplicationContext(),"ar");
//        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(R.string.RYWant);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
            }
        });
        builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private void BottomNav() {
        binding.MainActivityBnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mainmenu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity_layout_container, homeFragment).commit();
                        return true;
                    case R.id.mainmenu_chat:
                        return true;
                    case R.id.mainmenu_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.MainActivity_layout_container, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_drawer_darkmode:
                return true;
            case R.id.nav_drawer_about:
                Intent i = new Intent(getBaseContext(),ContainerActivity.class);
                i.putExtra(Constants.FRAGMENT,Constants.ABOUT_FRAGMENT);
                startActivity(i);
                return true;
            case R.id.nav_drawer_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage(R.string.RYWantLog);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        authentication.logout();
                        finishAffinity();
                        startActivity(new Intent(getBaseContext(), SplashActivity.class));                    }
                });
                builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();

                return true;
        }
        return false;
    }
}