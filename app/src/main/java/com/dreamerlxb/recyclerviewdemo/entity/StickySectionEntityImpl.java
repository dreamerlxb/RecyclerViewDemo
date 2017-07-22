package com.dreamerlxb.recyclerviewdemo.entity;

/**
 * Created by Administrator on 2017/3/18.
 */

public class StickySectionEntityImpl implements ISectionEntity {
    private int sectionId;
    private boolean isSection;
    private String title;

    public StickySectionEntityImpl(int sectionId, boolean isSection, String title) {
        this.sectionId = sectionId;
        this.isSection = isSection;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getSectionId() {
        return sectionId;
    }

    @Override
    public boolean isSection() {
        return isSection;
    }
}
