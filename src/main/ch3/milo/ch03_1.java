package milo;

import io.reactivex.Observable;

public class ch03_1 {

    public static void main(String[] args) {
        // map() 이나 filter()같은 단일 연산자는 누군가 관심을 기울이지 않는 한 평가를 수행하지 않는다.
        Observable.just(8, 9, 10)
                .filter(i -> i % 3 > 0)
                .map(i -> "# " + i * 10)
                .filter(s -> s.length() < 4);

        //doOnNext() 는 흘러 지나가는 항목을 건드리지 않고 들여다 볼수 있는 불순한 연산자다.
        //Observable로 둘러싼 모든 자료형은 반드시 불변이어야 한다.
        Observable.just(8,9,10)
                .doOnNext(i -> System.out.println("A: "+ i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("B: "+ i))
                .map(i -> "#" + i*10)
                .doOnNext(i -> System.out.println("C: "+ i))
                .filter(s -> s.length() < 4)
                .subscribe( s-> System.out.println("D: "+ s));

    }
}
