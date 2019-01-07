package com.rhyno.ch3;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectTest {
    @Test
    public void whenCollect_thenReplaceAccumulateEventToList_withReduce() {
        TestObserver<ArrayList<Integer>> testObservable = new TestObserver<>();

        Observable
                .range(10, 20)
                .reduce(new ArrayList<Integer>(), (accumulator, event) -> {
                    accumulator.add(event);
                    return accumulator;
                })
                .subscribe(testObservable);

        testObservable.assertValue(
                Lists.newArrayList(
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29
                )
        );

        Observable
                .range(10, 20)
                .collect(
                    () -> new ArrayList<Integer>(),
                    (accumulator, event) -> accumulator.add(event)
                )
                .subscribe(testObservable);
        testObservable.assertValue(
                Lists.newArrayList(
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29
                )
        );


        List<Integer> result = Observable
                .range(10, 20)
                .collect(ArrayList::new, ArrayList::add)
                .blockingGet()
                .stream()
                .map(i -> (Integer) i)
                .collect(Collectors.toList());
        assertThat(result).isEqualTo(
                Lists.newArrayList(
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29
                )
        );
    }
}
