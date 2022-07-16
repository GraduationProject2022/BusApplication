package hai2022.team.busapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databases.firebase.Realtime;
import hai2022.team.busapplication.databinding.ActivitySignupBinding;
import hai2022.team.busapplication.interfaces.AuthListiner;
import hai2022.team.busapplication.interfaces.UserListiner;
import hai2022.team.busapplication.models.User;
import hai2022.team.busapplication.utils.Utils;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    String username, email, password;
    Authentication authentication;
    Realtime realtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        realtime = new Realtime(getBaseContext(), new UserListiner() {
            @Override
            public void ceateuser(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                    Utils.moveIntoActivity(getBaseContext(), MainActivity.class);
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

            }
        });

        authentication = new Authentication(getBaseContext(), new AuthListiner() {
            @Override
            public void Signup(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    task.getResult().getUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName("admin").build());
                    realtime.createUser(task.getResult().getUser().getUid(), new User(task.getResult().getUser().getUid(), "admin", "admin", task.getResult().getUser().getEmail().toString(), "", "", "admin", Utils.getDateTime(), Utils.getDateTime()));
                } else {

                }
            }

            @Override
            public void editInfo(@NonNull Task<Void> task, String edit) {

            }
        });


        signup();

    }

    private void signup() {
        binding.SignupBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                username = binding.SignupEtUsername.getText().toString();
                email = binding.SignupEtEmail.getText().toString();
                password = binding.SignupEtPassword.getText().toString();
//                Log.d("DataReception",email);
                authentication.Signup(email, password);
            }
        });
    }
}