package com.rhyno.rx.ch3;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

import static io.reactivex.Observable.timer;

public class Merge {
    public Observable<String> fastAlgo() {
        return Observable.just(1, 2, 3)
//                .flatMap(i -> Observable.just("fast-" + i));
                .flatMap(i -> timer(1, TimeUnit.SECONDS).map(v -> "fast-" + i));
    }

    public Observable<String> preciseAlgo() {
        return Observable.just(1, 2, 3)
//                .flatMap(i -> Observable.just("precise-" + i));
                .flatMap(i -> timer(2, TimeUnit.SECONDS).map(v -> "precise-" + i));
    }

    public Observable<String> exprementalAlgo() {
        return Observable.just(1, 2, 3)
//                .flatMap(i -> Observable.just("exp-" + i));
                .flatMap(i -> timer(3, TimeUnit.SECONDS).map(v -> "exp-" + i));
    }

    public Observable<String> throwErrorAlgo() {
        return Observable.just(1, 2, 3)
                .flatMap(i -> {
                    if (i == 3) {
                        throw new Exception("fail to emit event: " + i);
                    }
                    return timer(3, TimeUnit.SECONDS).map(v -> "error-" + i);
                });
    }
}
