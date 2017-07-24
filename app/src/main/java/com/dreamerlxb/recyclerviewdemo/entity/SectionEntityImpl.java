package com.dreamerlxb.recyclerviewdemo.entity;

import java.util.List;

/**
 * Created by sxb on 2017/3/17.
 */

public class SectionEntityImpl implements ISectionEntity {

    private String title;

    private List<SectionEntityImpl> subItems;

    private boolean isHeader;

    private boolean expanded = false;

    public SectionEntityImpl( String title) {
        this.title = title;
        this.isHeader = false;
    }

    public SectionEntityImpl(String title, boolean isHeader) {
        this.title = title;
        this.isHeader = isHeader;
    }

    public SectionEntityImpl(String title, boolean isHeader, List<SectionEntityImpl> subItems) {
        this.title = title;
        this.subItems = subItems;
        this.isHeader = isHeader;
    }

    public List<SectionEntityImpl> getSubItems() {
        return subItems;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
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
