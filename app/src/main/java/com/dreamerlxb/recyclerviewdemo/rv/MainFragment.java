package com.dreamerlxb.recyclerviewdemo.rv;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.rv.listener.OnItemClickListener;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.MainAdapter;
import com.dreamerlxb.recyclerviewdemo.rv.fragments.ExpandableSectionFragment;
import com.dreamerlxb.recyclerviewdemo.rv.fragments.HeaderFooterFragment;
import com.dreamerlxb.recyclerviewdemo.rv.fragments.LoadMoreFragment;
import com.dreamerlxb.recyclerviewdemo.rv.fragments.SectionFragment;
import com.dreamerlxb.recyclerviewdemo.rv.fragments.StickySectionDecorFragment;
import com.dreamerlxb.recyclerviewdemo.rv.fragments.StickySectionFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends SupportFragment implements OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView rv;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main_rv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        MainAdapter mainAdapter = new MainAdapter(getContext(), MyData.getRvData());
        mainAdapter.setItemClickListener(this);
        rv.setAdapter(mainAdapter);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                start(HeaderFooterFragment.newInstance("", ""));
                break;
            case 1:
                start(SectionFragment.newInstance("", ""));
                break;
            case 2:
                start(StickySectionFragment.newInstance("", ""));
                break;
            case 3:
                start(LoadMoreFragment.newInstance("", ""));
                break;
            case 4:
                start(StickySectionDecorFragment.newInstance("", ""));
                break;
            case 5:
                start(ExpandableSectionFragment.newInstance("", ""));
                break;
        }
    }
}
