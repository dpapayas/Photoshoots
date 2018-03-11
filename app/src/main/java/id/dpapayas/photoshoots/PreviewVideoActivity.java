package id.dpapayas.photoshoots;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dpapayas on 3/9/18.
 */

public class PreviewVideoActivity extends AppCompatActivity {
    @BindView(R.id.record_back)
    ImageView recordBack;
    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.record_play)
    ImageView recordPlay;
    @BindView(R.id.videoViewSnd)
    VideoView videoViewSnd;
    @BindView(R.id.card_view)
    CardView cardView;

    String url_video = "https://firebasestorage.googleapis.com/v0/b/hopemon-go.appspot.com/o/VID_20180309_004810.mp4?alt=media&token=ee81e89d-1957-49be-a520-fe4085b1c967";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_video);
        ButterKnife.bind(this);

        Uri uri = Uri.parse(url_video);

        if (uri != null && !uri.equals("")) {
            videoView.setVideoURI(uri);
            videoView.start();
        } else {
            Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
        }
    }
}
