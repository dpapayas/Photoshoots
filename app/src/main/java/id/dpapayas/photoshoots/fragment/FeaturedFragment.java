package id.dpapayas.photoshoots.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.dpapayas.photoshoots.AppController;
import id.dpapayas.photoshoots.DashboardActivity;
import id.dpapayas.photoshoots.ProfileActivity;
import id.dpapayas.photoshoots.R;
import id.dpapayas.photoshoots.adapter.GalleryAdapter;
import id.dpapayas.photoshoots.adapter.ProfileSlideAdapter;
import id.dpapayas.photoshoots.model.Image;
import id.dpapayas.photoshoots.util.SpacesItemDecoration;

/**
 * Created by dpapayas on 9/11/17.
 */

public class FeaturedFragment extends Fragment {

    private String TAG = ProfileActivity.class.getSimpleName();
    private static final String endpoint = "https://api.androidhive.info/json/glide.json";

    Unbinder unbinder;
    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;

    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ProfileSlideAdapter galleryAdapter;
    LinearLayoutManager horizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;

    List<Image> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_featured, container, false);

        unbinder = ButterKnife.bind(this, v);

        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerview1.setLayoutManager(recyclerViewLayoutManager);

        fetchImages();

        galleryAdapter = new ProfileSlideAdapter(getActivity(), list);

        horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview1.setLayoutManager(horizontalLayout);

        recyclerview1.setAdapter(galleryAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen._1sdp);
        recyclerview1.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        // Adding on item click listener to RecyclerView.
        recyclerview1.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

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


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
