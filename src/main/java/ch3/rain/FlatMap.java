package ch3.rain;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class FlatMap {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== map, filter ==========");
        Observable
                .range(1, 10)
                .map(x -> x * 2)
                .filter(x -> 10 != x)
                .subscribe(System.out::println);

        System.out.println("========== flatMap ==========");
        Observable
                .range(1, 10)
                .flatMap(x -> Observable.just(x * 2))
                .flatMap(x -> x != 10 ? Observable.just(x): Observable.empty())
                .subscribe(System.out::println);

        System.out.println("========== delay ==========");
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")
                .delay(word -> Observable.timer(word.length(), TimeUnit.SECONDS))
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(15);

        System.out.println("========== delay with timer and flatMap ==========");
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit")
                .flatMap(word -> Observable.timer(word.length(), TimeUnit.SECONDS).map(x -> word))
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(15);

    }

}
