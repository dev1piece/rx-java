package milo;

import io.reactivex.Observable;

interface WeatherStation {
    Observable<Temperature> temperature();
    Observable<Wind> wind();
}

class BasicWeatherStation implements WeatherStation {

    @Override
    public Observable<Temperature> temperature() {
        return Observable.just(new Temperature());
    }

    @Override
    public Observable<Wind> wind() {
        return Observable.just(new Wind());
    }
}

class Temperature {}

class Wind {}

