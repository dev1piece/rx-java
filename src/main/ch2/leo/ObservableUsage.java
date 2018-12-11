package ch2.leo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import utils.Log;

import static utils.Log.log;

public class ObservableUsage {
    private static Observer<Object> observer = new Observer<Object>() {
        @Override
        public void onSubscribe(Disposable d) {
            log("onSubscribe");
        }

        @Override
        public void onNext(Object o) {
            log("onNext -> " + o);
        }

        @Override
        public void onError(Throwable e) {
            log("onNext -> " + e);
        }

        @Override
        public void onComplete() {
            log("onComplete");
        }
    };



    public static void main(String[] args) {
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
