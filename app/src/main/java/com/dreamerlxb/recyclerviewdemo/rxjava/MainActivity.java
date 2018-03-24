package com.dreamerlxb.recyclerviewdemo.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.listener.OnItemClickListener;
import com.dreamerlxb.recyclerviewdemo.rxjava.adatpers.MainAdapter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxjava);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.content, MainFragment.newInstance("", ""));
        }


//        Flowable.interval( 1 , TimeUnit.MILLISECONDS)
//                .onBackpressureDrop() //onBackpressureDrop 一定要放在 interval 后面否则不会生效
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Thread.sleep(1000);
//                        Log.i("zhao", "onNext: " + aLong);
//                    }
//                });



//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 100; i++) {  //只发1w个事件
//                    emitter.onNext(i);
//                }
//            }
//        }, BackpressureStrategy.DROP)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        Log.i("Flowable", "onSubscribe");
//                        // mSubscription = s;
//                        // s.request(128);  //一开始就处理掉128个事件
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i("Flowable", "onNext: " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        Log.i("Flowable", "onError: ", t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i("Flowable", "onComplete");
//                    }
//                });
    }
}
