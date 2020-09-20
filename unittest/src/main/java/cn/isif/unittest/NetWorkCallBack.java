package cn.isif.unittest;

public interface NetWorkCallBack {
    void onSuccess(Object data);
    void onFailure(int code, String msg);
}
