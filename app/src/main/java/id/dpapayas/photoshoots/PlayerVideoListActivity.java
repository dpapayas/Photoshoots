package id.dpapayas.photoshoots;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.dpapayas.photoshoots.adapter.ProfileSlideAdapter;
import id.dpapayas.photoshoots.model.Image;
import id.dpapayas.photoshoots.util.CenteredToolbar;
import id.dpapayas.photoshoots.util.SpacesItemDecoration;

/**
 * Created by dpapayas on 3/12/18.
 */

public class PlayerVideoListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.player_view)
    SimpleExoPlayerView simpleExoPlayerView;

    private Uri mUri;

    private String TAG = ProfileActivity.class.getSimpleName();
    private static final String endpoint = "https://api.androidhive.info/json/glide.json";

    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;

    String url_video = "https://conz.sgp1.digitaloceanspaces.com/mp4/potrait.mp4";
    String url_image = "https://conz.sgp1.digitaloceanspaces.com/thumbnails/vid_4.png";


    Unbinder unbinder;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ProfileSlideAdapter galleryAdapter;
    LinearLayoutManager horizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;

    List<Image> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_chevron_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewLayoutManager = new LinearLayoutManager(this);

        recyclerview1.setLayoutManager(recyclerViewLayoutManager);

        fetchImages();

        galleryAdapter = new ProfileSlideAdapter(this, list);

        horizontalLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview1.setLayoutManager(horizontalLayout);


        recyclerview1.setAdapter(galleryAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen._1sdp);
        recyclerview1.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        // Adding on item click listener to RecyclerView.
        recyclerview1.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(PlayerVideoListActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting clicked value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);


                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        window = new Timeline.Window();

        initializePlayer();
    }

    private void initializePlayer() {

        simpleExoPlayerView.requestFocus();

//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//
//        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//        simpleExoPlayerView.setPlayer(player);
//
//        player.setPlayWhenReady(shouldAutoPlay);
////        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
////        MediaSource mediaSource = new HlsMediaSource(Uri.parse(url_video),
////                mediaDataSourceFactory, null, null);
//
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url_video),
//                mediaDataSourceFactory, extractorsFactory, null, null);
//
//        player.prepare(mediaSource);

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            simpleExoPlayerView.setPlayer(player);
            player.setPlayWhenReady(shouldAutoPlay);
        }

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url_video),
                mediaDataSourceFactory, extractorsFactory, null, null);

        player.prepare(mediaSource, true, false);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

    }


    private void fetchImages() {

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                JSONObject url = object.getJSONObject("url");
                                image.setSmall(url.getString("small"));
                                image.setMedium(url.getString("medium"));
                                image.setLarge(url.getString("large"));
                                image.setTimestamp(object.getString("timestamp"));

                                list.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        galleryAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }

//    private void initializePlayer() {
//
//        playerView.requestFocus();
//
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//
//        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//        playerView.setPlayer(player);
//
//        player.setPlayWhenReady(shouldAutoPlay);
////        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
////        MediaSource mediaSource = new HlsMediaSource(Uri.parse(url_video),
////                mediaDataSourceFactory, null, null);
//
//        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url_video),
//                mediaDataSourceFactory, extractorsFactory, null, null);
//
//        player.prepare(mediaSource);
//
//    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
//            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
//            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
//            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
//            releasePlayer();
        }
    }
}
