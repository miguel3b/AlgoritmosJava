package test.classes.java;

import test.classes.java.interfaces.InterfaceConMetodos;

public class ClaseImplementacion implements InterfaceConMetodos {


    @Override
    public void metodo1() {
       // LA IMPLEMENTACION DEL CODIGO
    }

    public static void main(String[] args) {
        ClaseImplementacion claseImplementacion = new ClaseImplementacion();

        // los metodos 'default' de las interfaces no requieren implementacion
        claseImplementacion.saludar();
        claseImplementacion.despedirse();
    }
}
