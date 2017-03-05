package com.example.richardje1.brewr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by richardje1 on 2/24/17.
 */

public class ScreenSlidePageFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(android.R.id.text1))
                .setText(
                Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }

}

