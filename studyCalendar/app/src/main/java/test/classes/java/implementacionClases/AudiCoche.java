package test.classes.java.implementacionClases;

import test.classes.java.abstractClass.Coche;

public class AudiCoche extends Coche {


    public void recibirCoche(Coche coche){



    }

    public static void main(String[] args) {

        Coche coche = new AudiCoche();

        AudiCoche audiCoche = new AudiCoche();
        audiCoche.recibirCoche(coche);

    }
}
