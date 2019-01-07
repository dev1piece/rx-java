package com.rhyno.ch3;

import com.rhyno.rx.Logger;
import com.rhyno.rx.ch3.Amb;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;


@RunWith(MockitoJUnitRunner.class)
public class AmbTest {

    @Mock
    private Logger mockLogger;

    private Amb subject;

    @Before
    public void setUp() {
        subject = new Amb(mockLogger);
    }

    /**
     * amb()는 첫번째 이벤트를 받는 순간 나머지 ObservableSource는 구독을 해지한다
     *
     */
    @Test
    public void whenAmb_thenReturnFirstEventSources_andUnsubscribeFastStream() throws InterruptedException {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable.amb(
                Arrays.asList(
                        subject.stream(100, 17, "S"),
                        subject.stream(200, 10, "F")
                )
        ).subscribe(testObserver);

        TimeUnit.SECONDS.sleep(3);

        assertAmbResult(testObserver);
    }

    /**
     * ambArray()는 amb()와 동일한 결과를 리턴한다
     */
    @Test
    public void whenAmbArray_thenReturnSameResult() throws InterruptedException {
        TestObserver<String> testObserver = new TestObserver<>();

        Observable.ambArray(
            subject.stream(100, 17, "S"),
            subject.stream(200, 10, "F")
        ).subscribe(testObserver);

        TimeUnit.SECONDS.sleep(3);

        assertAmbResult(testObserver);
    }

    private void assertAmbResult(TestObserver<String> testObserver) {
        then(mockLogger).should().info("Subscribe to S");
        then(mockLogger).should().info("Subscribe to F");
        then(mockLogger).should().info("Unsubscribe from F");

        System.out.println(testObserver.getEvents().get(0));
        List<String> expectEmptyList = testObserver.getEvents().get(0)
                .stream()
                .map(event -> (String) event)
                .filter(e -> !e.contains("S"))
                .collect(Collectors.toList());
        assertThat(expectEmptyList.isEmpty()).isTrue();
    }
}
