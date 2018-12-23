package ch2.milo;


import io.reactivex.Observable;

public class BaseExceptHandleSample {
    public static void main(String[] args) {
        Observable<Integer> numbers = Observable.create(s -> {
            s.onNext(1);
            s.onNext(3);
            if (true) {
                throw new RuntimeException();
            }
            s.onNext(5);
            s.onComplete();
        });

        numbers.subscribe(
                (Integer number) -> System.out.println("#" + number),
                (Throwable t) -> System.out.println("@ Exception"),
                () -> System.out.println("Detect end of stream")
        );

        System.out.println("========================================================");

        Observable<Integer> numbers2 = Observable.create(s -> {
            s.onNext(1);
            s.onNext(3);
            s.onNext(5);
            s.onComplete();
        });

        numbers2.subscribe(
                (Integer number) -> System.out.println("#" + number),
                (Throwable t) -> System.out.println("@ Exception"),
                () -> System.out.println("Detect end of stream")
        );

    }
}