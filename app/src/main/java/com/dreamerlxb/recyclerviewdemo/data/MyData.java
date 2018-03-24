package com.dreamerlxb.recyclerviewdemo.data;

import com.dreamerlxb.recyclerviewdemo.entity.SectionDecorEntity;
import com.dreamerlxb.recyclerviewdemo.entity.SectionEntityImpl;
import com.dreamerlxb.recyclerviewdemo.entity.StickySectionEntityImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sxb on 2017/3/16.
 */

public class MyData {

    public static List<String> getMainData() {
        List<String> list = new ArrayList<>();
        list.add("RecyclerView");
        list.add("Rxjava");
        list.add("Lottie");
        list.add("Notification");

        return list;
    }

    /**
     *  测试RecyclerView
     * @return List<String>
     */
    public static List<String> getRvData() {
        List<String> list = new ArrayList<>();
        list.add("Header Footer");
        list.add("Section");
        list.add("Sticky");
        list.add("Load More");
        list.add("Section Decoration");
        list.add("Expandable Section");
        list.add("Scale header");

        return list;
    }

    public static List<String> getTestData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Test" + i );
        }

        return list;
    }

    public static List<String> getTestGridData() {
        String [] strs = {"dsdsdcscsv", "sddsvsdvsdfddvsdvvsv", "sddsvsdvsdvsdsewfwfsdvvsv", "svsdvdvsdvvsv"};
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            int a = (int) (Math.random() * strs.length);
            list.add("Test" + i + strs[a]);
        }

        return list;
    }

    public static List<List<String>> getSectionGridData() {
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

    public static List<SectionEntityImpl> getSectionGridData2() {
        List<SectionEntityImpl> list = new ArrayList<>();
        list.add(new SectionEntityImpl("Section-0", true));
        list.add(new SectionEntityImpl("test-0-0"));
        list.add(new SectionEntityImpl("test-0-1"));
        list.add(new SectionEntityImpl("test-0-2"));
        list.add(new SectionEntityImpl("test-0-3"));
        list.add(new SectionEntityImpl("Section-1", true));
        list.add(new SectionEntityImpl("test-1-0"));
        list.add(new SectionEntityImpl("test-1-1"));
        list.add(new SectionEntityImpl("test-1-2"));
        list.add(new SectionEntityImpl("test-1-3"));
        list.add(new SectionEntityImpl("test-1-4"));
        list.add(new SectionEntityImpl("Section-2", true));
        list.add(new SectionEntityImpl("test-2-0"));
        list.add(new SectionEntityImpl("test-2-1"));
        list.add(new SectionEntityImpl("test-2-2"));
        list.add(new SectionEntityImpl("Section-3", true));
        list.add(new SectionEntityImpl("test-3-0"));
        list.add(new SectionEntityImpl("test-3-1"));
        list.add(new SectionEntityImpl("test-3-2"));
        list.add(new SectionEntityImpl("test-3-3"));
        list.add(new SectionEntityImpl("test-3-4"));
        list.add(new SectionEntityImpl("test-3-5"));
        list.add(new SectionEntityImpl("Section-4", true));
        list.add(new SectionEntityImpl("test-4-0"));
        list.add(new SectionEntityImpl("test-4-1"));
        list.add(new SectionEntityImpl("test-4-2"));
        list.add(new SectionEntityImpl("test-4-3"));
        return list;
    }


    public static List<SectionEntityImpl> getExpandableSectionData() {
        List<SectionEntityImpl> list = new LinkedList<>();
        list.add(new SectionEntityImpl("Section-0", true, getItems(0)));
        list.add(new SectionEntityImpl("Section-1", true, getItems(0)));
        list.add(new SectionEntityImpl("Section-2", true, getItems(0)));
        list.add(new SectionEntityImpl("Section-3", true, getItems(0)));
        list.add(new SectionEntityImpl("Section-4", true, getItems(0)));

        return list;
    }

    private static List<SectionEntityImpl> getItems(int id) {
        List<SectionEntityImpl> list = new ArrayList<>();
        list.add(new SectionEntityImpl("Sub Section-0", false));
        list.add(new SectionEntityImpl("Sub Section-1", false));
        list.add(new SectionEntityImpl("Sub Section-2", false));
        list.add(new SectionEntityImpl("Sub Section-3", false));
        list.add(new SectionEntityImpl("Sub Section-4", false));

        return list;
    }


    public static List<StickySectionEntityImpl> getData2() {
        List<StickySectionEntityImpl> list = new ArrayList<>();
        list.add(new StickySectionEntityImpl(0, true, "Section-0"));// 0
        list.add(new StickySectionEntityImpl(0, false, "Section-0-1")); // 1
        list.add(new StickySectionEntityImpl(0, false, "Section-0-2")); // 2
        list.add(new StickySectionEntityImpl(3, true, "Section-1")); // 3
        list.add(new StickySectionEntityImpl(3, false, "Section-1-0")); // 4
        list.add(new StickySectionEntityImpl(3, false, "Section-1-1")); // 5
        list.add(new StickySectionEntityImpl(3, false, "Section-1-2")); // 6
        list.add(new StickySectionEntityImpl(7, true, "Section-2")); // 7
        list.add(new StickySectionEntityImpl(7, false, "Section-2-0")); // 8
        list.add(new StickySectionEntityImpl(7, false, "Section-2-1")); // 9
        list.add(new StickySectionEntityImpl(10, true, "Section-3")); // 10
        list.add(new StickySectionEntityImpl(10, false, "Section-3-0")); // 11
        list.add(new StickySectionEntityImpl(10, false, "Section-3-1")); // 12
        list.add(new StickySectionEntityImpl(10, false, "Section-3-2")); // 13
        list.add(new StickySectionEntityImpl(10, false, "Section-3-3")); // 14
        list.add(new StickySectionEntityImpl(15, true, "Section-4")); // 15
        list.add(new StickySectionEntityImpl(15, false, "Section-4-0")); // 16
        return list;
    }

    public static List<SectionDecorEntity> getData3() {
        List<SectionDecorEntity> list = new ArrayList<>();
        list.add(new SectionDecorEntity(0, "Section-0", "Section-0-1")); // 1
        list.add(new SectionDecorEntity(0, "Section-0", "Section-0-2")); // 2
        list.add(new SectionDecorEntity(0, "Section-0", "Section-0-3"));// 0
        list.add(new SectionDecorEntity(3, "Section-1", "Section-1-0")); // 4
        list.add(new SectionDecorEntity(3, "Section-1", "Section-1-1")); // 5
        list.add(new SectionDecorEntity(3, "Section-1", "Section-1-2")); // 6
        list.add(new SectionDecorEntity(3, "Section-1", "Section-1-3")); // 3
        list.add(new SectionDecorEntity(7, "Section-2", "Section-2-0")); // 8
        list.add(new SectionDecorEntity(7, "Section-2", "Section-2-1")); // 9
        list.add(new SectionDecorEntity(7, "Section-2", "Section-2-2")); // 7
        list.add(new SectionDecorEntity(10, "Section-3", "Section-3-0")); // 11
        list.add(new SectionDecorEntity(10, "Section-3", "Section-3-1")); // 12
        list.add(new SectionDecorEntity(10, "Section-3", "Section-3-2")); // 13
        list.add(new SectionDecorEntity(10, "Section-3", "Section-3-3")); // 14
        list.add(new SectionDecorEntity(10, "Section-3", "Section-3-4")); // 10
        list.add(new SectionDecorEntity(15, "Section-4", "Section-4-0")); // 16
        list.add(new SectionDecorEntity(15, "Section-4", "Section-4-1")); // 15
        return list;
    }
}
