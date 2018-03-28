package id.dpapayas.photoshoots;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.dpapayas.photoshoots.util.CenteredToolbar;

/**
 * Created by dpapayas on 3/10/18.
 */

public class PreviewStatusActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.swFacebook)
    Switch swFacebook;
    @BindView(R.id.swTwitter)
    Switch swTwitter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_status);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_chevron_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Preview");

        Intent intent = getIntent();
        getSupportActionBar().setTitle("App Key");
        String temp = intent.getStringExtra("filepath");

        Glide.with(getApplicationContext())
                .asBitmap()
                .load("https://4.bp.blogspot.com/-JTjWScQZ2pE/V2zIbjRaqcI/AAAAAAAAACg/SOH7Slsp3rwoC5crLTLRdVdech964ncSwCLcB/s1600/penyuluhan.jpg")
                .into(ivPreview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_submit:
                Intent intent = new Intent(PreviewStatusActivity.this, DashboardActivity.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
