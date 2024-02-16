package de.storchp.opentracks.osmplugin.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();

    // Private constructor to hide the implicit public one
    private FileUtil() {
        // This constructor is private to prevent instantiation of FileUtil objects.
        // All methods in this class are static, so there's no need to create instances.
    }

    public static DocumentFile getDocumentFileFromTreeUri(Context context, Uri uri) {
        try {
            return DocumentFile.fromTreeUri(context, uri);
        } catch (Exception e) {
            Log.w(TAG, "Error getting DocumentFile from Uri: " + uri);
        }
        return null;
    }
}

