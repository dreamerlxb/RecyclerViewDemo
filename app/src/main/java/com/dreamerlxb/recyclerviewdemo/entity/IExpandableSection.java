package com.dreamerlxb.recyclerviewdemo.entity;

import java.util.List;

/**
 * Created by lxb on 17-7-22.
 */

public interface IExpandableSection extends ISectionEntity {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<IExpandableSection> getSubItems();
}
