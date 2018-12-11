package ch2.leo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import utils.LoggingObserver;

import static utils.Log.log;

public class ObservableUsage {
    public static void main(String[] args) {
        Observer<Object> observer = new LoggingObserver<>();

        log("========= just =========");
        Observable.just(1)
                .subscribe(observer);
        System.out.println();

        log("========= fromArray =========");
        Observable.fromArray(1, 2)
                .subscribe(observer);
        System.out.println();

        log("========= range =========");
        Observable.range(3, 2)
                .subscribe(observer);
        System.out.println();

        log("========= empty =========");
        Observable.empty()
                .subscribe(observer);
        System.out.println();

        log("========= never =========");
        Observable.never()
                .subscribe(observer);
        System.out.println();

        log("========= error =========");
        Observable.error(new RuntimeException("Hello Exception"))
                .subscribe(observer);
        System.out.println();
    }
}
