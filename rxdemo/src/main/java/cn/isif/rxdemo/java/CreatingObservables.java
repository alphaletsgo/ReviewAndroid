package cn.isif.rxdemo.java;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CreatingObservables {
    public static void main(String[] args) {
        //crate 参考HalloWorld
//        just();//just
//        from();//from
        defer();
    }

    /**
     * 一种对create的简写
     */
    private static void just(){
        Observable.just("RxJava creating with just").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 能够将一组(数组、列表等等)数据转化为被观察的对象列表
     */
    private static void from(){
        Observable.fromArray(new String[]{"from 1","from 2","from 3"}).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 直到有观察者订阅时才创建Observable，并且为每个观察者创建一个新的Observable
     */
    private static  void defer(){
        Observable<Long> observable = Observable.defer(() -> {
            long time = System.currentTimeMillis();
            return Observable.just(time);
        });
        System.out.println(System.currentTimeMillis());
        observable.subscribe( time -> System.out.println("in subscribe" + time));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(time -> System.out.println("in subscribe" + time));

    }
}
