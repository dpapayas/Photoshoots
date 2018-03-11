package id.dpapayas.photoshoots.videocompressor;

import android.app.Application;

public class VideoCompressorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileUtils.createApplicationFolder();
    }

}