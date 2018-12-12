package milo;


import io.reactivex.Observable;

public class MultiSubscribeAndSample { // p.40

    public static void main(String[] args) {
        //Multi Subscriber
        Observable<Integer> ints =
                Observable.create(subscribe -> {
                    System.out.println("Create");
                    subscribe.onNext(40);
                    subscribe.onComplete();
                });
        System.out.println("# Start");
        ints.subscribe( i -> System.out.println("Element A : " + i));
        ints.subscribe( i -> System.out.println("Element b : " + i));
        System.out.println("# Exit");


    }
}
