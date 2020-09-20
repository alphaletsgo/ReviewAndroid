package cn.isif.unittest.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import cn.isif.unittest.NetWorkCallBack;
import cn.isif.unittest.PresentUser;
import cn.isif.unittest.UserRepositories;


/**
 * Mockito 有两大作用
 * 其一：验证方法调用
 * 其二：指定某个方法的返回值，或者执行特定的动作
 */
public class PresentUserTest {

    /**
     * 我们使用mockito来验证无返回值的函数
     * eg:我们使用模拟的present来模拟向服务器提交信息，使用单元测试来验证是否正确的调用了提交函数
     * 使用verify函数来验证方法的调用
     * 如果我们并不想关心方法调用参数的正确性，只想关注这个方法是否被调用的话，可以使用：
     *  verify().xxxMethod(Mockito.anyString, Mockito.anyString)
     *  除了anyString还有：anyInt、anyLong、anyDouble。anyObject表示任何对象，any(clazz)表示任何属于clazz的对象
     *  除了对象外，我们还可以表示任何集合对象，，比如：anyCollection、anyCollectionOf(clazz)、anyList(Map, set)...
     */
    @Test
    public void test_commitInfo(){
        UserRepositories userRepositories = Mockito.mock(UserRepositories.class); //调用mock函数生成mock对象
        //方法调用里面也必须是mock对象，否则会报错，为了保证方法里面对象为mock，我们利用构造函数设置
        PresentUser presentUser = new PresentUser(userRepositories);
        presentUser.commitInfo("xiaoming","oka@isif.cn");
        //验证UserRepositories的commitExec方法得到了一次调用，如果想要验证得到多次调用，可以verify(mock, Mockito.times(n))
        Mockito.verify(userRepositories).commitExec("xiaoming","oka@isif.cn");
    }

    /**
     * 指定方法的返回
     */
    @Test
    public void test_getUsedServerList(){
       UserRepositories userRepositories = Mockito.mock(UserRepositories.class);
       //当调用userRepositories的getUsedServerList方法时，返回一个空的list
       Mockito.when(userRepositories.getUsedServerList()).thenReturn(Collections.EMPTY_LIST);
    }

    @Test
    public void test_commitInfo_CallBack(){
        UserRepositories userRepositories = Mockito.mock(UserRepositories.class);
        PresentUser presentUser = new PresentUser(userRepositories);
        presentUser.commitInfo("xiaoming","oka@isif.cn");
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                //获取commitExec的参数
                Object[] arguments = invocation.getArguments();
                //获取回调对象
                NetWorkCallBack callBack = (NetWorkCallBack)arguments[2];
                callBack.onSuccess("onSuccess");
                return null;
            }
        }).when(userRepositories).commitExec(Mockito.anyString(),Mockito.anyString(),Mockito.any(NetWorkCallBack.class));
    }

}
