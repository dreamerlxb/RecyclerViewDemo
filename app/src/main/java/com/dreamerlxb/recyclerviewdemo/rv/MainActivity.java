package com.dreamerlxb.recyclerviewdemo.rv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamerlxb.recyclerviewdemo.R;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rv);
        
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.content, MainFragment.newInstance("", ""));
        }
    }
}
