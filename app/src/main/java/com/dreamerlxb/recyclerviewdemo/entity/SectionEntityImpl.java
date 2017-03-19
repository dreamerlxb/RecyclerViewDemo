package com.dreamerlxb.recyclerviewdemo.entity;

/**
 * Created by sxb on 2017/3/17.
 */

public class SectionEntityImpl implements SectionEntity{
    private String title;
    private boolean isHeader;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SectionEntityImpl(String title) {
        this.title = title;
        this.isHeader = false;
    }

    public SectionEntityImpl(String title, boolean isHeader) {
        this.title = title;
        this.isHeader = isHeader;
    }

    @Override
    public int getSectionId() {
        return 0;
    }

    @Override
    public boolean isSection() {
        return isHeader;
    }
}
