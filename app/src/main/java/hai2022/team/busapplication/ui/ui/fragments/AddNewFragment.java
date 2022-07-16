package hai2022.team.busapplication.ui.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databases.firebase.CloudStorage;
import hai2022.team.busapplication.databases.firebase.Realtime;
import hai2022.team.busapplication.databinding.FragmentAddNewBinding;
import hai2022.team.busapplication.interfaces.AuthListiner;
import hai2022.team.busapplication.interfaces.UserListiner;
import hai2022.team.busapplication.models.User;
import hai2022.team.busapplication.ui.activities.MainActivity;
import hai2022.team.busapplication.ui.activities.SigninActivity;
import hai2022.team.busapplication.utils.Utils;

public class AddNewFragment extends Fragment {

    private String type;
    FragmentAddNewBinding binding;

    public AddNewFragment() {
        // Required empty public constructor
    }

    public static AddNewFragment newInstance(String param1, String param2) {
        AddNewFragment fragment = new AddNewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        type = b.getString("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddNewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        addNewUser();

        return view;
    }


    private void addNewUser() {
        Realtime realtime = new Realtime(getContext(), new UserListiner() {
            @Override
            public void ceateuser(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getContext(), "User Created", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getUser(User user) {}
            @Override
            public void getAdmins(ArrayList<User> users) {}
            @Override
            public void getDrivers(ArrayList<User> users) {}
            @Override
            public void getStudents(ArrayList<User> users) {}

            @Override
            public void updateUser(boolean state) {

            }
        });


        binding.AddNewBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.AddNewEtEmail.getText().toString();
                String password = binding.AddNewEtPassword.getText().toString();
                Authentication authentication2 = new Authentication();
                Authentication authentication = new Authentication(getContext(), new AuthListiner() {
                    @Override
                    public void Signup(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            task.getResult().getUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(type).build());
                            realtime.createUser(task.getResult().getUser().getUid(), new User(task.getResult().getUser().getUid(), binding.AddNewEtUsername.getText().toString(), "admin", task.getResult().getUser().getEmail().toString(), "", "", type, Utils.getDateTime(), Utils.getDateTime()));
                            getActivity().finishAffinity();
                            startActivity(new Intent(getContext(), SigninActivity.class));

                        }
                    }

                    @Override
                    public void editInfo(@NonNull Task<Void> task, String edit) {

                    }
                });

                authentication.Signup(email, password);
            }
        });
    }

}