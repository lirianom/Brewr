package com.example.richardje1.brewr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

/**
 * BrewListFragment creates and instantiates the activity that displays
 * the BrewList.
 *
 * Uses the friends array and userid to pass in to other classes.
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class BrewListFragment extends Fragment {

    private RecyclerView mBrewRecyclerView;
    private BrewAdapter mAdapter;
    private String[] friends;
    private String userID;
    private static int hasUpdated = 0;
    Handler h;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.brew_title);
    }

    public void setFriends(String[] f) {
        friends = Arrays.copyOf(f, f.length);
    }

    public void setUserID(String s) {
        userID = s;
    }

    /**
     * BrewHolder make up the small activities that get displayed on the homepage
     *
     * @Author Jacob Richard
     * @Version 1.0
     */
    private class BrewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mUserTextView;
        private Brew mBrew;

        public BrewHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_brew, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.brew_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.brew_date);
            mUserTextView = (TextView) itemView.findViewById(R.id.shown_user);
        }

        public void bind(Brew brew){
            mBrew = brew;
            mTitleTextView.setText(mBrew.getmTitle());
            mDateTextView.setText(mBrew.getmDate());
            mUserTextView.setText(mBrew.getmUserName());
        }

        @Override
        public void onClick(View view){
            Intent intent = BrewActivity.newIntent(getActivity(), mBrew.getId(), mBrew);
            startActivity(intent);
        }
    }

    //Used to attached Brews to list
    private class BrewAdapter extends RecyclerView.Adapter<BrewHolder> {
        private List<Brew> mBrews;

        public BrewAdapter(List<Brew> brews){
            mBrews = brews;
        }

        @Override
        public BrewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new BrewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BrewHolder holder, int position) {
            Brew brew = mBrews.get(position);
            holder.bind(brew);
        }

        @Override
        public int getItemCount() {
            return mBrews.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        h = new Handler();
        View view = inflater.inflate(R.layout.fragment_brew_list, container, false);
        mBrewRecyclerView = (RecyclerView) view
                        .findViewById(R.id.brew_recycler_view);
        mBrewRecyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity()));
        if(hasUpdated < 2) {
            updateUI();

        }
        //updateUI();


        return view;
    }

    //Updates the Page based on what BrewLab is gotten
    public void updateUI() {
        BrewLab brewLab = BrewLab.get(getActivity(), friends, userID);
        List<Brew> brews = brewLab.getBrews();
        mAdapter = new BrewAdapter(brews);
        mBrewRecyclerView.setAdapter(mAdapter);
        //hasUpdated++;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
