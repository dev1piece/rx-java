package com.rhyno.ch3;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccumulatorTest {
    private Observable<Integer> progress = Observable.just(10, 14, 12, 13, 14, 16);

    @Test
    public void whenScan_withOutInitialValue_thenEmitEventEach() {
        Observable<Integer> totalProgress = progress.scan((total, chunk) -> total + chunk);

        List<Integer> result = totalProgress.toList().blockingGet();
        assertThat(result).isEqualTo(Lists.newArrayList(10, 24, 36, 49, 63, 79));
    }

    @Test
    public void whenScanWithInitial_whenNotUseFirstEventToInitial() {
        TestObserver<BigInteger> testObserver = new TestObserver<>();

        Observable
                .range(2, 100)
                .scan(BigInteger.ONE, (big, cur) -> big.multiply(BigInteger.valueOf(cur)))
                .doOnNext(event -> System.out.println(event))
                .subscribe(testObserver);

        testObserver.assertValueAt(0, BigInteger.ONE);
        testObserver.assertValueCount(101); //Initial Value + event sources
    }

    @Test
    public void whenReduce_thenNotEmitEventEach_justReturnAccumulatorValue() {
        TestObserver<Integer> testObserver = new TestObserver<>();

        progress.reduce((total, chunk) -> total + chunk)
                .doOnEvent((event, error) -> {
                    System.out.println("accumulator: " + event);
                    System.out.println("error: " + error);
                })
                .subscribe(testObserver);

        testObserver.assertValueCount(1);
        testObserver.assertValue(79);
    }
}
