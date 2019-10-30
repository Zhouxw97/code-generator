package com.yonyou.cyx.framework.bean.dto.framework;

/**
 * rest接口返回的bean
 *
 * @author BENJAMIN
 */
public class RestResultResponse<T> extends ResultBean {

    private T data;
    private boolean success;

    public RestResultResponse() {
    }

    public RestResultResponse(Integer resultCode, String errMsg) {
        this.setResultCode(resultCode);
        this.setErrMsg(errMsg);
        this.setSuccess(false);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public RestResultResponse success(boolean success) {
        this.setSuccess(success);
        return this;
    }
    public RestResultResponse data(T data) {
        this.setResultCode(200);
        this.setSuccess(true);
        this.setData(data);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
