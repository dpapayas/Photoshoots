package id.dpapayas.photoshoots.internal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import id.dpapayas.photoshoots.R;

public class PreviewFragment extends Fragment implements View.OnClickListener, CameraUriInterface {

    private VideoView videoView;
    private String uri;
    private ImageView play;
    //    private int type;
    private BaseCaptureInterface mInterface;

    public static PreviewFragment newInstance(String outputUri, boolean allowRetry, int primaryColor) {
        final PreviewFragment fragment = new PreviewFragment();
        fragment.setRetainInstance(true);
        Bundle args = new Bundle();
//        args.putString("output_uri", outputUri);
//        args.putInt("type", type);
        args.putString("output_uri", outputUri);
        args.putBoolean(CameraIntentKey.ALLOW_RETRY, allowRetry);
        args.putInt(CameraIntentKey.PRIMARY_COLOR, primaryColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uri = getArguments().getString("output_uri");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_record, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        ImageView back = (ImageView) view.findViewById(R.id.record_back);
        TextView next = (TextView) view.findViewById(R.id.record_next);
        play = (ImageView) view.findViewById(R.id.record_play);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        play.setOnClickListener(this);
        mInterface = (BaseCaptureInterface) getActivity();
        if (uri != null && !uri.equals("")) {
            videoView.setVideoURI(Uri.parse(uri));
            videoView.start();
        } else {
            Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
        }
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.record_back) {
            hideFragment();
        } else if (id == R.id.record_play) {
            if (uri != null && !uri.equals("")) {
                videoView.setVideoURI(Uri.parse(uri));
                videoView.start();
                play.setVisibility(View.GONE);
            } else {
                Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.record_next) {
            Intent intent = new Intent();
            intent.putExtra("videoUrl", uri);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void hideFragment() {
        new MaterialDialog.Builder(getActivity())
                .title("Test")
                .content("Test Content？")
                .positiveText("Yes")
                .negativeText("No")
                .positiveColor(R.color.blue_primary)
                .negativeColor(R.color.red)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mInterface.onRetry(getOutputUri());
                    }
                })
                .show();
    }

    @Override
    public String getOutputUri() {
        return uri;
    }
}