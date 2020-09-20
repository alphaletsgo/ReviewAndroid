package cn.isif.unittest.utils;

/**
 * 单元测试学习类
 *
 * function：unit test
 */
public class Calculator {
    public int add(int one, int another){
        return one + another;
    }

    public int multiply(int one, int another){
        return one * another;
    }

    public double divide(double dividend, double divisor){
        if (divisor == 0) throw new IllegalArgumentException("Divisor cannot be 0");
        return dividend / divisor;
    }
}
