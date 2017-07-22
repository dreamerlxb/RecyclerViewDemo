package com.dreamerlxb.recyclerviewdemo.entity;

import java.util.List;

/**
 * Created by lxb on 17-7-22.
 */

public interface IExpandableItem<T> {

    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();

    /**
     * Get the level of this item. The level start from 0.
     * If you don't care about the level, just return a negative.
     */
    int getLevel();
}
