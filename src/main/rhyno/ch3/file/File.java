package com.rhyno.rx.ch3.file;

import io.reactivex.Observable;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Builder
@Data
@Slf4j
public class File {
    private FileService fileService;
    private UUID id;

    public void store(UUID id) {
//        upload(id).subscribe(
//                bytes -> {},
//                e -> log.error("Error", e),
//                () -> rate(id)
//        );

        Observable<Rating> ratingObservable = upload(id).flatMap(
                bytes -> Observable.empty(),
                e -> Observable.error(e),
                () -> rate(id)
        );
        ratingObservable.subscribe(rating -> fileService.save(id, rating));

    }

    public Observable<Long> upload(UUID id) {
        //업로드 진행상황 반환
        return Observable.just(id.getLeastSignificantBits());
    }

    public Observable<Rating> rate(UUID id) {
        //업로드가 성공해야 동영상 등급을 매김
        int random = (int) (Math.random() * (10 - 5));
        System.out.println("UUID(" + id + ") is rate=" + random);
        return Observable.just(Rating.builder().value(random).build());
    }
}