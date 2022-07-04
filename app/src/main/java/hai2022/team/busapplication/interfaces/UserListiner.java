package hai2022.team.busapplication.interfaces;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import hai2022.team.busapplication.models.User;

public interface UserListiner {
    void ceateuser(@NonNull Task<Void> task);
    void getUser(User user);
    void getAdmins(ArrayList<User> users);
    void getDrivers(ArrayList<User> users);
    void getStudents(ArrayList<User> users);
    void updateUser(boolean state);
}
