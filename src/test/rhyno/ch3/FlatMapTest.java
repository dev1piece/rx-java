package com.rhyno.ch3;

import com.rhyno.rx.ch3.file.File;
import com.rhyno.rx.ch3.file.FileService;
import com.rhyno.rx.ch3.file.Rating;
import io.reactivex.Observable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static io.reactivex.Observable.timer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class FlatMapTest {
    @Mock
    private FileService mockFileService;

    @Test
    public void whenFlatMapMutation_thenHasOnNextAndOnErrorAndOnCompleted() {
        UUID uuid = UUID.randomUUID();
        File file = File.builder().id(uuid).fileService(mockFileService).build();
        file.store(uuid);

        then(mockFileService).should().save(any(UUID.class), any(Rating.class));
    }

    @Test
    public void whenFlatMapWithMaxConcurrency_thenLimitEmitEvent() throws InterruptedException {
        Instant start = Instant.now();
        AtomicReference<Duration> durationEmit1Event = new AtomicReference<>();
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap(
                        i -> timer(1, TimeUnit.SECONDS).map(l -> "number:" + i),
                        1
                )
                .subscribe(
                        v -> System.out.println(v),
                        e -> Observable.error(Exception::new),
                        () -> {
                            Duration between = Duration.between(start, Instant.now());
                            durationEmit1Event.set(between);
                        }
                );

        TimeUnit.SECONDS.sleep(11);
        System.out.println("duration with maxConcurrency is 1=" + durationEmit1Event);

        Instant start2 = Instant.now();
        AtomicReference<Duration> durationEmit5Events = new AtomicReference<>();
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .flatMap(
                        i -> timer(1, TimeUnit.SECONDS).map(l -> "number:" + i),
                        5
                )
                .subscribe(
                        v -> System.out.println(v),
                        e -> Observable.error(Exception::new),
                        () -> {
                            Duration between = Duration.between(start2, Instant.now());
                            durationEmit5Events.set(between);
                        }
                );

        TimeUnit.SECONDS.sleep(3);
        System.out.println("duration with maxConcurrency is 5=" + durationEmit5Events);

        assertThat(durationEmit1Event.get().toMillis()).isBetween(10000L, 11000L);
        assertThat(durationEmit5Events.get().toMillis()).isBetween(2000L, 3000L);
    }
}
