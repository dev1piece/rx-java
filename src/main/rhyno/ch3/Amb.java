package com.rhyno.rx.ch3;

import com.rhyno.rx.Logger;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Amb {
    private Logger logger;

    public Amb(Logger logger) {
        this.logger = logger;
    }

    public Observable<String> stream(int initialDelay, int interval, String name) {
        return Observable
                .interval(initialDelay, interval, TimeUnit.MILLISECONDS)
                .map(x -> name + x)
                .doOnSubscribe(event -> logger.info("Subscribe to " + name))
                .doOnDispose(() -> logger.info("Unsubscribe from " + name));
    }
}
