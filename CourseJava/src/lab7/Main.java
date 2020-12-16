package lab7;

//С использованием streamAPI реализовать следующие методы:
//метод, возвращающий среднее значение списка целых чисел
//метод, приводящий все строки в списке в верхний регистр
//метод возвращающий список квадратов всех уникальных элементов списка


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final int ARRAY_SIZE = 15;

    public static void main(String[] args) {
        try {
            List<Integer> list = Stream.generate(() -> (int)(Math.random() * 15))
                    .limit(ARRAY_SIZE)
                    .collect(Collectors.toList());
            System.out.println(list);

            //среднее значение списка целых чисел
            System.out.printf("Average number = %.2f\n", list.stream().mapToDouble(a -> a).average().getAsDouble());

            //все строки в списке в верхний регистр
            List<String> stringList = new ArrayList<>(Arrays.asList("small ", "characters ", "to ", "upper ", "case\n"));
            stringList.stream()
                    .map(str -> str.toUpperCase())
                    .forEach(System.out::print);

            //список квадратов всех уникальных элементов списка
            list.stream().filter(el -> Collections.frequency(list, el) == 1)
                    .map(el -> Math.pow(el, 2.0))
                    .sorted()
                    .forEach(a -> System.out.printf("%7.0f", a));
            System.out.println();
            //список квадратов всех уникальных элементов списка
            list.stream().distinct()
                    .map(el -> Math.pow(el, 2.0))
                    .sorted()
                    .forEach(a -> System.out.printf("%7.0f", a));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}