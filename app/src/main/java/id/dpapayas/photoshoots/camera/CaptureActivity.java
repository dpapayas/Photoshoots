package id.dpapayas.photoshoots.camera;

import android.app.Fragment;
import android.support.annotation.NonNull;
import id.dpapayas.photoshoots.internal.BaseCaptureActivity;
import id.dpapayas.photoshoots.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

    @Override
    @NonNull
    public Fragment getFragment() {
        this.setCanShowGuide(this.showGuide());
        return CameraFragment.newInstance();
    }
}