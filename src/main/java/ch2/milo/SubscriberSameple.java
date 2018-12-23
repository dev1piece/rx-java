package ch2.milo;

import io.reactivex.Observable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.stream.IntStream;

public class SubscriberSameple {

    public static void main(String[] args) {

        Observable<Integer> numbers = Observable.create(s -> {
            IntStream.range(1,20).forEach(s::onNext);
            s.onComplete();
        });

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("#onSubscriebe !");
            }

            @Override
            public void onNext(Integer integer) {
                if(integer == 10){
                    this.onComplete();
                }
                System.out.println("# " + integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("# onError");
            }

            @Override
            public void onComplete() {
                System.out.println("# onComplete");
            }
        };
        //numbers.subscribe(subscriber); ??
    }

}
