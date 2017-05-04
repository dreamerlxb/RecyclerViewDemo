package com.dreamerlxb.recyclerviewdemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dreamerlxb.recyclerviewdemo.adapter.SectionDecorAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.decoration.StickyItemDecoration;
import com.dreamerlxb.recyclerviewdemo.entity.MarkType;
import com.dreamerlxb.recyclerviewdemo.service.MarkTypeService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StickySectionDecorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SectionDecorAdapter sectionDecorAdapter;
    private List<MarkType> items;
    private List<MarkType> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_decoration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.section_deco_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        sectionDecorAdapter = new SectionDecorAdapter(this);
        recyclerView.setAdapter(sectionDecorAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(this,
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
                sectionDecorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MarkType>> call, Throwable t) {
                Log.i("==Error==", "return:" + t.toString());
            }
        });
    }
}
