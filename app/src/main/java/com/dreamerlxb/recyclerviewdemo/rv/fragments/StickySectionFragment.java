package com.dreamerlxb.recyclerviewdemo.rv.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.entity.StickySectionEntityImpl;
import com.dreamerlxb.recyclerviewdemo.listener.RecyclerViewItemListener;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.StickySectionAdapter;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickySectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickySectionFragment extends SupportFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StickySectionAdapter stickySectionAdapter;
    private View stickyView; // 这里的stickyView时RecyclerView的section
    private TextView stickyTv;

    private int stickyHeaderHeight;
    private int rvCurrentPos = 0;


    public StickySectionFragment() {
        // Required empty public constructor
    }

    public static StickySectionFragment newInstance(String param1, String param2) {
        StickySectionFragment fragment = new StickySectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sticky_section, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Recycler View Sticky", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = view.findViewById(R.id.sticky_rv);
        stickyView = view.findViewById(R.id.sticky_header);
        stickyTv = stickyView.findViewById(R.id.sticky_section_item_txt);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutManager);

        stickySectionAdapter = new StickySectionAdapter(getContext(), MyData.getData2());
        recyclerView.setAdapter(stickySectionAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                stickyHeaderHeight = stickyView.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (stickySectionAdapter.isSection(rvCurrentPos + 1)) {
                    View view = linearLayoutManager.findViewByPosition(rvCurrentPos + 1);
                    if (view != null) {
                        if(view.getTop() < stickyHeaderHeight) {
                            stickyView.setY(-(stickyHeaderHeight - view.getTop()));
                        } else {
                            stickyView.setY(0);
                            updateStickyHeader(rvCurrentPos);
                        }
                    }
                }

                if (rvCurrentPos != linearLayoutManager.findFirstVisibleItemPosition()) {
                    rvCurrentPos = linearLayoutManager.findFirstVisibleItemPosition();
                    stickyView.setY(0);
                    updateStickyHeader(rvCurrentPos);
                }
            }
        });
    }

    private void updateStickyHeader(int pos) {
        int sec = stickySectionAdapter.getSectionForPosition(pos);
        Object se = stickySectionAdapter.getItemObject(sec);
        if (se != null) {
            StickySectionEntityImpl ssei = (StickySectionEntityImpl) se;
            stickyTv.setText(ssei.getTitle());
        }
    }
}
