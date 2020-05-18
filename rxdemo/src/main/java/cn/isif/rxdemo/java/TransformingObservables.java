package cn.isif.rxdemo.java;

import io.reactivex.rxjava3.core.Observable;

public class TransformingObservables {
    public static void main(String[] args) {
//        map();
    }

    /**
     * Map操作符对原始Observable发射的每一项数据应用一个你选择的函数，然后返回一个发射这些结果的Observable。
     */
    private static void map(){
        Observable.just(1,2,3).map(x -> x * x).subscribe(result -> System.out.println(result));
    }

    private static void flatMap(){

    }
}
