package com.dreamerlxb.recyclerviewdemo.entity;

import java.util.List;

/**
 * Created by sxb on 2017/7/24.
 */

public abstract class AbstractExpandableSection implements IExpandableSection {

    private List<IExpandableSection> mSubItems;
    private boolean expanded;

    @Override
    public int getSectionId() {
        return 0;
    }

    @Override
    public boolean isSection() {
        return mSubItems.size() > 0;
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public List<IExpandableSection> getSubItems() {
        return mSubItems;
    }
}
