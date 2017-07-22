package com.dreamerlxb.recyclerviewdemo.entity;

/**
 * Created by sxb on 2017/5/2.
 */

public class MarkType implements ISectionEntity {

    /**
     * typeName : temp
     * typeDesc : 临时类型
     * typeDetail : null
     * typeGroupId : 0
     * id : 1
     */

    private String typeName;
    private String typeDesc;
    private String typeDetail;
    private int typeGroupId;
    private int id;


    private MarkType typeGroup;

    public MarkType getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(MarkType typeGroup) {
        this.typeGroup = typeGroup;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(String typeDetail) {
        this.typeDetail = typeDetail;
    }

    public int getTypeGroupId() {
        return typeGroupId;
    }

    public void setTypeGroupId(int typeGroupId) {
        this.typeGroupId = typeGroupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getSectionId() {
        return this.typeGroupId;
    }

    @Override
    public boolean isSection() {
        return this.typeGroupId == 0;
    }

    @Override
    public String toString() {
        return "MarkType{" +
                "typeDesc='" + typeDesc + '\'' +
                ", typeGroupId=" + typeGroupId +
                ", id=" + id +
                '}';
    }
}
