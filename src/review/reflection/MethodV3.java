package review.reflection;

import review.reflection.data.Calculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MethodV3 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        System.out.print("호출 메서드: ");
        String methodName = sc.nextLine();

        System.out.print("숫자1: ");
        int firstValue = sc.nextInt();
        sc.nextLine();
        System.out.print("숫자2: ");
        int secondValue = sc.nextInt();
        sc.nextLine();

        Calculator calculator = new Calculator();
        // 호출할 메서드를 변수 이름으로 동적으로 선택

        Class<? extends Calculator> aClass = calculator.getClass();
        Method method = aClass.getMethod(methodName, int.class, int.class);
        Object returnValue = method.invoke(calculator, firstValue, secondValue);
        System.out.println("결과 = " + returnValue);

    }
}
