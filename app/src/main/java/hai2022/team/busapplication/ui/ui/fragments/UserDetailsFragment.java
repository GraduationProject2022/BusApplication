package hai2022.team.busapplication.ui.ui.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.CloudStorage;
import hai2022.team.busapplication.databinding.FragmentUserDetailsBinding;
import hai2022.team.busapplication.interfaces.StorageListener;
import hai2022.team.busapplication.models.User;

public class UserDetailsFragment extends Fragment {

    private User user;
    private FragmentUserDetailsBinding binding;
    private CloudStorage storage;
    public UserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getArguments().getSerializable("User");

        storage = new CloudStorage(new StorageListener() {
            @Override
            public void onDownloadImageListener(Uri uri) {
                Glide.with(getContext()).load(uri).placeholder(R.drawable.profile).into(binding.userDetailsFragmentIvUser);
            }

            @Override
            public void onUploadImageListener(boolean status) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        try {
            if (!user.getImgPath().equals("")){
                storage.download(user.getImgPath());
            }
        }catch (Exception e){

        }

        binding.userDetailsFragmentTvName.setText(user.getUsername());
        binding.userDetailsFragmentTvAddress.setText(user.getAddress());
        binding.userDetailsFragmentTvEmail.setText(user.getEmail());
        binding.userDetailsFragmentTvPhone.setText(user.getPhone());
        Toast.makeText(getContext(), ""+user.getUsername(), Toast.LENGTH_SHORT).show();
        return root;
    }
}