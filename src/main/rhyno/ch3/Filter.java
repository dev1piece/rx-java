package com.rhyno.rx.ch3;

import io.reactivex.Observable;

public class Filter {
    public static void main(String[] args) {
        Observable<String> strings = Observable.fromArray("a", "#b", ">c", "#>f", "#>d", "");
        Observable<String> comments = strings.filter(s -> s.startsWith("#"));
        Observable<String> instructions = strings.filter(s -> s.startsWith(">"));
        Observable<String> empty = strings.filter(String::isEmpty);

        comments.subscribe(s -> System.out.println("comment" + s));
        instructions.subscribe(s -> System.out.println("instruction" + s));
        empty.subscribe(s -> System.out.println("is empty" + s));
        strings.subscribe(s -> System.out.println("origin: " + s));
    }
}
