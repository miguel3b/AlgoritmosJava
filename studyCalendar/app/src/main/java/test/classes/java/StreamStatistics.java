package test.classes.java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Stream;

import test.classes.java.entity.Persona;

public class StreamStatistics {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {

        Persona persona1 = new Persona("Jose", 25);
        Persona persona2 = new Persona("David", 50);
        Persona persona3 = new Persona("Miguel", 30);

        List<Persona> personas = Arrays.asList(persona1, persona2, persona3);
        System.out.println("-------------------------------------------------------");
        DoubleSummaryStatistics estadisticas =
                personas.stream().mapToDouble(Persona::getEdad).summaryStatistics();
        System.out.println(estadisticas.getSum());  // imprime 105.0
        System.out.println("-------------------------------------------------------");
        // importante que dentro del metodo .mapToInt  la persona sea con 'P' mayuscula
        personas.stream().mapToInt(Persona::getEdad).max().ifPresent(System.out::println); // imprime 50
        System.out.println("---------------------------va el minimo----------------------------");
        int EdadMinima = personas.stream().mapToInt(Persona::getEdad).min().getAsInt();
        System.out.println("--------------------- ----------------------------------");
        // Con este metodo ordenamos el array en base a la edad de las personas
        personas.sort((p1, p2) -> {
            return p1.getEdad() > p2.getEdad() ? 1 : -1;
        });
        System.out.println("-------------------------------------------------------");
        // Esta linea imprime el nombre
        personas.forEach((final Persona persona) -> System.out.println(persona.getNombre()));
        // Esta linea imprime el objeto
        personas.stream().forEach(System.out::println);
        /*imprime
         * jose
         * miguel
         * david
         * */
        System.out.println("-------------------------------------------------------");
        // Retorna la persona con mayor edad
        Persona personaConMasEdad = personas.stream().reduce((p1, p2) -> {
            return p1.getEdad() > persona2.getEdad() ? p1 : p2;
        }).get();
        System.out.println(personaConMasEdad.toString());
        System.out.println("-------------------------------------------------------");
        //Lo que hacemos primero con el mapToInt es "convertir" el array a uno de timpo entero y con el .reduce sumamos todos los valores
        personas.stream().mapToInt(Persona::getEdad).reduce((p1, p2) -> {
            return p1 + p2;
        }).ifPresent(System.out::println);
        System.out.println("-------------------------------------------------------");
        // Esta linea hace lo mismo que el ejemplo anterior
        int suma = personas.stream().mapToInt(Persona::getEdad).sum();

        personas.stream()
                .filter(x -> !x.getNombre().equals("Systems"))
                .sorted((x, y) -> x.getNombre().compareToIgnoreCase(y.getNombre()))
                .distinct()
                .forEach(System.out::println);

        System.out.println("-------------------------------------------------------");

    }
}
