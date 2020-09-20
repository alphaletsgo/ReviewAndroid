package cn.isif.reviewandroid;

import org.junit.Assert;
import org.junit.Test;

import cn.isif.reviewandroid.utils.StringUtils;

public class StringUtilsTest {
    @Test
    public void test_isEmpty(){
        String nullString = null;
        Assert.assertEquals(StringUtils.isEmpty(nullString),true);
    }
}
