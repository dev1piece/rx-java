package ch3.rain;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class MultipleObservable {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== merge ==========");
        Observable
                .merge(preciseAlgo(), fastAlgo(), experimentalAlgo())
                .subscribe(System.out::println);
        TimeUnit.MILLISECONDS.sleep(2000);

        System.out.println("========== zip ==========");
        // TODO 1. 요부분 delay 걸어도 이벤트가 동시에 나가는것같은데... 좀 이상함
        // TODO 2. 짝이 다 지어지지 않았는데 안기다리고 잘 끝나네??
        Observable.zip(
                Observable.just("A", "B", "C").delay(500, TimeUnit.MILLISECONDS),
                Observable.just(1, 2).delay(1000, TimeUnit.MILLISECONDS),
                (s, i) -> Observable.just(s + i))
                .subscribe(
                        o -> o.subscribe(System.out::println),
                        throwable -> System.out.println("error"),
                        () -> System.out.println("complete")
                );
        TimeUnit.MILLISECONDS.sleep(2000);

        System.out.println("========== FlatMap Example in cartesian product ==========");
        Observable<Integer> oneToEight = Observable.range(1, 8);
        Observable<String> ranks = oneToEight.map(Object::toString);
        Observable<String> files = oneToEight
                .map(x -> (char) ('A' + x - 1))
                .map(ch -> Character.toString(ch));
        files.flatMap(file -> ranks.map(rank -> file + rank)).subscribe(System.out::println);

        System.out.println("========== FlatMap Example ==========");
    }

    private static Observable experimentalAlgo() {
        return Observable.just("experimental").delay(50, TimeUnit.MILLISECONDS);
    }

    private static Observable fastAlgo() {
        return Observable.just("fast");
    }

    private static Observable preciseAlgo() {
        return Observable.just("precise").delay(1000, TimeUnit.MILLISECONDS);
    }

}
