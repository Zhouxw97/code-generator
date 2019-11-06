package com.yonyou.cyx.framework.bean.dto.framework;

/**
 * 接口通用的返回bean
 *
 * @author BENJAMIN
 */
public class ResultBean {

    /**
     * API 调用成功
     */
    public static final Integer SUCCESS_CODE = 200;
    /**
     * 验证失败
     */
    public static final Integer VALID_FAILD = 400;
    /**
     * 未知错误
     */
    public static final Integer ERROR_UNKNOWN = 900;
    public static final Integer ERROR_DB = 901;

    private Integer resultCode;
    private String errMsg;
    private long elapsedMilliseconds;

    public ResultBean() {
        super();
    }

    public ResultBean(Integer resultCode, String errMsg) {
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }


    public long getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    public void setElapsedMilliseconds(long elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }


    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}