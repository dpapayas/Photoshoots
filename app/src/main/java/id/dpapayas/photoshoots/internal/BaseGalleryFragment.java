package id.dpapayas.photoshoots.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import id.dpapayas.photoshoots.R;
import id.dpapayas.photoshoots.util.CameraUtil;

public abstract class BaseGalleryFragment extends Fragment
    implements CameraUriInterface, View.OnClickListener {

  BaseCaptureInterface mInterface;
  int mPrimaryColor;
  String mOutputUri;
  View mControlsFrame;
  RelativeLayout mRetry;
  RelativeLayout mSaveStory;
  RelativeLayout mAddToStory;

  @SuppressWarnings("deprecation")
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    mInterface = (BaseCaptureInterface) activity;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (getActivity() != null)
      getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mOutputUri = getArguments().getString("output_uri");
    mControlsFrame = view.findViewById(R.id.controlsFrame);
//    mRetry = (Button) view.findViewById(R.id.retry);
    mRetry = (RelativeLayout) view.findViewById(R.id.retry);
    mSaveStory = (RelativeLayout) view.findViewById(R.id.save_story);

    if (CameraUtil.isColorDark(mPrimaryColor)) {
      final int textColor = ContextCompat.getColor(view.getContext(), R.color.mcam_color_light);
    } else {
      final int textColor = ContextCompat.getColor(view.getContext(), R.color.mcam_color_dark);
    }
    mRetry.setVisibility(
        getArguments().getBoolean(CameraIntentKey.ALLOW_RETRY, true) ? View.VISIBLE : View.GONE);
  }

  @Override
  public String getOutputUri() {
    return getArguments().getString("output_uri");
  }

  void showDialog(String title, String errorMsg) {
    new MaterialDialog.Builder(getActivity())
        .title(title)
        .content(errorMsg)
        .positiveText(android.R.string.ok)
        .show();
  }
}
