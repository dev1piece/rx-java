# ch1. Reactive Programming

## 정의
**리액티브 프로그래밍이란 데이터나 이벤트 변화의 반응에 초점을 맞춘 프로그래밍을 말한다.**                                      
* 복잡한 이벤트를 처리할때는 큰 이득을 얻는다. 단순한 이벤트를 처리할때는 큰 이득이 없다

## Reactive Streams 배경
* Iterable vs Observer
  - Iterable.next() : pull
  - Observer.notifyObservers(): push
  - Observer는 java 1.0 부터 존재. 1.9에서 deprecated
* **GoF가 만든 Observer 패턴의 한계?**
  - Complete를 시킬 수 없다. event(data)의 입력이 더 이상 없을 경우. 기존 observer 패턴은 종료할 방법이 없다.
  - Error 핸들링은 어떻게 할 것 인가? ex) 실제 버그외, 네트워크 지연에 따른 일시적 장애의 경우 몇번 try후 종료 같은 fallback 적용 어렵다

## 특징
* Pull(request) vs Push
* Sync, Async
  - Observerable 이벤트 생성의 중요한 기준은 블로킹/논블로킹 여부이지 동기/비동기 여부가 아니다.
* Concurrency, Parallelism
  - Observable 스트림은 동시성, 병렬성 모두 지원하지 않는다.
* observer에  onNext() 함수는 왜 동시에 수행하도록 설계하지 않았나?
  - 1) 사용자가 사용하기 어렵다. 2) 성능 오버헤드 존재 (컨텍스트 스위칭 비용)  
* Eager vs Lazy
  - Future
  - Observable
* Duality (쌍대성)
  - 수학적 용어, 똑같은 행위를 관점만 다르게 볼때 쌍대라 한다.
  - pull vs push
* Cardinality
  - Single: 값 하나의 스트림, 단일 요소 Observable
  - Observable: 다중 값 스트림
  - Completable: 반환형이 없을때, 구성 요소없는 Observable
* Block I/O vs Non Blocking I/O
  - 지연 시간과 처리량 개선 -> 사용자 경험 개선, 인프라스트럭처 비용 절감
  - 이벤트 루프 아키텍처 운영이 쉬움
  - 톰캣 : 스레드 풀 크기, 가비지 컬렉션 조정 필요
  - 네티: 단일쓰레드와 다중 쓰레드 이벤트 루프 지원

## 실습
* **page9. 마지막 코드 실행 결과 확인하기**
  - 1) main thread에 있는 sysout 출력되고, 2) subscribe 안에 sysout 출력

## 참조
* [스프링캠프 2017 [Day2 A4] : Reactive Programming with RxJava](https://www.youtube.com/watch?v=0zVwXszDk88)
* [Reactive streams에 대한 배경 이해](https://www.youtube.com/watch?v=8fenTR3KOJo)
