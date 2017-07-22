package com.dreamerlxb.recyclerviewdemo.entity;

/**
 * Created by Administrator on 2017/3/19.
 */

public class SectionDecorEntity implements ISectionEntity {
    private int sectionId;
    private String title;
    private String sectionTitle;


    public SectionDecorEntity(int sectionId, String title, String sectionTitle) {
        this.sectionId = sectionId;
        this.title = title;
        this.sectionTitle = sectionTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    @Override
    public int getSectionId() {
        return sectionId;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}
