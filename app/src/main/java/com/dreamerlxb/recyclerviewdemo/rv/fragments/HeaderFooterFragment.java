package com.dreamerlxb.recyclerviewdemo.rv.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.HeaderAndFooterAdapter;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.rv.decoration.DividerGridItemDecoration;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderFooterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderFooterFragment extends SupportFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    public HeaderFooterFragment() {
        // Required empty public constructor
    }

    public static HeaderFooterFragment newInstance(String param1, String param2) {
        HeaderFooterFragment fragment = new HeaderFooterFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header_footer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Header Footer");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "RecyclerView Header and Footer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.header_footer_rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        // 自定义
        NormalAdapter adapter = new NormalAdapter(getContext(), MyData.getTestGridData());
        final HeaderAndFooterAdapter headerAdapter = new HeaderAndFooterAdapter(getContext(), adapter);
        headerAdapter.addHeader(this.getLayoutInflater().inflate(R.layout.recycler_header_item, null));
        headerAdapter.addHeader(this.getLayoutInflater().inflate(R.layout.recycler_header_item, null));
        headerAdapter.addFooter(this.getLayoutInflater().inflate(R.layout.recycler_header_item, null));
        recyclerView.setAdapter(headerAdapter);
    }
}
