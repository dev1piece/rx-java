package ch2.leo;

import io.reactivex.Observable;

import static utils.Log.log;

public class CacheUsage {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.<Integer>create(emitter -> {
            log("Create");
            emitter.onNext(42);
            emitter.onComplete();
        });

        observable.subscribe(n -> log(n));
        observable.subscribe(n -> log(n));

        System.out.println();
        Observable<Integer> cacheObservable = Observable.<Integer>create(emitter -> {
            log("Create");
            emitter.onNext(42);
            emitter.onComplete();
        }).cache();

        cacheObservable.subscribe(n -> log(n));
        cacheObservable.subscribe(n -> log(n));
    }
}
