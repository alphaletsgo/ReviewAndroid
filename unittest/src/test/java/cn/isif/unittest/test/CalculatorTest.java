package cn.isif.unittest.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import cn.isif.unittest.utils.Calculator;

public class CalculatorTest {
    Calculator mCalculator;

    /**
     * @Before 其他方法执行前会先执行这个方法
     * 与此对应的还有一个@After,即会在每一个测试方法之后会调用
     *
     * 相关注解：
     * @BeforeClass 在跑一个测试类所有方法之前会被执行一次
     * @AlferClass  在跑一个测试类所有方法完成后会执行一次
     * 注意：被@BeforeClass和AlferClass修饰的方法必须是static方法
     */
    @Before
    public void setup() {
        mCalculator = new Calculator();
    }

    /**
     * Assert 介绍
     *
     * 常用方法介绍
     * assertEquals(expected, actual) 验证expected的值跟actual是否一样，一样 —— 测试通过，不一样 —— 测试失败。
     * assertTrue(boolean condition) 验证condition的值是否是true，同理assertFalse(boolean condition)
     * assertNull(Object obj) 验证obj是否是null，同理assertNotNull(Object obj)
     * assertSame(expected, actual) 验证expected和actual是否是同一个对象，同理assertNotSame(expected, actual)
     *
     * fail() 让测试方法失败，上面的每一个方法，都有一个重载的方法，可以在前面加一个String类型的参数，表示如果验证失败的话，将
     * 这个字符串作为失败的结果报告。
     */

    @Test
    public void test_add() {
        int result = mCalculator.add(3, 4);
//        Assert.assertEquals(7, result);
        //验证失败返回一个我们设定的报告，我们可以指定一个失败的报告
        Assert.assertEquals("emmm... The result is fail.",8,result);

    }

    @Test
    public void test_multiply() {
        int result = mCalculator.multiply(3, 4);
        Assert.assertEquals(12, result);
    }

    @Test
    @Ignore("not implemented yet")
    public void test_ignore(){
        //使用了@Ignore注解，在跑全部测试的时候会忽略被修饰的方法，所以这个方法不会执行
        System.out.println("Ignore test method");
    }

    /**
     * (expected = IllegalArgumentException.class) 会验证抛出的异常是否是IllegalArgumentException，只有跑出这个异常才会验证通过
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_divide(){
        mCalculator.divide(4,0);
    }

}
