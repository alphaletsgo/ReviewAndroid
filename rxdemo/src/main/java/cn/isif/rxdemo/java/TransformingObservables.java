package cn.isif.rxdemo.java;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TransformingObservables {
    public static void main(String[] args) {
//        map();
        flatMap();
    }

    /**
     * Map操作符对原始Observable发射的每一项数据应用一个你选择的函数，然后返回一个发射这些结果的Observable。
     */
    private static void map(){
//        Observable.just(1,2,3).map(x -> x * x).subscribe(result -> System.out.println(result));
        //输入int输出str
        Observable.just(1,2,3).map(x -> "int to String:" + x ).subscribe(result -> System.out.println(result));
    }

    private static void flatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>(3);
                for (int i = 0;i<3;i++)
                    list.add("flatMap " +integer);
                return Observable.fromIterable(list);//返回一个新的Observable
            }
        }).subscribe(s -> System.out.println(s));
        //注意：flatMap并不保证事件的顺序，如果想要保证事件的顺序可以使用concatMap
    }
}
