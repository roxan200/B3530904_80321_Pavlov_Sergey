package lab8;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnnotableMethods {
    @MethodCallNumber(callNumber = 2)
    private void method1 () {
        System.out.println("The method1 was called");
    }

    @MethodCallNumber(callNumber = 4)
    private void method2 () {
        System.out.println("The method2 was called");
    }

    private void method3 () {
        System.out.println("The method3 was called");
    }

    @MethodCallNumber()
    private void method4 () {
        System.out.println("The method4 was called");
    }
}
