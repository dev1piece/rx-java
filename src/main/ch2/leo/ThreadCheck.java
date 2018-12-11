package ch2.leo;

import io.reactivex.Observable;
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

    }
}
