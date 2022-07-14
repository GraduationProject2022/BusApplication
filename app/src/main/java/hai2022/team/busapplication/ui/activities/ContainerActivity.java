package hai2022.team.busapplication.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databinding.ActivityContainerBinding;
import hai2022.team.busapplication.ui.ui.fragments.AboutFragment;
import hai2022.team.busapplication.ui.ui.fragments.EditEmailAndPasswordFragment;
import hai2022.team.busapplication.ui.ui.fragments.ProfileFragment;
import hai2022.team.busapplication.ui.ui.fragments.UserDetailsFragment;
import hai2022.team.busapplication.utils.Constants;

public class ContainerActivity extends AppCompatActivity implements ProfileFragment.onProfileListener {

    ActivityContainerBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContainerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//         toolbar = binding.maintoolbar.maintoolbar;


        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.get("Fragment");
        if (name.equals(Constants.PROFILE_FRAGMENT)) {
            ProfileFragment profileFragment = new ProfileFragment(ContainerActivity.class,this::profileCalled);
            profileFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.ContainerActivity_container, profileFragment).commit();
        }else if (name.equals(Constants.ABOUT_FRAGMENT)){
            getSupportFragmentManager().beginTransaction().replace(R.id.ContainerActivity_container, AboutFragment.newInstance()).commit();
        }else if (name.equals(Constants.USER_DETAILS_FRAGMENT)){
            UserDetailsFragment detailsFragment = new UserDetailsFragment();
            Bundle b = new Bundle();
            b.putSerializable("User", bundle.getSerializable("User"));
            detailsFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.ContainerActivity_container, detailsFragment).commit();
        }else if(name.equals(Constants.EDIT_EMAIL_AND_PASSWORD_FRAGMENT)){
            getSupportFragmentManager().beginTransaction().replace(R.id.ContainerActivity_container, EditEmailAndPasswordFragment.newInstance()).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        back();
    }

    @Override
    public void profileCalled(String name) {
        if (name.equals(Constants.PROFILE_FRAGMENT)) {
//            binding.maintoolbar.maintoolbarTvTitle.setText("Edit Profile");
        }
    }

    private void back() {
        binding.ContainerActivityIvArrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}