package ch2.leo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import utils.Log;
import utils.LoggingObserver;


import static utils.Log.log;

public class ThreadCheck {
    public static void main(String[] args) {

        log("Before");
        Observable.range(5, 3)
                .subscribe(n -> log(n));
        log("After");
        log("");

        Observable<Integer> ints = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                log("Create");
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
                emitter.onComplete();
                log("Completed");
            }
        });

        log("Starting");
        ints.subscribe(i -> log("Element -> " + i));
        log("Exit");
    }
}
