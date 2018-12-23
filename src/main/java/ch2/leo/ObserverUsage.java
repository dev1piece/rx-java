package ch2.leo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ObserverUsage {
    public static void main(String[] args) {
        Observable<Integer> integers = Observable.create((sub) -> {
            sub.onNext(1);
            sub.onNext(2);
            sub.onNext(3);
            sub.onComplete();
        });

        integers.subscribe(i -> System.out.println("onNext: " + i));
        integers.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Observer::onNext : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Observer:onError");
            }

            @Override
            public void onComplete() {
                System.out.println("Observer:onComplete");
            }
        });
    }
}
