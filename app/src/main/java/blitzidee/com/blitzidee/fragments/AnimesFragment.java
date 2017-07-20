package blitzidee.com.blitzidee.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import blitzidee.com.blitzidee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimesFragment extends Fragment {


    public AnimesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animes, container, false);
    }

}
