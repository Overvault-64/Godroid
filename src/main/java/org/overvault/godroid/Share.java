package org.overvault.godroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.UsedByGodot;

import java.io.File;


public class Share extends GodotPlugin {
    private static final String TAG = "godot";
    private final Activity activity;

    public Share(Godot godot) {
        super(godot);
        activity = godot.getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodroidShare";
    }

    @UsedByGodot
    public void shareText(String title, String subject, String text) {
        Log.d(TAG, "shareText called");
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        activity.startActivity(Intent.createChooser(sharingIntent, title));
    }

    @UsedByGodot
    public void shareFile(String path, String fileType, String title) {
        Log.d(TAG, "shareFile called with " + fileType);
        Log.d(TAG, "received path: " + path);

        File f = new File(path);
        Uri uri;
        try {
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", f);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, String.valueOf(e));
            return;
        }
        Log.d(TAG, "uri: " + uri);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(fileType + "/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(share, title));
    }
}
