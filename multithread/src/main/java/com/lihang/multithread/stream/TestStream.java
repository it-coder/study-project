package com.lihang.multithread.stream;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author lih
 * @create 2019-04-23-17:11.
 */
public class TestStream {

    /**
     * // 流构建的几种方式
     */
    public static void streamBuilder(){
        // 1. 值
        Stream<String> stream = Stream.of("1","2");
        // 2. array
        String[] strs = new String[]{"a", "b", "c"};
        Stream stream2 = Stream.of(strs);
        Stream stream3 = Arrays.stream(strs);

        // 3. 集合
        List<String> list = Arrays.asList(strs);
        Stream stream4 = list.stream();
    }

    public static void main(String[] args) {
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);

        IntStream.range(1, 3).forEach(System.out::println);

        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        String[] strs = new String[]{"a", "b", "c"};
        Stream stream2 = Stream.of(strs);

        String collect = stream2.collect(Collectors.joining(",")).toString();
        System.out.println(collect);

    }
}
