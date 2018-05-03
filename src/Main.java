
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import pt.up.fe.specs.sort.algorithms.ISort;
import pt.up.fe.specs.sort.randomizer.RandomValues;
import pt.up.fe.specs.util.SpecsIo;
import tuner.SortTuner;

/**
 * Copyright 2018 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */
public class Main {
    public static SortTuner tuner = new tuner.SortTuner(5, 100);

    static ISort sort = pt.up.fe.specs.sort.algorithms.QuickSort::sort;

    private static final int NUM_ARGS = 5;

    private static final String CSV_HEADER = "id,length,values";

    private static final String VALUES_SEPARATOR = " ";

    public static void main(String[] args) {
        int[] array = Main.generateArray(args);
        int[] original = Arrays.copyOf(array, array.length);
        Main.sortArray(array);
        Main.generateCsv(args[0], args[1], original);
    }

    private static void sortArray(int[] array) {
        String algName = null;
        int[] original = array;
        while (!tuner.inBestMode(array.length)) {
            array = java.util.Arrays.copyOf(original, original.length);
            String current = tuner.getAlgorithm(array.length).getID();
            if (algName != current) {
                System.out.println("Running with " + current);
                algName = current;
            }
            autotuner.manager.AlgorithmSampling<pt.up.fe.specs.sort.algorithms.ISort, Long> algorithm = tuner
                    .getAlgorithm(array.length);
            sort = algorithm.applyAndGet();
            kadabra.utils.Timers.timer.start();
            sort.sort(array);
            kadabra.utils.Timers.timer.stop();
            algorithm.update(kadabra.utils.Timers.timer.getTime());
        }
        System.out.println(("Sorted: " + (Arrays.toString(array))));
    }

    private static void generateCsv(String outFileName, String uid, int[] array) {
        File outFile = new File(outFileName);
        if (!(outFile.exists())) {
            SpecsIo.write(outFile, ((Main.CSV_HEADER) + "\n"));
        }
        SpecsIo.append(outFile,
                (((((uid + ",") + (array.length)) + ",") + (Arrays.toString(array).replace(",", Main.VALUES_SEPARATOR)))
                        + "\n"));
        String extension = pt.up.fe.specs.util.SpecsIo.getExtension(outFileName);
        outFileName = outFileName.substring(0, outFileName.lastIndexOf(extension)) + "results." + extension;
        generateResults(outFileName, uid, array);
    }

    public static int[] generateArray(String[] args) {
        int pos = 0;
        if ((args.length) < (Main.NUM_ARGS)) {
            System.err.println(
                    ((("The program requires at least " + (Main.NUM_ARGS)) + " arguments:") + (Main.getArgs())));
            System.exit(1);
        }
        String outFileName = args[(pos++)];
        File outFile = new File(outFileName);
        if (!(outFile.exists())) {
            System.out.println(("Will create file " + outFileName));
        } else {
            System.out.println(("Will append file " + outFileName));
        }
        // UID
        pos++;
        // END UID
        int size = Integer.parseInt(args[(pos++)]);
        int lowerBound = Integer.parseInt(args[(pos++)]);
        int upperBound = Integer.parseInt(args[(pos++)]);
        int mode = Integer.parseInt(args[(pos++)]);
        int[] array = RandomValues.genVector(size, lowerBound, upperBound);
        switch (mode) {
        case 0:
            break;
        case 1:
            Arrays.sort(array);
            break;
        case 2:
            Arrays.sort(array);
            Main.reverse(array);
            break;
        case 3:
            if ((args.length) <= pos) {
                System.err.println(
                        "The flow mode requires a stream of #D|#U specifying descendings (e.g. 2D), climbings (e.g. 1U) ");
                System.exit(1);
            }
            String flow = args[(pos++)];
            float mutation = 0.0F;
            if ((args.length) > pos) {
                mutation = Float.parseFloat(args[pos]);
            }
            // String string = Arrays.toString(array);
            // System.out.println("PREVIOUS: " + string);
            Main.orderWithFlow(array, flow, mutation, lowerBound, upperBound);
            // string = Arrays.toString(array);
            // System.out.println("RESULT: " + string);
            break;
        }
        return array;
    }

    private static void orderWithFlow(int[] array, String flow, float mutation, int lowerBound, int upperBound) {
        int aL = array.length;
        int numDirs = 0;
        int[] dirs = new int[(flow.length()) / 2];
        int dirPos = 0;
        for (int i = 0; i < (flow.length()); i += 2) {
            // TODO Add extra verifications
            char num = flow.charAt(i);
            char dir = flow.charAt((i + 1));
            int rep = Integer.parseInt(("" + num));
            numDirs += rep;
            if (dir == 'D') {
                rep = -rep;
            }
            // TODO Add extra verifications
            dirs[(dirPos++)] = rep;
        }
        if (aL < numDirs) {
            System.err.println("The flow mode cannot be bigger than the number of elements to sort");
            System.exit(1);
        }
        int step = aL / numDirs;
        int init = 0;
        Random r = new Random();
        outer: for (int i : dirs) {
            int upperBoundArray = init + ((Math.abs(i)) * step);
            int[] tempArray = Arrays.copyOfRange(array, init, upperBoundArray);
            Arrays.sort(tempArray);
            if (i < 0) {
                Main.reverse(tempArray);
            }
            for (int j = 0; j < (tempArray.length); j++) {
                int newArrayPos = init + j;
                if (newArrayPos >= aL) {
                    break outer;
                }
                if (mutation > 0) {
                    float nextFloat = r.nextFloat();
                    if (nextFloat < mutation) {
                        array[newArrayPos] = r.ints(lowerBound, upperBound).findAny().getAsInt();
                    } else {
                        array[newArrayPos] = tempArray[j];
                    }
                } else {
                    array[newArrayPos] = tempArray[j];
                }
            }
            init = upperBoundArray;
        }
    }

    private static void reverse(int[] array) {
        int length = array.length;
        for (int i = 0; i < (length / 2); i++) {
            int temp = array[i];
            array[i] = array[((length - i) - 1)];
            array[((length - i) - 1)] = temp;
        }
    }

    private static String getArgs() {
        StringBuilder args = new StringBuilder();
        args.append("\n<output CSV file (will append if exists)>");
        args.append("\n<array UID>");
        args.append("\n<array size>");
        args.append("\n<lower bound (inclusive)>");
        args.append("\n<upper bound (exclusive)>");
        args.append("\n[0:random|1:ordered|2:reversed|3:flow");
        args.append("\n  (if flow: (#D|#U)+ to define flow)");
        return args.toString();
    }

    private static void generateResults(String outFileName, String uid, int[] array) {
        File outFile = new File(outFileName);
        if (!(outFile.exists())) {
            SpecsIo.write(outFile, ("id,algorithm,avg time(ns)" + "\n"));
        }
        System.out.println("-= EXPLORATION REPORT =-");
        System.out.println(tuner);
        autotuner.manager.AlgorithmSampling<pt.up.fe.specs.sort.algorithms.ISort, Long> best = tuner
                .getBest(array.length);
        SpecsIo.append(outFile, uid + "," + best.getID() + "," + best.getMeasurer().getValue() + "\n");
    }
}
