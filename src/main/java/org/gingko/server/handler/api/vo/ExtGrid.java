package org.gingko.server.handler.api.vo;

import java.util.List;

/**
 * Created by TangYing on 14-4-1.
 */
public class ExtGrid<T> {

    private List<T> data;
    private List<ExtColumnModle> columnModle;
    private List<ExtField> fieldsNames;

    /**
     * Important construct function. </br>
     * Do generic conversion
     */
    public ExtGrid() {

    }

    public ExtGrid(List<T> data, List<ExtColumnModle> columnModle, List<ExtField> fieldsNames) {
        this.data = data;
        this.columnModle = columnModle;
        this.fieldsNames = fieldsNames;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<ExtColumnModle> getColumnModle() {
        return columnModle;
    }

    public void setColumnModle(List<ExtColumnModle> columnModle) {
        this.columnModle = columnModle;
    }

    public List<ExtField> getFieldsNames() {
        return fieldsNames;
    }

    public void setFieldsNames(List<ExtField> fieldsNames) {
        this.fieldsNames = fieldsNames;
    }
}
