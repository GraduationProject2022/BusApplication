package hai2022.team.busapplication.ui.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.CloudStorage;
import hai2022.team.busapplication.databases.firebase.Realtime;
import hai2022.team.busapplication.databinding.FragmentProfileBinding;
import hai2022.team.busapplication.interfaces.StorageListener;
import hai2022.team.busapplication.interfaces.UserListiner;
import hai2022.team.busapplication.models.User;
import hai2022.team.busapplication.ui.activities.ContainerActivity;
import hai2022.team.busapplication.ui.activities.MainActivity;
import hai2022.team.busapplication.ui.activities.SplashActivity;
import hai2022.team.busapplication.utils.Constants;


public class ProfileFragment extends Fragment {

    private onProfileListener profileListener;
    private User user;
    private FragmentProfileBinding binding;
    private Uri profile_uri;
    Realtime realtime;
    Class<? extends Activity> activity;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public ProfileFragment(Class<? extends Activity> activity, onProfileListener profileListener) {
        this.profileListener = profileListener;
        this.activity=activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().get("User");
            Toast.makeText(getContext(), user.getUsername(), Toast.LENGTH_SHORT).show();
        }
        profileListener.profileCalled(Constants.PROFILE_FRAGMENT);
        realtime = new Realtime(getContext(), new UserListiner() {
            @Override
            public void ceateuser(@NonNull Task<Void> task) {

            }

            @Override
            public void getUser(User user) {

            }

            @Override
            public void getAdmins(ArrayList<User> users) {

            }

            @Override
            public void getDrivers(ArrayList<User> users) {

            }

            @Override
            public void getStudents(ArrayList<User> users) {

            }

            @Override
            public void updateUser(boolean state) {
                if (state) {

//                        getActivity().finishAffinity();
//                        startActivity(new Intent(getContext(), SplashActivity.class));
//                    }
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.ProfileFragmentEtUsername.setText(user.getUsername());
        binding.ProfileFragmentEtFullname.setText(user.getFullname());
        binding.ProfileFragmentEtPhone.setText(user.getPhone());
        binding.ProfileFragmentEtAddress.setText(user.getAddress());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        editImage();

        binding.SignupBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUsername(binding.ProfileFragmentEtUsername.getText().toString());
                user.setFullname(binding.ProfileFragmentEtFullname.getText().toString());
                user.setAddress(binding.ProfileFragmentEtAddress.getText().toString());
                user.setPhone(binding.ProfileFragmentEtPhone.getText().toString());
                String Imagepath = profile_uri != null ? "users/" + user.getType() + "/" + user.getUsername().replace(" ", "") + profile_uri.getLastPathSegment() : user.getImgPath();
                if (profile_uri != null) {
                    CloudStorage storage = new CloudStorage(new StorageListener() {
                        @Override
                        public void onDownloadImageListener(Uri uri) {

                        }

                        @Override
                        public void onUploadImageListener(boolean status) {
                            if (status) {
                                requireActivity().finishAffinity();
                                startActivity(new Intent(getContext(), MainActivity.class));
                                realtime.updateUser(user);

                            }
                        }
                    });
                    storage.upload("users", user.getType(), user.getUsername().replace(" ", ""), profile_uri);
                    user.setImgPath(Imagepath);
                }else{
                    realtime.updateUser(user);
                }
                Log.d("ImagePath", ": " + Imagepath + "");



            }
        });

    }

    public interface onProfileListener {
        public void profileCalled(String name);
    }

    private void editImage() {
        binding.ProfileFragmentIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PROFILE_REQUEST_QUDE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PROFILE_REQUEST_QUDE && resultCode == Activity.RESULT_OK) {
            profile_uri = data.getData();
            binding.ProfileFragmentIvProfile.setImageURI(profile_uri);
        }
    }


}