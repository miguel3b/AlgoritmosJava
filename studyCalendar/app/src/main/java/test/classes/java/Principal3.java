package test.classes.java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
public class Principal3 {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
        List<Integer> gastos = new ArrayList<Integer>();
        gastos.add(100);
        gastos.add(200);
        gastos.add(300);
        gastos.add(150);
        gastos.add(400);
        gastos.add(800);

        // por default el primer parametro es un acumulador, el segundo es el elemento del array
        gastos.stream().reduce((acumulador,numero)-> {
            return acumulador+numero;
        }).ifPresent(System.out::println);

    }
}