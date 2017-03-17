package com.dreamerlxb.recyclerviewdemo.entity;

/**
 * Created by sxb on 2017/3/17.
 */

public class SectionEntity {
    private String title;
    private boolean isHeader;

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SectionEntity( String title) {
        this.title = title;
        this.isHeader = false;
    }

    public SectionEntity( String title, boolean isHeader) {
        this.title = title;
        this.isHeader = isHeader;
    }
}
