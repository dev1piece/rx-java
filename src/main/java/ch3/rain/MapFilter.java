package ch3.rain;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MapFilter {

    public static void main(String[] args) {
        Observable
                .just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .doOnNext(x -> System.out.println("A: " + x))
                .filter(i -> i % 3 == 0)
                .doOnNext(x -> System.out.println("B: " + x))
                .map(i -> "#" + i)
                .subscribe(s -> System.out.println("subscribe: " + s));
    }

}
