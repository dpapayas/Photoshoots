package id.dpapayas.photoshoots.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

import butterknife.Unbinder;
import id.dpapayas.photoshoots.R;

/**
 * Created by dpapayas on 9/11/17.
 */

public class RecentFragment extends Fragment {

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_featured, container, false);

        unbinder = ButterKnife.bind(this, v);


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
