package com.rhyno.rx.ch3;

import io.reactivex.Observable;

import java.time.DayOfWeek;
import java.util.concurrent.TimeUnit;

public class ConcatMap {
    public static Observable<String> loadRecordsFor(DayOfWeek dow) {
        switch (dow) {
            case SUNDAY:
                return Observable
                        .interval(95, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Sun-" + i);
            case MONDAY:
                return Observable
                        .interval(65, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Mon-" + i);
            case TUESDAY:
                return Observable
                        .interval(70, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Tue-" + i);
            case WEDNESDAY:
                return Observable
                        .interval(75, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Wen-" + i);
            case THURSDAY:
                return Observable
                        .interval(80, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Thu-" + i);
            case FRIDAY:
                return Observable
                        .interval(85, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Fri-" + i);
            case SATURDAY:
                return Observable
                        .interval(90, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> "Sat-" + i);
            default:
                return Observable.empty();
        }
    }
}
