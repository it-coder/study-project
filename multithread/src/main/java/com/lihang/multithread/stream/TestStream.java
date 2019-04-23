package com.lihang.multithread.stream;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

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

    public static void stream() {
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);

        IntStream.range(1, 3).forEach(System.out::println);

        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * 流数据结构的转换
     */
    public static void streamCovert() {
        // 1. 数组
        String[] strs = new String[]{"a", "b", "c"};
        Stream stream2 = Stream.of(strs);
        String[] objects = (String[]) stream2.toArray(String[]::new);
        // 2. 集合 一个stream只能使用一次
        stream2 = Stream.of(strs);
        List<String> collect = (List<String>)stream2.collect(toList());
        stream2 = Stream.of(strs);
        collect = (List<String>)stream2.collect(Collectors.toCollection(ArrayList::new));
        // set
        //stream2.collect(Collectors.toSet());
        // stack
        //stream2.collect(Collectors.toCollection(Stack::new));

        // 3. 字符串
        stream2 = Stream.of(strs);
        String str = stream2.collect(Collectors.joining(",")).toString();
        System.out.println(str);
    }

    /**
     *  一对一map
     */
    public static void streamMap() {
        List<String> strings = Arrays.asList("a", "b", "c");
        List<String> s = strings.stream().map(x -> x.toUpperCase()).collect(toList());
    }

    /**
     *  一对多 flatMap
     */
    public static void streamFlatMap() {
        List<String> collect = Stream.of(Arrays.asList("a", "b", "c"), Arrays.asList("A", "B", "D")).flatMap(child ->
                child.stream()).collect(toList());

        System.out.println(String.join(",", collect));
    }

    /**
     *  Optional
     */
    public static void optional() {
        String s = "";
        boolean present = Optional.ofNullable(s).isPresent();
        System.out.println(present);
    }

    public static void main(String[] args) {


        optional();


    }
}
