package com.dreamerlxb.recyclerviewdemo.entity;

/**
 * Created by sxb on 2017/3/17.
 */

public class SectionEntityImpl implements ISectionEntity {

    private int id;

    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionEntityImpl that = (SectionEntityImpl) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }


    public SectionEntityImpl(int id) {
        this.id = id;
    }

    public SectionEntityImpl(int id, String title) {
        this.id = id;
        this.title = title;

        this.isHeader = false;
    }

    public SectionEntityImpl(int id, String title, boolean isHeader) {
        this.id = id;
        this.title = title;
        this.isHeader = isHeader;
    }

    private boolean isHeader;

    private boolean expanded = false;

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
