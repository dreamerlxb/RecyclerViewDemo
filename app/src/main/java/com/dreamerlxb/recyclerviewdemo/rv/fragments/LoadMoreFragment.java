package com.dreamerlxb.recyclerviewdemo.rv.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.LoadMoreAdapter;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.rv.decoration.LoadMoreDividerItemDecoration;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoadMoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadMoreFragment extends SupportFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    public LoadMoreFragment() {

    }

    public static LoadMoreFragment newInstance(String param1, String param2) {
        LoadMoreFragment fragment = new LoadMoreFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        recyclerView = view.findViewById(R.id.load_more_rv);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LoadMoreDividerItemDecoration(getContext()));

        final NormalAdapter adapter = new NormalAdapter(getContext(), MyData.getTestGridData());
        final LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(recyclerView, adapter);
        loadMoreAdapter.setLoadingMoreView(getLayoutInflater().inflate(R.layout.load_more_item, recyclerView, false));
        loadMoreAdapter.setLoadMoreNoDataView(getLayoutInflater().inflate(R.layout.load_more_no_data, recyclerView, false));
        loadMoreAdapter.setLoadMoreListener(new LoadMoreAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> strs = MyData.getTestData();
                        adapter.addItems(strs);
                        loadMoreAdapter.notifyItemRangeInserted(adapter.getItemCount() - strs.size(), strs.size());
                        loadMoreAdapter.loadMoreFinish(false);
                    }
                }, 2000);
            }
        });
        recyclerView.setAdapter(loadMoreAdapter);
    }
}
