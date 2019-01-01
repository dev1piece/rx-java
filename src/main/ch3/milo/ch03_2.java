package milo;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static io.reactivex.Observable.empty;
import static io.reactivex.Observable.just;
import static io.reactivex.Observable.timer;
import static java.util.concurrent.TimeUnit.SECONDS;
import static milo.Sound.DAH;
import static milo.Sound.DI;

public class ch03_2 {

    public static void main(String[] args) {
        mapFilter_compare_flatmap();

    }
    static void mapFilter_compare_flatmap() {
        //flatmap 은 업스트림의 T형 값을 Observable<R>로 바꿔서 Observable<Observable<R>> 로 만들어 낸다

        Observable<Integer> numbers = just(1, 2, 3);
        numbers.map(x -> x*2);
        numbers.filter(x -> x != 10);
        //flatmap
        numbers.flatMap( x -> just(x*2));
        numbers.flatMap(x -> ( x != 10) ? just(x) : empty());

        // flatmap 을 사용하는 경우
        // 1. map()의 변환결과가 Observable이어야 하는 경우 . 스트림의 개별항목이 블록돠지 않고 오랫동안 수행되는비동기 작업
        // 2. 단일 이벤트가 여러 하위 이벤트로 확장되는 일대다 변환이 필요한 경우 . 고객스트림 -> 고객 주문 스트림(여러개)

    }


    Observable<Sound> toMorseCode(char ch) {
        switch (ch) {
            case 'a':
                return just(DI, DAH);
            case 'b':
                return just(DAH, DI, DI, DI);
            case 'c':
                return just(DAH, DI, DAH, DI);
            case 'd':
                return just(DAH, DI, DI);
            case 'e':
                return just(DI);
            case 'f':
                return just(DI, DI, DAH, DI);
            case 'g':
                return just(DAH, DAH, DI);
            case 'h':
                return just(DI, DI, DI, DI);
            case 'i':
                return just(DI, DI);
            case 'j':
                return just(DI, DAH, DAH, DAH);
            case 'k':
                return just(DAH, DI, DAH);
            case 'l':
                return just(DI, DAH, DI, DI);
            case 'm':
                return just(DAH, DAH);
            case 'n':
                return just(DAH, DI);
            case 'o':
                return just(DAH, DAH, DAH);
            case 'p':
                return just(DI, DAH, DAH, DI);
            case 'q':
                return just(DAH, DAH, DI, DAH);
            case 'r':
                return just(DI, DAH, DI);
            case 's':
                return just(DI, DI, DI);
            case 't':
                return just(DAH);
            case 'u':
                return just(DI, DI, DAH);
            case 'v':
                return just(DI, DI, DI, DAH);
            case 'w':
                return just(DI, DAH, DAH);
            case 'x':
                return just(DAH, DI, DI, DAH);
            case 'y':
                return just(DAH, DI, DAH, DAH);
            case 'z':
                return just(DAH, DAH, DI, DI);
            case '0':
                return just(DAH, DAH, DAH, DAH, DAH);
            case '1':
                return just(DI, DAH, DAH, DAH, DAH);
            case '2':
                return just(DI, DI, DAH, DAH, DAH);
            case '3':
                return just(DI, DI, DI, DAH, DAH);
            case '4':
                return just(DI, DI, DI, DI, DAH);
            case '5':
                return just(DI, DI, DI, DI, DI);
            case '6':
                return just(DAH, DI, DI, DI, DI);
            case '7':
                return just(DAH, DAH, DI, DI, DI);
            case '8':
                return just(DAH, DAH, DAH, DI, DI);
            case '9':
                return just(DAH, DAH, DAH, DAH, DI);
            default:
                return empty();
        }
    }

    @Test
    public void sample_213() throws Exception {
        just('S', 'p', 'a', 'r', 't', 'a')
                .map(Character::toLowerCase)
                .flatMap(this::toMorseCode)
                .subscribe(System.out::println);
    }

    Observable<Long> delay(){
        return Observable.just(1L,2L,3L).delay(1, TimeUnit.SECONDS);
    }

    @Test
    void testDelay() {
        delay().subscribe(System.out::println);
    }

    @Test
    public void sample_timer() throws Exception {
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .delay(word -> timer(word.length(), SECONDS))
                .subscribe(System.out::println);

        SECONDS.sleep(15);
    }

    @Test
    public void sample_zip() throws Exception {
        Observable<LocalDate> nextTenDays =
                Observable
                        .range(1, 10)
                        .map(i -> LocalDate.now().plusDays(i));

        Observable<Vacation> possibleVacations = Observable
                .just(City.Warsaw, City.London, City.Paris)
                .flatMap(city -> nextTenDays.map(date -> new Vacation(city, date))
                        .flatMap(vacation ->
                                Observable.zip(
                                        vacation.weather().filter(Weather::isSunny),
                                        vacation.cheapFlightFrom(City.NewYork),
                                        vacation.cheapHotel(),
                                        (w, f, h) -> vacation
                                )));
    }

}
