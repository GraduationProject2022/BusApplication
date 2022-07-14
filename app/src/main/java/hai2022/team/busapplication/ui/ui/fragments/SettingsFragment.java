package hai2022.team.busapplication.ui.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databases.firebase.CloudStorage;
import hai2022.team.busapplication.databinding.FragmentSettingsBinding;
import hai2022.team.busapplication.interfaces.StorageListener;
import hai2022.team.busapplication.models.User;
import hai2022.team.busapplication.ui.activities.ContainerActivity;
import hai2022.team.busapplication.ui.activities.MainActivity;
import hai2022.team.busapplication.ui.activities.SplashActivity;
import hai2022.team.busapplication.utils.Constants;
import hai2022.team.busapplication.utils.Utils;

public class SettingsFragment extends Fragment {
    //    SharedPreferences sp;
    Context context;
    SharedPreferences.Editor editor;

    private FragmentSettingsBinding binding;
    Authentication authentication = new Authentication();
    User user;
    CloudStorage cloudStorage;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public SettingsFragment(Context context) {
        this.context = context;
        editor = Utils.getSettingsPreferences(context).edit();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cloudStorage = new CloudStorage(new StorageListener() {
            @Override
            public void onDownloadImageListener(Uri uri) {
                try {
                    Glide.with(getContext()).load(uri).placeholder(R.drawable.profile).into(binding.SettingsFragmentIvProfile);
                }catch (Exception e){
                }
            }

            @Override
            public void onUploadImageListener(boolean status) {
            }
        });
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater);
        View view = binding.getRoot();
        checkSettings();
        darkMode();
        setLanguage();
        binding.SettingsFragmentTvUsername.setText(user.getUsername());
        binding.SettingsFragmentTvEmail.setText(user.getEmail());
        binding.SettingsFragmentTvMembersince.setText(user.getCreated_at());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (user.getImgPath() == null) {
            binding.SettingsFragmentIvProfile.setImageResource(R.drawable.profile);
        } else {
            cloudStorage.download(user.getImgPath());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        editProfile();
        editEmailAndPassword();
        logout();
    }

    private void editProfile() {
        binding.SettingsFragmentBtnEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContainerActivity.class);
                intent.putExtra("User", user);
                intent.putExtra("Fragment", Constants.PROFILE_FRAGMENT);
                startActivity(intent);
            }
        });
    }

    private void editEmailAndPassword() {
        binding.SettingsFragmentBtnEditemailandpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ContainerActivity.class);
//                intent.putExtra("User", user);
                intent.putExtra("Fragment", Constants.EDIT_EMAIL_AND_PASSWORD_FRAGMENT);
                startActivity(intent);
            }
        });
    }

    private void darkMode() {
        binding.SettingsFragmentSwDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    applyMode(b);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    applyMode(b);
                }
            }
        });
    }

    private void applyMode(boolean b) {
        getActivity().finishAffinity();
        startActivity(new Intent(getContext(), MainActivity.class));
        editor.putBoolean("isDarkMode", b).commit();
    }

    private void checkSettings() {
        if (Utils.getSettingsPreferences(context).getBoolean("isDarkMode", false)) {
            binding.SettingsFragmentSwDark.setChecked(true);
        } else {
            binding.SettingsFragmentSwDark.setChecked(false);
        }

    }

    private void setLanguage() {
        binding.SettingsFragmentRbEn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Utils.setLanguage(getActivity(), "en");
                    editor.putString("lang", "en").commit();
                }
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        binding.SettingsFragmentRbAr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Utils.setLanguage(getActivity(), "ar");
                    editor.putString("lang", "ar").commit();
                }
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });
    }

    private void logout() {
        binding.SettingsFragmentBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication.logout();
                Utils.moveIntoActivity(getContext(), SplashActivity.class);
                requireActivity().finish();
            }
        });
    }
}