package ch3.rain;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Disharmony {

    public static void main(String[] args) throws InterruptedException {
        Observable<String> fast = Observable
                .intervalRange(1, 3, 10, 10, TimeUnit.MILLISECONDS)
                .map(x -> "FAST" + x);
        Observable<String> slow = Observable
                .intervalRange(1, 3, 17, 17, TimeUnit.MILLISECONDS)
                .map(x -> "SLOW" + x);

        System.out.println("========== combineLatest ==========");
        Observable.combineLatest(fast, slow, (f, s) -> f + ":" + s).subscribe(System.out::println);
        TimeUnit.MILLISECONDS.sleep(100);

        System.out.println("========== withLatestFrom ==========");
        slow.withLatestFrom(fast, (s, f) -> s + ":" + f).subscribe(System.out::println);
        TimeUnit.MILLISECONDS.sleep(100);
    }

}
