package com.rhyno.rx.ch3;

import com.rhyno.rx.sample.Status;
import com.rhyno.rx.sample.Tweet;
import io.reactivex.Observable;

import java.time.Instant;
import java.util.Date;

public class Map {
    public static void main(String[] args) {
        Observable<Tweet> tweets = Observable.fromArray(
                Tweet.builder().status(Status.builder().name("normal").createdAt(new Date()).build()).build(),
                Tweet.builder().status(Status.builder().name("warning").createdAt(new Date()).build()).build(),
                Tweet.builder().status(Status.builder().name("normal").createdAt(new Date()).build()).build()
        );
        Observable<Date> dates = tweets
                .map(Tweet::getStatus)
                .map(s -> {
                    System.out.println("map is called");
                    return s.getCreatedAt();
                });
        System.out.println("Observable is lazy \n");

        dates.subscribe(d -> System.out.println(d));

        Observable<Instant> instants = tweets
                .map(Tweet::getStatus)
                .map(s -> s.getCreatedAt())
                .map(d -> d.toInstant());
        instants.subscribe(i -> System.out.println(i));
    }
}
