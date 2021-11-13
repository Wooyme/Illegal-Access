package me.wooy.game.nax.misc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;

import static java.lang.Double.*;

public class AtomicDouble extends Number {

    private AtomicLong bits;

    public AtomicDouble() {
        this(0);
    }

    public AtomicDouble(double initialValue) {
        bits = new AtomicLong(doubleToLongBits(initialValue));
    }

    public final boolean compareAndSet(double expect, double update) {
        return bits.compareAndSet(doubleToLongBits(expect), doubleToLongBits(update));
    }

    public final void set(double newValue) {
        bits.set(doubleToLongBits(newValue));
    }

    public final double get() {
        return longBitsToDouble(bits.get());
    }

    public double doubleValue() {
        return get();
    }

    public final double getAndSet(double newValue) {
        return (bits.getAndSet(doubleToLongBits(newValue)));
    }

    public final boolean weakCompareAndSet(double expect, double update) {
        return bits.weakCompareAndSet(doubleToLongBits(expect),
                doubleToLongBits(update));
    }
    
    public final double addAndGet(double delta){
        double old = get();
        return bits.getAndSet(doubleToLongBits(old+delta));
    }

    public float floatValue() { return (float) doubleValue(); }
    public int intValue()       { return (int) get();           }
    public long longValue()     { return (long) get();          }

}