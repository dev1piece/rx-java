package com.rhyno.ch1;

import io.reactivex.Observable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class ObservableFactoryTest {
    @Mock
    private Caller mockCaller;

    @Test
    public void whenJustWithItems_thenEmitOneItem() {
        //just create observable with each item
        Observable.just(new int[]{1, 2, 3})
                .subscribe(i -> {
                    System.out.println("just: " + i);
                    mockCaller.call(i);
                });

        then(mockCaller).should(times(1)).call(any());
    }

    @Test
    public void whenFromWithItems_thenEmitNItems() {
        //from create observable with Iterable
        Observable.fromArray(1, 2, 3)
                .subscribe(i -> {
                    System.out.println("from: " + i);
                    mockCaller.call(i);
                });

        then(mockCaller).should(times(3)).call(anyInt());
    }

    public class Caller {
         public void call(int number) {
             System.out.println(number);
         }

         public void call(int[] numbers) {
             System.out.println(numbers);
         }
    }
}
