package tuner;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import autotuner.algorithm.Algorithm;
import autotuner.algorithm.provider.AlgorithmListProvider;
import autotuner.algorithm.provider.AlgorithmProvider;
import autotuner.manager.ConfigProvider;
import autotuner.manager.ExplorationSupervisor;
import autotuner.measurer.Measurer;
import pt.up.fe.specs.sort.algorithms.ISort;

public class SortTuner extends ExplorationSupervisor<Integer, ISort, Long> {
    private AlgorithmListProvider<ISort> algListProvider;

    public SortTuner(int warmup, int samples) {
        super(warmup, samples);
        initAlgProvider();
    }

    private void initAlgProvider() {
        algListProvider = new AlgorithmListProvider<>();
        algListProvider // HI
                // .algorithm(() -> new autotuner.algorithm.provider.SingleAlgorithmProvider<>(
                // pt.up.fe.specs.sort.algorithms.CountingSort::sort, "CountingSort"))
                .algorithm(() -> new autotuner.algorithm.provider.SingleAlgorithmProvider<>(
                        pt.up.fe.specs.sort.algorithms.HeapSort::sort, "HeapSort"))
                // .algorithm(() -> new autotuner.algorithm.provider.SingleAlgorithmProvider<>(
                // pt.up.fe.specs.sort.algorithms.InsertionSort::sort, "InsertionSort"))
                .algorithm(() -> new autotuner.algorithm.provider.SingleAlgorithmProvider<>(
                        pt.up.fe.specs.sort.algorithms.MergerSort::sort, "MergerSort"))
        // .algorithm(() -> new autotuner.algorithm.provider.SingleAlgorithmProvider<>(
        // pt.up.fe.specs.sort.algorithms.QuickSort::sort, "QuickSort"))
        // .algorithm(() -> new autotuner.algorithm.provider.SingleAlgorithmProvider<>(
        // pt.up.fe.specs.sort.algorithms.SelectionSort::sort, "SelectionSort"))
        ;
    }

    @Override
    protected Algorithm<ISort> defaultAlgorithm() {
        return new autotuner.algorithm.SimpleAlgorithm<>(pt.up.fe.specs.sort.algorithms.QuickSort::sort, "original");
    }

    @Override
    protected Supplier<Measurer<Long>> measurerProvider() {
        return () -> new autotuner.measurer.AvgLongMeasurer();
    }

    @Override
    protected ConfigProvider<ISort> configurationProvider() {
        return autotuner.configs.ConfigFactory::normal;
    }

    @Override
    protected BiFunction<Integer, Integer, Double> distanceProvider() {
        return null;
    }

    @Override
    protected List<AlgorithmProvider<ISort>> getAlgorithms() {
        return algListProvider.build();
    }
}
