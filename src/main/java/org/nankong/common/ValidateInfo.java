package org.nankong.common;

import org.apache.spark.sql.sources.In;

/**
 * @Description: 校验外包装信息
 * @Author NanKong
 * @Date 2022/9/23 14:32
 */
public class ValidateInfo {

    /**
     * 是否校验通过
     **/
    private Boolean isTure;

    /**
     * 错误信息
     **/
    private String errorMsg;


    /**
     * 成功条数
     **/
    private Integer successNum;


    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Builder class of {@link ValidateInfo}.
     */
    public static class Builder<T> {

        public Boolean isTure;

        public String errorMsg;

        public Integer successNum;

        public Builder<T> isTure(Boolean isTure) {
            this.isTure = isTure;
            return this;
        }

        public Builder<T> errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public Builder<T> successNum(Integer successNum) {
            this.successNum = successNum;
            return this;
        }

        public ValidateInfo build() {
            ValidateInfo validate = new ValidateInfo();
            validate.setErrorMsg(this.errorMsg);
            validate.setTure(this.isTure);
            validate.setSuccessNum(this.successNum);
            return validate;
        }
    }

    public Boolean getTure() {
        return isTure;
    }

    public void setTure(Boolean ture) {
        isTure = ture;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    @Override
    public String toString() {
        return "ValidateInfo{" +
                "isTure=" + isTure +
                ", errorMsg='" + errorMsg + '\'' +
                ", successNum=" + successNum +
                '}';
    }
}
