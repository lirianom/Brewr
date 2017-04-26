package com.example.richardje1.brewr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

import static android.R.attr.fragment;

/**
 * Created by richardje1 on 3/5/17.
 */

public class BrewFragment extends Fragment {
    private Brew mBrew;
    private TextView mTitleField;
    private TextView mBrewDate;
    private TextView mUsername;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String EXTRA_BREW_ID = "brewr.CRIME_ID";
    private static final String ARG_BREW_ID = "brew_id";

    public static BrewFragment newInstance(UUID brewId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BREW_ID, brewId);

        BrewFragment fragment = new BrewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID brewId = (UUID) getActivity().getIntent()
                .getSerializableExtra(BrewActivity.EXTRA_BREW_ID);
        mBrew = BrewLab.get(getActivity()).getBrew(brewId);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_brew, parent, false);
        mTitleField = (TextView)v.findViewById(R.id.brew_title);
        mTitleField.setText(mBrew.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //todo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBrew.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //todo
            }
        });
        mBrewDate = (TextView)v.findViewById(R.id.brew_date);
        mUsername = (TextView)v.findViewById(R.id.username);
        return v;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */



}
 // 153