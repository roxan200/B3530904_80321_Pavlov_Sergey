package lab1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Дан массив без повторяющихся элементов. Перемешать его элементы случайным образом так,
// чтобы каждый элемент оказался на новом месте. Вывести исходный и получившийся массивы в консоль.

public class Main {
    static final int LIST_SIZE = 15;

    public static <Elem> boolean AllElementsUnique(List<Elem> set1, List<Elem> set2) {
        for (int i = 0; i < set1.size(); ++i)
            if (set1.get(i).equals(set2.get(i)))
                return false;
        return true;
    }

    public static void main(String[] args) {
        List<Integer> set1 = Stream.generate(() -> (int)(Math.random() * 10)).limit(LIST_SIZE)
                .collect(Collectors.toSet())
                .stream().collect(Collectors.toList());
        List<Integer> set2 = new ArrayList<>(set1);
        while (!AllElementsUnique(set1, set2))
            Collections.shuffle(set2);
        System.out.println(set1);
        System.out.println(set2);

//        try {
//            int answer = (Math.random() >= 0) ? 0 : 1;
//            System.out.println(10 / answer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}