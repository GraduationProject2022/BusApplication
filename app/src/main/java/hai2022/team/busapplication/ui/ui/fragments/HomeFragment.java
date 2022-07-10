package hai2022.team.busapplication.ui.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.adapters.UserRecyclerviewAdapter;
import hai2022.team.busapplication.databases.firebase.Authentication;
import hai2022.team.busapplication.databases.firebase.Realtime;
import hai2022.team.busapplication.databinding.FragmentHomeBinding;
import hai2022.team.busapplication.interfaces.UserCallback;
import hai2022.team.busapplication.interfaces.UserListiner;
import hai2022.team.busapplication.models.User;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<User> admins, drivers, students;
    private UserRecyclerviewAdapter adminsAdapter, driversAdapter, studentsAdapter;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
    private RecyclerView.LayoutManager layoutManagerDrivers = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
    private RecyclerView.LayoutManager layoutManagerStudents = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
    private Realtime realtime;
    ProgressDialog progressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        realtime = new Realtime(getContext(), new UserListiner() {
            @Override
            public void ceateuser(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "User Removerd Successfully!!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getUser(User user) {

            }

            @Override
            public void getAdmins(ArrayList<User> users) {
                progressDialog.dismiss();
                adminsAdapter = new UserRecyclerviewAdapter(getContext(), users, R.layout.recyclerview_person, new UserCallback() {
                    @Override
                    public void user_click_listener(User user) {
                        UserDetailsFragment detailsFragment = new UserDetailsFragment();
                        Bundle b = new Bundle();
                        b.putSerializable("User", user);
                        detailsFragment.setArguments(b);
                        getFragmentManager().beginTransaction().add(R.id.MainActivity_layout_container, detailsFragment).commit();
                    }

                    @Override
                    public void remove_user(User user) {

                    }

                    @Override
                    public void add_new_user(String type) {
                        addUsers(type);
                    }
                });

                    binding.HomeFragmentRvAdmins.setAdapter(adminsAdapter);
                    if (binding.HomeFragmentRvAdmins.getLayoutManager() == null)
                    binding.HomeFragmentRvAdmins.setLayoutManager(layoutManager);
                    adminsAdapter.notifyDataSetChanged();



            }

            @Override
            public void getDrivers(ArrayList<User> users) {
                progressDialog.dismiss();
                driversAdapter = new UserRecyclerviewAdapter(getContext(), users, R.layout.recyclerview_person, new UserCallback() {
                    @Override
                    public void user_click_listener(User user) {
                        UserDetailsFragment detailsFragment = new UserDetailsFragment();
                        Bundle b = new Bundle();
                        b.putSerializable("User", user);
                        detailsFragment.setArguments(b);
                        getFragmentManager().beginTransaction().add(R.id.MainActivity_layout_container, detailsFragment).commit();
                    }

                    @Override
                    public void remove_user(User user) {
                        realtime.removeUser(user.getId(),user);
                        users.remove(user);
                        driversAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void add_new_user(String type) {
                        addUsers(type);
                    }
                });

                    binding.HomeFragmentRvDrivers.setAdapter(driversAdapter);
                if (binding.HomeFragmentRvDrivers.getLayoutManager() == null)
                    binding.HomeFragmentRvDrivers.setLayoutManager(layoutManagerDrivers);
                    driversAdapter.notifyDataSetChanged();
                    binding.HomeFragmentRvDrivers.setVisibility(View.VISIBLE);
                    binding.HomeFragmentTvDrivers.setVisibility(View.VISIBLE);
                    binding.HomeFragmentTvShowall2.setVisibility(View.VISIBLE);
                    binding.HomeFragmentBtnAdddrivers.setVisibility(View.GONE);

            }

            @Override
            public void getStudents(ArrayList<User> users) {
                progressDialog.dismiss();
                studentsAdapter = new UserRecyclerviewAdapter(getContext(), users, R.layout.recyclerview_person, new UserCallback() {
                    @Override
                    public void user_click_listener(User user) {
                        UserDetailsFragment detailsFragment = new UserDetailsFragment();
                        Bundle b = new Bundle();
                        b.putSerializable("User", user);
                        detailsFragment.setArguments(b);
                        getFragmentManager().beginTransaction().add(R.id.MainActivity_layout_container, detailsFragment).commit();
                    }

                    @Override
                    public void remove_user(User user) {

                    }

                    @Override
                    public void add_new_user(String type) {
                        addUsers(type);
                    }
                });

                    binding.HomeFragmentRvStudents.setAdapter(studentsAdapter);
                if (binding.HomeFragmentRvStudents.getLayoutManager() == null)
                    binding.HomeFragmentRvStudents.setLayoutManager(layoutManagerStudents);
                    studentsAdapter.notifyDataSetChanged();
                    binding.HomeFragmentRvStudents.setVisibility(View.VISIBLE);
                    binding.HomeFragmentTvStudents.setVisibility(View.VISIBLE);
                    binding.HomeFragmentTvShowall3.setVisibility(View.VISIBLE);
                    binding.HomeFragmentBtnAddstudents.setVisibility(View.GONE);


            }

            @Override
            public void updateUser(boolean state) {

            }
        });
        Authentication authentication = new Authentication();

        realtime.getUsers("admin");
        realtime.getUsers("driver");
        realtime.getUsers("student");


//        binding.HomeFragmentRvDrivers.setAdapter(driversAdapter);
//        binding.HomeFragmentRvDrivers.setLayoutManager(layoutManagerDrivers);
//        binding.HomeFragmentRvStudents.setAdapter(studentsAdapter);
//        binding.HomeFragmentRvStudents.setLayoutManager(layoutManagerStudents);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.HomeFragmentBtnAdddrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUsers("driver");
            }
        });

        binding.HomeFragmentBtnAddstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUsers("student");
            }
        });
    }

    private void addUsers(String type) {
        AddNewFragment addNewFragment = new AddNewFragment();
        Bundle b = new Bundle();
        b.putString("type", type);
        addNewFragment.setArguments(b);
        getFragmentManager().beginTransaction().add(R.id.MainActivity_layout_container, addNewFragment).addToBackStack("add").commit();
    }
}