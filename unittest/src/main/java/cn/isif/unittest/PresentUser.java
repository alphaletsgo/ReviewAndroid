package cn.isif.unittest;

import android.text.TextUtils;

import cn.isif.unittest.utils.StringUtils;

public class PresentUser {
    UserRepositories mUserRepositories;

    public PresentUser(UserRepositories mUserRepositories) {
        this.mUserRepositories = mUserRepositories;
    }

    /**
     * 模拟提交信息
     */
    public void commitInfo(String name, String email) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email)) {
            return;
        }
        mUserRepositories.commitExec(name, email);
    }

    /**
     * 模拟提交信息
     */
    public void commitInfo(String name, String email, NetWorkCallBack callBack) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email)) {
            return;
        }
        mUserRepositories.commitExec(name, email, callBack);
    }

}
