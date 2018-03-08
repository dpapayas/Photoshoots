package id.dpapayas.photoshoots;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dpapayas on 3/8/18.
 */

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.layHeadLeft)
    RelativeLayout layHeadLeft;
    @BindView(R.id.layHeadRight)
    RelativeLayout layHeadRight;
    @BindView(R.id.layHeader)
    RelativeLayout layHeader;
    @BindView(R.id.rvGrid)
    RecyclerView rvGrid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.layHeadLeft, R.id.layHeadRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layHeadLeft:
                break;
            case R.id.layHeadRight:
                Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
        }
    }
}
