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
import hai2022.team.busapplication.ui.ui.fragments.ProfileFragment;
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