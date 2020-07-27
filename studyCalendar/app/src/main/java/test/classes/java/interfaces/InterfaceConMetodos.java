package test.classes.java.interfaces;

public interface InterfaceConMetodos {


    public void metodo1();


    public default void saludar(){
        System.out.println("Hola mundo");

    }

    public default void despedirse(){
        System.out.println("bye");
    }
}
