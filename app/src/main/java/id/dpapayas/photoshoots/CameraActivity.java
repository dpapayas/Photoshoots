package id.dpapayas.photoshoots;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.dpapayas.photoshoots.camera.MaterialCamera;
import id.dpapayas.photoshoots.util.FilePaths;

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.path)
    TextView path;
    @BindView(R.id.btnUpload)
    Button btn_upload;
    @BindView(R.id.progress)
    ProgressBar pb;
    @BindView(R.id.percentage)
    TextView _status;
    private final static int CAMERA_RQ = 6969;
    private final static int DEFAULT_BITRATE = 1024000;
    private static final int RESULT_START_CAMERA = 4567;
    private static final int RESULT_START_VIDEO = 4589;
    private static final int RESULT_ADD_NEW_STORY = 7891;
    private TextView file_path_text;

    public static final String TAG = "CameraActivity";

    private int mStartType = RESULT_START_VIDEO;

    AmazonS3Client s3;
    BasicAWSCredentials credentials;
    TransferUtility transferUtility;
    TransferObserver observer;

    String key = "YFGGC6GCT6I6WT3SWIPO";
    String secret = "8ACm2O8JY4tcZeklNIEBET90oYEr+LRWLZ6+mpxpWv4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                        && ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                        && ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//                        && ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                    startRecordVideo();
//                } else {
//                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.CAMERA,
//                            Manifest.permission.RECORD_AUDIO}, CHECK_PERMISSION);
//                }
//
//            }
//        });


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                try {
//                    UploadFile();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InvalidKeyException e) {
//                    e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                }


//                credentials = new BasicAWSCredentials(key, secret);
//                s3 = new AmazonS3Client(credentials);
//                transferUtility = new TransferUtility(s3, CameraActivity.this);
//
//
//                File file = new File(file_path_text.getText().toString());
//                if (!file.exists()) {
//                    Toast.makeText(CameraActivity.this, "File Not Found!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                observer = transferUtility.upload(
//                        "sgp1.digitaloceanspaces.com",
//                        "Test_Video",
//                        file
//                );
//
//                observer.setTransferListener(new TransferListener() {
//                    @Override
//                    public void onStateChanged(int id, TransferState state) {
//
//                        if (state.COMPLETED.equals(observer.getState())) {
//
//                            Toast.makeText(CameraActivity.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//
//
//                        long _bytesCurrent = bytesCurrent;
//                        long _bytesTotal = bytesTotal;
//
//                        float percentage = ((float) _bytesCurrent / (float) _bytesTotal * 100);
//                        Log.d("percentage", "" + percentage);
//                        pb.setProgress((int) percentage);
//                        _status.setText(percentage + "%");
//                    }
//
//                    @Override
//                    public void onError(int id, Exception ex) {
//
//                        Toast.makeText(CameraActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });

    }

    private void init() {
        Log.d(TAG, "init: initializing material camera.");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

//        File saveFolder = new File(Environment.getExternalStorageDirectory(), "Stories/" + getTimestamp());
        FilePaths filePaths = new FilePaths();
        File saveFolder = new File(filePaths.STORIES);
        try {
            if (!saveFolder.mkdirs()) ;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        MaterialCamera mMaterialCamera = new MaterialCamera(this);                               // Constructor takes an Activity

        if (mStartType == RESULT_START_VIDEO) {
            Log.d(TAG, "init: starting camera with VIDEO enabled.");
            mMaterialCamera
                    .allowRetry(true)                                  // Whether or not 'Retry' is visible during playback
                    .autoSubmit(false)                                 // Whether or not user is allowed to playback videos after recording. This can affect other things, discussed in the next section.
                    .saveDir(saveFolder)                               // The folder recorded videos are saved to
//                .primaryColorAttr(R.attr.colorPrimary)             // The theme color used for the camera, defaults to colorPrimary of Activity in the constructor
                    .showPortraitWarning(false)                         // Whether or not a warning is displayed if the user presses record in portrait orientation
                    .defaultToFrontFacing(false)                       // Whether or not the camera will initially show the front facing camera
//                .allowChangeCamera(true)                           // Allows the user to change cameras.
                    .retryExits(false)                                 // If true, the 'Retry' button in the playback screen will exit the camera instead of going back to the recorder
                    .restartTimerOnRetry(false)                        // If true, the countdown timer is reset to 0 when the user taps 'Retry' in playback
                    .continueTimerInPlayback(false)                    // If true, the countdown timer will continue to go down during playback, rather than pausing.
                    //Play with the encoding bitrate to change quality and size.
                    .videoEncodingBitRate(DEFAULT_BITRATE * 5)                     // Sets a custom bit rate for video recording.
                    .audioEncodingBitRate(50000)                       // Sets a custom bit rate for audio recording.
                    .videoFrameRate(30)                                // Sets a custom frame rate (FPS) for video recording.
//                    .qualityProfile(MaterialCamera.QUALITY_1080P)       // Sets a quality profile, manually setting bit rates or frame rates with other settings will overwrite individual quality profile settings
                    .videoPreferredHeight(720)                         // Sets a preferred height for the recorded video output.
                    .videoPreferredAspect(16f / 9f)                     // Sets a preferred aspect ratio for the recorded video output.
                    .maxAllowedFileSize(1024 * 1024 * 40)               // Sets a max file size of 4MB, recording will stop if file reaches this limit. Keep in mind, the FAT file system has a file size limit of 4GB.
                    .iconRecord(R.drawable.mcam_action_capture)        // Sets a custom icon for the button used to start recording
                    .iconStop(R.drawable.mcam_action_stop)             // Sets a custom icon for the button used to stop recording
                    .iconFrontCamera(R.drawable.mcam_camera_front)     // Sets a custom icon for the button used to switch to the front camera
                    .iconRearCamera(R.drawable.mcam_camera_rear)       // Sets a custom icon for the button used to switch to the rear camera
                    .iconPlay(R.drawable.evp_action_play)              // Sets a custom icon used to start playback
                    .iconPause(R.drawable.evp_action_pause)            // Sets a custom icon used to pause playback
                    .iconRestart(R.drawable.evp_action_restart)        // Sets a custom icon used to restart playback
                    .labelRetry(R.string.mcam_retry)                   // Sets a custom button label for the button used to retry recording, when available
                    .labelConfirm(R.string.mcam_use_video)             // Sets a custom button label for the button used to confirm/submit a recording
//                .autoRecordWithDelaySec(5)                         // The video camera will start recording automatically after a 5 second countdown. This disables switching between the front and back camera initially.
//                .autoRecordWithDelayMs(5000)                       // Same as the above, expressed with milliseconds instead of seconds.
                    .audioDisabled(false)                              // Set to true to record video without any audio.
                    .countdownSeconds(15f)
                    .start(CAMERA_RQ);
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case CAMERA_RQ:
//                    try {
//                        String filePath = intent.getStringExtra("videoUrl");
//                        Log.e("lzf_video", filePath);
//                        if (filePath != null && !filePath.equals("")) {
//                            if (filePath.startsWith("file://")) {
//                                filePath = intent.getStringExtra("videoUrl").substring(7, filePath.length());
//                                file_path_text.setText(filePath);
//                            }
//                        }
//                    } catch (Exception ex) {
//
//                    }
//                    break;
//            }
//        }
//    }


    @OnClick(R.id.btnUpload)
    public void onViewClicked() {
    }

//    public void UploadFile() throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {
//        try {
//            MinioClient minioClient = new MinioClient("sgp1.digitaloceanspaces.com", key, secret);
//
//            boolean isExist = minioClient.bucketExists("teststrip");
//            if (isExist) {
//                System.out.println("Bucket already exists.");
//            } else {
//                minioClient.makeBucket("teststrip");
//            }
//
//            minioClient.putObject("teststrip", "Test_Video", file_path_text.getText().toString());
//        } catch (MinioException e) {
//            System.out.println("Error occurred: " + e);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: incoming result.");
        // Received recording or error from MaterialCamera
        if (requestCode == CAMERA_RQ) {

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: result is OK.");
//                Toast.makeText(this, "Saved to: " + data.getDataString(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_START_CAMERA) {
                Log.d(TAG, "onActivityResult: got activity result. Opening Camera.");
                mStartType = RESULT_START_CAMERA;
                init();
            } else if (resultCode == RESULT_START_VIDEO) {
                Log.d(TAG, "onActivityResult: got activity result. Opening video.");
                mStartType = RESULT_START_VIDEO;
                init();
            } else if (resultCode == RESULT_ADD_NEW_STORY) {
                Log.d(TAG, "onActivityResult: preparing to add new story.");
                Log.d(TAG, "onActivityResult: upload uri: " + data.getData());
                if (data.hasExtra(MaterialCamera.DELETE_UPLOAD_FILE_EXTRA)) {
                    setResult(
                            RESULT_ADD_NEW_STORY,
                            getIntent()
                                    .putExtra(MaterialCamera.DELETE_UPLOAD_FILE_EXTRA, true)
                                    .putExtra(MaterialCamera.STATUS_EXTRA, MaterialCamera.STATUS_RECORDED)
                                    .setDataAndType(data.getData(), data.getType()));
                    finish();
                } else {
                    setResult(
                            RESULT_ADD_NEW_STORY,
                            getIntent()
                                    .putExtra(MaterialCamera.DELETE_UPLOAD_FILE_EXTRA, false)
                                    .putExtra(MaterialCamera.STATUS_EXTRA, MaterialCamera.STATUS_RECORDED)
                                    .setDataAndType(data.getData(), data.getType()));
                    finish();
                }


            } else if (data != null) {
                Log.d(TAG, "onActivityResult: something went wrong.");
                Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
