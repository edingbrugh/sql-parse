package org.nankong.common;

/**
 * @Description: 错误信息的位置
 * @Author NanKong
 * @Date 2022/9/23 15:20
 */
public class ErrorColumn {

    /**
    *  第几行错误
    **/
    private Integer line;

    /**
     * 第几个字符有存在问题
     **/
    private Integer charLocation;

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getCharLocation() {
        return charLocation;
    }

    public void setCharLocation(Integer charLocation) {
        this.charLocation = charLocation;
    }

    public ErrorColumn(Integer line, Integer charLocation) {
        this.line = line;
        this.charLocation = charLocation;
    }

    public ErrorColumn() {
    }

    @Override
    public String toString() {
        return "ErrorColumn{" +
                "line=" + line +
                ", charLocation=" + charLocation +
                '}';
    }
}
