package com.rhyno.ch3;

import com.rhyno.rx.ch3.Delay;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.reactivex.Observable.timer;
import static org.assertj.core.api.Assertions.assertThat;

public class DelayTest {
    private Delay subject;

    @Before
    public void setUp() throws Exception {
        subject = new Delay();
    }

    @Test
    public void whenDelayIsLogger_thenTimerAndFlatMap() throws InterruptedException {
        Duration delayDuration = subject.delay();
        Duration timerDuration = subject.timerAndFlatMap();
        System.out.println("[delay] duration: " + delayDuration);
        System.out.println("[timerAndFlayMap] duration: " + timerDuration);

        assertThat(delayDuration).isGreaterThan(timerDuration);
    }

    @Test
    public void whenDelayEachWordLength_thenEmitWordAfterLengthSeconds() throws InterruptedException {
        List<String> words = new ArrayList<>();
        Observable.just("Lorem", "ipsum", "dolor", "sit", "amet",
                "consectetur", "adipiscing", "elit")
                .delay(word -> timer(word.length(), TimeUnit.SECONDS))
                .subscribe(w -> {
                    words.add(w);
                    System.out.println(w);
                });

        TimeUnit.SECONDS.sleep(15);
        assertThat(words.get(0)).isEqualTo("sit");
        assertThat(words.subList(1, 3)).containsExactly("amet", "elit");
        assertThat(words.subList(3, 6)).containsExactly("Lorem", "ipsum", "dolor");
        assertThat(words.subList(6, 7)).containsExactly("adipiscing");
        assertThat(words.get(7)).isEqualTo("consectetur");

    }

    @Test
    public void whenDelayWithFlatMap_thenAlwaysNotGuaranteeEventOrder() throws InterruptedException {
        Disposable disposable = Observable.range(0, 10)
                .forEachWhile(i -> {
                    List<String> words = new ArrayList<>();
                    Observable.just("Lorem", "ipsum", "dolor", "sit", "amet",
                            "consectetur", "adipiscing", "elit")
                            .flatMap(word -> timer(word.length(), TimeUnit.SECONDS).map(x -> word))
                            .subscribe(w -> {
                                words.add(w);
                                System.out.println(w);
                            });

                    TimeUnit.SECONDS.sleep(15);
                    try {
                        assertThat(words.get(0)).isEqualTo("sit");
                        assertThat(words.subList(1, 3)).containsExactly("amet", "elit");
                        assertThat(words.subList(3, 6)).containsExactly("Lorem", "ipsum", "dolor");
                        assertThat(words.subList(6, 7)).containsExactly("adipiscing");
                        assertThat(words.get(7)).isEqualTo("consectetur");
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
        assertThat(disposable.isDisposed()).isTrue();
    }
}
