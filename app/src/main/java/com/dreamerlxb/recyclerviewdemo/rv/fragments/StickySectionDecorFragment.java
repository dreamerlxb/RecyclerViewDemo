package com.dreamerlxb.recyclerviewdemo.rv.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.entity.MarkType;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.SectionDecorAdapter;
import com.dreamerlxb.recyclerviewdemo.rv.decoration.StickyItemDecoration;
import com.dreamerlxb.recyclerviewdemo.service.MarkTypeService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickySectionDecorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickySectionDecorFragment extends SupportFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private SectionDecorAdapter<MarkType> sectionDecorAdapter;
    private List<MarkType> items;
    private List<MarkType> sections;

    public StickySectionDecorFragment() {
        // Required empty public constructor
    }

    public static StickySectionDecorFragment newInstance(String param1, String param2) {
        StickySectionDecorFragment fragment = new StickySectionDecorFragment();
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
        return inflater.inflate(R.layout.fragment_sticky_section_decor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = view.findViewById(R.id.section_deco_rv);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        sectionDecorAdapter = new SectionDecorAdapter<MarkType>(getContext());
        recyclerView.setAdapter(sectionDecorAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(getContext(),
                new StickyItemDecoration.StickyItemDecorationCb() {

                    @Override
                    public int getSectionId(int position) {
                        return sectionDecorAdapter.getSectionForPosition(position);
                    }

                    @Override
                    public Object getSectionItem(int position) {
                        MarkType mt = (MarkType) sectionDecorAdapter.getItemObject(position);
                        return mt.getTypeGroup();
                    }

                });
        recyclerView.addItemDecoration(stickyItemDecoration);

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        requestData();
    }

    private void requestData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MarkTypeService markTypeService = retrofit.create(MarkTypeService.class);
        Call<List<MarkType>> call = markTypeService.getMarkTypes("{\"order\": [\"typeGroupId\", \"id\"]}");
        call.enqueue(new Callback<List<MarkType>>() {
            @Override
            public void onResponse(Call<List<MarkType>> call, Response<List<MarkType>> response) {
                Log.i("==Success==", "return:" + response.toString());
                sections = new ArrayList<MarkType>();
                items = new ArrayList<MarkType>();

                for (MarkType markType : response.body()) {
                    if (markType.getTypeGroupId() == 0) {
                        sections.add(markType);
                    } else {
                        items.add(markType);
                    }
                }

                Iterator<MarkType> mtt = sections.iterator();
                MarkType sectionMt = mtt.next();
                mtt.remove();
                // 所有的数据按照 typeGroupId和Id排序：先按照typeGroupId拍戏，再按照id排序
                for (MarkType ma : items) {
                    if (sectionMt.getId() == ma.getTypeGroupId()) {
                        ma.setTypeGroup(sectionMt);
                    } else {
                        if (mtt.hasNext()) {
                            sectionMt = mtt.next();
                            mtt.remove();
                            ma.setTypeGroup(sectionMt);
                        }
                    }
                }

                sectionDecorAdapter.addItems(items);
            }

            @Override
            public void onFailure(Call<List<MarkType>> call, Throwable t) {
                Log.i("==Error==", "return:" + t.toString());
            }
        });
    }
}
