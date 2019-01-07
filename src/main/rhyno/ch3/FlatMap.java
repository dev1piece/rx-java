package com.rhyno.rx.ch3;


import com.rhyno.rx.ch3.order.Customer;
import com.rhyno.rx.ch3.order.Order;
import io.reactivex.Observable;

import java.util.Arrays;

import static io.reactivex.Observable.empty;
import static io.reactivex.Observable.just;

public class FlatMap {
    public static void main(String[] args) {
        //Observable의 map과 flatMap으로 동일한 결과 출력하기
        Observable<Integer> byMap = just(1, 2, 3, 4, 5)
                .map(x -> x * 2)
                .filter(x -> x != 10);
        byMap.subscribe(i -> System.out.println("by map:" + i));

        Observable<Integer> byFlatMap = just(1, 2, 3, 4, 5)
                .flatMap(x -> just(x * 2))
                .flatMap(x -> (x != 10) ? just(x) : empty());
        byFlatMap.subscribe(i -> System.out.println("by flatMap: " + i));

        //flatMapIterable
        Customer rhyno = Customer.builder()
                .name("rhyno")
                .orders(Arrays.asList(
                        Order.builder().buyer("rhyno").name("a").build(),
                        Order.builder().buyer("rhyno").name("b").build(),
                        Order.builder().buyer("rhyno").name("c").build()
                ))
                .build();
        Customer rain = Customer.builder()
                .name("rain")
                .orders(Arrays.asList(
                        Order.builder().buyer("rain").name("a").build(),
                        Order.builder().buyer("rain").name("b").build()
                ))
                .build();
        Customer leo = Customer.builder()
                .name("leo")
                .orders(Arrays.asList(
                        Order.builder().buyer("leo").name("c").build()
                ))
                .build();
        Observable<Order> orderObservable = just(rhyno, rain, leo)
                .flatMapIterable(Customer::getOrders);
        orderObservable.subscribe(order -> System.out.println(order));

    }
}
