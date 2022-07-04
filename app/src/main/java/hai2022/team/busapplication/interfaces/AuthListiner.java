package hai2022.team.busapplication.interfaces;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface AuthListiner
{

    void Signup(@NonNull Task<AuthResult> task);
}
