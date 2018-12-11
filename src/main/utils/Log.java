package utils;

public class Log {
    public static void log(String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }
}
