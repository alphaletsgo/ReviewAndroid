package cn.isif.unittest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserRepositories {
    public void commitExec(final String name, final String email) {
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name: " + name);
        System.out.println("email: " + email);
    }

    /**
     * 这里模拟一个网络请求：请求可用服务器列表
     * @return List<String> 返回一个list
     */
    public List<String> getUsedServerList(){
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] tempArray = new String[]{"a.a.a.a","b.b.b.b","c.c.c.c"};
        return Arrays.asList(tempArray);
    }
    //带返回值的提交
    public void commitExec(final String name, final String email,NetWorkCallBack netWorkCallBack) {
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("name: " + name);
        System.out.println("email: " + email);
        netWorkCallBack.onSuccess("");
    }
}
