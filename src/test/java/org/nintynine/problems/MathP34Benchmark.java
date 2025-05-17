package org.nintynine.problems;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

/**
 * Microbenchmark for {@link MathP34#totientPhi(long)} and
 * {@link MathP37#totientPhi(long)} using JMH.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MathP34Benchmark {

    /**
     * Benchmark input state.
     */
    @State(Scope.Thread)
    public static class Input {
        /**
         * Numbers for totient calculation.
         */
        @Param({"315", "1024", "12345"})
        public long n;
    }

    /**
     * Benchmark the primitive totient implementation.
     */
    @Benchmark
    public long primitiveTotient(Input input) {
        return MathP34.totientPhi(input.n);
    }

    /**
     * Benchmark the improved totient implementation.
     */
    @Benchmark
    public long improvedTotient(Input input) {
        return MathP37.totientPhi(input.n);
    }
}
