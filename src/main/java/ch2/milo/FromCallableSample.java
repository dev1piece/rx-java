package ch2.milo;


import io.reactivex.Observable;

public class FromCallableSample {

    public static void main(String[] args) {
        //  fromCallable : Observable을 try-catch로 감싸준다. (Supplier)
        Observable<Integer> ints = Observable.fromCallable(() -> 3);
    }
}
