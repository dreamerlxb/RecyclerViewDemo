package com.dreamerlxb.recyclerviewdemo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxb on 2017/3/16.
 */

public class MyData {
    public static List<String> getNormalData() {
        List<String> list = new ArrayList<>();
        list.add("Header And Footer");
        list.add("Section");
        list.add("Sticky");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");

        list.add("RecyclerView添加Header和Footer");
        list.add("RecyclerView添加Header和Footer");

        return list;
    }

    public static List<String> getTestData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            list.add("Test" + i );
        }

        return list;
    }

    public static List<String> getTestGridData() {
        String [] strs = {"dsdsdcscsv", "sddsvsdvsdfddvsdvvsv", "sddsvsdvsdvsdsewfwfsdvvsv", "svsdvdvsdvvsv"};
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int a = (int) (Math.random() * strs.length);
            list.add("Test" + i + strs[a]);
        }

        return list;
    }

    public static List<List<String>> getSectionGridData() {
//        String [] strs = {"dsdsdcscsv", "sddsvsdvsdfddvsdvvsv", "sddsvsdvsdvsdsewfwfsdvvsv", "svsdvdvsdvvsv"};
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int a = (int) (Math.random() * 10);
            List<String> al = new ArrayList<>();
            for (int j = 0; j < a; j++) {
                al.add("test-" + i + "-" + j);
            }
            list.add(al);
        }

        return list;
    }

    public static List<com.dreamerlxb.recyclerviewdemo.entity.SectionEntity> getSectionGridData2() {
        List<com.dreamerlxb.recyclerviewdemo.entity.SectionEntity> list = new ArrayList<>();
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("Section-0", true));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-0-0"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-0-1"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-0-2"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-0-3"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("Section-1", true));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-1-0"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-1-1"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-1-2"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-1-3"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-1-4"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("Section-2", true));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-2-0"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-2-1"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-2-2"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("Section-3", true));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-3-0"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-3-1"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-3-2"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-3-3"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-3-4"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-3-5"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("Section-4", true));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-4-0"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-4-1"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-4-2"));
        list.add(new com.dreamerlxb.recyclerviewdemo.entity.SectionEntity("test-4-3"));

        return list;
    }
}
