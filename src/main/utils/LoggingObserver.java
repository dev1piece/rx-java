package utils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static utils.Log.log;

public class LoggingObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        log("onSubscribe");
    }

    @Override
    public void onNext(T o) {
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
}
