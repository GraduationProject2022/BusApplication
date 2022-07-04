package hai2022.team.busapplication.databases.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hai2022.team.busapplication.interfaces.StorageListener;

public class CloudStorage {
    private StorageReference storageReference;
    private StorageListener listener;

    public CloudStorage(StorageListener listener) {
        this.listener = listener;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public CloudStorage() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }


    public void upload(String MainFolder, String SubFolder, String username, Uri uri) {
        storageReference.child(MainFolder).child(SubFolder).child(username + uri.getLastPathSegment()).putFile(uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("UPLOAD", e.getMessage());
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                listener.onUploadImageListener(task.isSuccessful());
            }
        });
    }

    public void download(String path) {
        // Create a reference with an initial file path and name
        StorageReference pathReference = storageReference.child(path);

        pathReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    listener.onDownloadImageListener(task.getResult());
                }
            }
        });

    }


}
