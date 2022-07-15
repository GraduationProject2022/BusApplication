package hai2022.team.busapplication.ui.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databinding.FragmentEditEmailAndPasswordBinding;
import hai2022.team.busapplication.interfaces.AuthListiner;

public class EditEmailAndPasswordFragment extends Fragment {
    private static EditEmailAndPasswordFragment editEmailAndPasswordFragment;
    private FragmentEditEmailAndPasswordBinding binding;
    private Authentication authentication;

    public EditEmailAndPasswordFragment() {
        // Required empty public constructor
    }


    public static EditEmailAndPasswordFragment newInstance() {
        if (editEmailAndPasswordFragment == null)
            editEmailAndPasswordFragment = new EditEmailAndPasswordFragment();
        return editEmailAndPasswordFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "To edit Email or Password you must login.", Toast.LENGTH_LONG).show();
        authentication = new Authentication(getContext(), new AuthListiner() {
            @Override
            public void Signup(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    binding.emailAndPassFragmentBtnSignin.setVisibility(View.GONE);
                    binding.emailAndPassFragmentBtnUpdate.setVisibility(View.VISIBLE);
                    binding.emailAndPassFragmentEtEmail.setText("");
                    binding.emailAndPassFragmentEtPassword.setText("");
                }else{
                    Toast.makeText(getContext(), "sign in information is wrong!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void editInfo(@NonNull Task<Void> task, String edit) {
                if (task.isSuccessful() && edit.equals("email")) {
                    Toast.makeText(getContext(), "email updated sucessfuly!!", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "email cant be updated!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                if (task.isSuccessful() && edit.equals("password")) {
                    requireActivity().finish();
                    Toast.makeText(getContext(), "password updated sucessfuly!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "password cant be updated!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditEmailAndPasswordBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        binding.emailAndPassFragmentBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication.EditEmailAndPass(binding.emailAndPassFragmentEtEmail.getText().toString(), binding.emailAndPassFragmentEtPassword.getText().toString());
            }
        });

        binding.emailAndPassFragmentBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication.Signin(binding.emailAndPassFragmentEtEmail.getText().toString(), binding.emailAndPassFragmentEtPassword.getText().toString());
            }
        });
        return root;
    }
}