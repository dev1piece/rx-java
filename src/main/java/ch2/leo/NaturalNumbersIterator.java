package ch2.leo;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.stream.Stream;

public class NaturalNumbersIterator implements Iterator<BigInteger> {

    private BigInteger current = BigInteger.ZERO;

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public BigInteger next() {
        current = current.add(BigInteger.ONE);
        return current;
    }

    public static void main(String[] args) {
        NaturalNumbersIterator iterator = new NaturalNumbersIterator();
        for (int i = 0; i < 10; i++) {
            System.out.println(iterator.next());
        }
    }
}
