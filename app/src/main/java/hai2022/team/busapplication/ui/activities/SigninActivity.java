package hai2022.team.busapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databinding.ActivitySigninBinding;
import hai2022.team.busapplication.interfaces.AuthListiner;
import hai2022.team.busapplication.utils.Utils;

public class SigninActivity extends AppCompatActivity {
    ActivitySigninBinding binding;
    Authentication authentication;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        authentication = new Authentication(getBaseContext(), new AuthListiner() {
            @Override
            public void Signup(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getUser().getDisplayName().equalsIgnoreCase("Admin")) {
                        Utils.moveIntoActivity(getBaseContext(), MainActivity.class);
                        finish();
                    } else {
                        authentication.logout();
                        Toast.makeText(SigninActivity.this, "Login Failled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SigninActivity.this, "Login Failled", Toast.LENGTH_SHORT).show();
                }
            }

        });

        signin();

    }

    private void signin() {
        binding.SigninBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.SigninEtEmail.getText().toString();
                password = binding.SigninEtPassword.getText().toString();
//                Toast.makeText(SigninActivity.this, email1 + password1, Toast.LENGTH_SHORT).show();
                authentication.Signin(email, password);
            }
        });
    }
}