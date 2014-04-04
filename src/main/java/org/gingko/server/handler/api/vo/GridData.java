package org.gingko.server.handler.api.vo;

/**
 * Created by Administrator on 14-4-1.
 */
public class GridData {

    private String column;
    private int y2011;
    private int y2012;
    private int y2013;

    public GridData(String column, int y2013, int y2012, int y2011) {
        this.column = column;
        this.y2011 = y2011;
        this.y2012 = y2012;
        this.y2013 = y2013;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getY2011() {
        return y2011;
    }

    public void setY2011(int y2011) {
        this.y2011 = y2011;
    }

    public int getY2012() {
        return y2012;
    }

    public void setY2012(int y2012) {
        this.y2012 = y2012;
    }

    public int getY2013() {
        return y2013;
    }

    public void setY2013(int y2013) {
        this.y2013 = y2013;
    }
}