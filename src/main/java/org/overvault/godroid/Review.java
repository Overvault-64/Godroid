package org.overvault.godroid;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Set;

public class Review extends GodotPlugin {
    public static final String PLUGIN_NAME = "GodroidReview";
    private final Activity activity;

    public Review(Godot godot) {
        super(godot);
        activity = godot.getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("review_flow_started"));
        signals.add(new SignalInfo("review_flow_finished"));
        signals.add(new SignalInfo("review_info_request_failed"));

        return signals;
    }

    @UsedByGodot
    public void inAppReview() {
        activity.runOnUiThread(() -> {
            ReviewManager manager = ReviewManagerFactory.create(activity);
            Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(requestTask -> {
                if (requestTask.isSuccessful()) {
                    emitSignal("review_flow_started");
                    // We can get the ReviewInfo object
                    ReviewInfo reviewInfo = requestTask.getResult();
                    Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                    flow.addOnCompleteListener(task -> {
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                        emitSignal("review_flow_finished");
                    });
                } else {
                    // There was some problem, continue regardless of the result.
                    emitSignal("review_info_request_failed");
                }
            });
        });
    }
}
