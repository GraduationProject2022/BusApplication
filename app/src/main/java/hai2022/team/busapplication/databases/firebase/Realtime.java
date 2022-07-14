package hai2022.team.busapplication.databases.firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hai2022.team.busapplication.R;
import hai2022.team.busapplication.interfaces.UserListiner;
import hai2022.team.busapplication.models.User;

public class Realtime {
    FirebaseDatabase database;
    DatabaseReference myRef;
    UserListiner userListiner;

    Context context;

    public Realtime(Context context, UserListiner userListiner) {
        this.context = context;
        this.userListiner = userListiner;
        this.database = FirebaseDatabase.getInstance("https://busapplication-f0066-default-rtdb.asia-southeast1.firebasedatabase.app/");
        this.myRef = database.getReference("");
    }

    public void createUser(String uid, User user) {
        Toast.makeText(context, "test" + uid, Toast.LENGTH_SHORT).show();
// Write a message to the database
        myRef.child("users").child(user.getType()).child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userListiner.ceateuser(task);
            }
        });
    }

    public void removeUser(String uid, User user) {
        Toast.makeText(context, "test" + uid, Toast.LENGTH_SHORT).show();
// Write a message to the database
        myRef.child("users").child(user.getType()).child(uid).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userListiner.ceateuser(task);
            }
        });
    }

    public void getUser(String type, String id) {
        myRef.child("users").child(type).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userListiner.getUser(snapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUsers(String type) {
        ArrayList<User> users = new ArrayList<>();

        myRef.child("users").child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.removeAll(users);
                users.add(new User("", R.drawable.ic_baseline_home_24, "Add new " + type, ""));

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    users.add(snapshot1.getValue(User.class));
                }

                if (type.equalsIgnoreCase("admin"))
                    userListiner.getAdmins(users);
                else if (type.equalsIgnoreCase("driver"))
                    userListiner.getDrivers(users);
                else
                    userListiner.getStudents(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void updateUser(User user) {
        myRef.child("users").child(user.getType()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        userListiner.updateUser(task.isSuccessful());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
