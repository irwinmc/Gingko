package org.gingko.server.handler.api.vo;

/**
 * Created by TangYing on 14-4-1.
 */
public class ExtColumnModle {

    private String header;
    private String dataIndex;
    private int width;
    private int flex;
    private ExtEditor editor;

    public ExtColumnModle(String header, String dataIndex, int width, int flex, ExtEditor editor) {
        this.header = header;
        this.dataIndex = dataIndex;
        this.width = width;
        this.flex = flex;
        this.editor = editor;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getFlex() {
        return flex;
    }

    public void setFlex(int flex) {
        this.flex = flex;
    }

    public ExtEditor getEditor() {
        return editor;
    }

    public void setEditor(ExtEditor editor) {
        this.editor = editor;
    }
}
