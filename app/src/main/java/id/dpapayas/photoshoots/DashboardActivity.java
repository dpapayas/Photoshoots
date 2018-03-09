package id.dpapayas.photoshoots;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.dpapayas.photoshoots.adapter.DashboardAdapter;
import id.dpapayas.photoshoots.model.ItemModel;

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
    DashboardAdapter adapter;
    private List<ItemModel> albumList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        albumList = new ArrayList<>();

        adapter = new DashboardAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rvGrid.setLayoutManager(mLayoutManager);
        rvGrid.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvGrid.setItemAnimator(new DefaultItemAnimator());
        rvGrid.setAdapter(adapter);

        prepareAlbums();

        rvGrid.addOnItemTouchListener(new RecyclerTouchListener(this,
                rvGrid, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Intent intent = new Intent(DashboardActivity.this, PreviewVideoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.images};

        ItemModel a = new ItemModel("Jokowi & JK", "12122014", covers[0]);
        albumList.add(a);

        ItemModel b = new ItemModel("Ahok Jarot", "12122014", covers[0]);
        albumList.add(b);

        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }
}
