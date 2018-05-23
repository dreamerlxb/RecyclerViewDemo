package com.dreamerlxb.recyclerviewdemo.rxjava.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObservableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObservableFragment extends SupportFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar;

    public ObservableFragment() {
        // Required empty public constructor
    }

    public static ObservableFragment newInstance(String param1, String param2) {
        ObservableFragment fragment = new ObservableFragment();
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
        return inflater.inflate(R.layout.fragment_observable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        Observable.just("包子", "馒头", "肠粉", "春卷", "饺子", "炒粉")
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        Log.i("TAG", "test: " + s);
//                        return s.equals("饺子");//只允许饺子通过测试
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    //accept可以简单的看做onNext
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.d("TAG", "accept: " + s);//这里只能吃上饺子
//                    }
//                });
    }
}
