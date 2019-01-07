package com.rhyno.ch3;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableErrorTest {
    @Test
    public void whenThrowErrorDuringThirdEmitEvent_thenStopEmitEvents() {
        Observable<String> observable = Observable.just(1, 2, 3, 4, 5)
                .concatMap(i -> {
                    if (i == 3) {
                        throw new Exception("third throw error");
                    }
                    return Observable.just(String.valueOf(i));
                });

        TestObserver<String> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);
        testObserver.assertError(err -> err.getMessage().equals("third throw error"));

        List<Object> emittedEvents = testObserver.getEvents().get(0);
        System.out.println("emitted events :" + emittedEvents);
        assertThat(emittedEvents).doesNotContain("3", "4", "5");
    } // 라이노 바보
}
