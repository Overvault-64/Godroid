package org.overvault.godroid;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.UsedByGodot;

public class Toast extends GodotPlugin {
    private final Activity activity;

    public Toast(Godot godot) {
        super(godot);
        activity = godot.getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodroidToast";
    }

    @UsedByGodot
    public void sendToast(final String mess) {
        activity.runOnUiThread(() -> android.widget.Toast.makeText(activity, mess, android.widget.Toast.LENGTH_LONG).show());
    }
}
