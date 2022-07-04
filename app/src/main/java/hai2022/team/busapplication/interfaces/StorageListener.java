package hai2022.team.busapplication.interfaces;

import android.net.Uri;

public interface StorageListener {
    void onDownloadImageListener(Uri uri);
    void onUploadImageListener(boolean status);

}
