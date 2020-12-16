package lab8;

//Написать аннотацию с целочисленным параметром. Создать класс, содержащий только приватные методы (3-4шт), аннотировать любые из них.
// Вызвать из другого класса все аннотированные методы столько раз, сколько указано в параметре аннотации.

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            AnnotableMethods annotable = new AnnotableMethods();
            for (Method method : AnnotableMethods.class.getDeclaredMethods()) {
                method.setAccessible(true);                                 //disable check access rights
                if (method.isAnnotationPresent(MethodCallNumber.class))     //it has annotation MethodCallNumber
                    for (int i = 0; i < method.getDeclaredAnnotation(MethodCallNumber.class).callNumber(); ++i)
                        method.invoke(annotable);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
