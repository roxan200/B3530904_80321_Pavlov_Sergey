package lab3;

//Дана строка. Разделить строку на фрагменты по три подряд идущих символа.
//В каждом фрагменте средний символ заменить на случайный символ, не совпадающий ни с одним из символов этого фрагмента.
//Вывести в консоль фрагменты, упорядоченные по алфавиту.

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    public static HashMap<String, String> changeMiddleChar(HashMap<String, String> map) {
        for (String key : map.keySet()) {
            char randomChar;
            do {
                randomChar = (char)((int)((Math.random() + 32.0 / 100.0) * (128 - 32)));//range[32, 128]
            } while (randomChar == key.indexOf(0) || randomChar == key.indexOf(1) || randomChar == key.indexOf(2));
            map.put(key, key.charAt(0) + String.valueOf(randomChar) + key.charAt(2));
        }
        return map;
    }

    public static void main(String[] args) {
        Integer wordSize = 3;
        String str = "mangunsunru";
        char[] ch = {'n', 'c', 'a', 'n'};
        for (int j = 0; j < ch.length; ++j) {
            str += ch[j];
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i + wordSize <= str.length(); i += wordSize)
                map.put(str.substring(i, i + wordSize), str.substring(i, i + wordSize));
            changeMiddleChar(map);
            System.out.println("String length = " + str.length());
            map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(e -> System.out.println("\"" + e.getKey() + "\" - \"" + e.getValue() + "\""));
        }

    }
}