package test.classes.java.implementacionClases;

import test.classes.java.abstractClass.Coche;

public class MainCoche {

    public void recibirCoche(Coche coche){


    }


    public static void main(String[] args) {

        MainCoche mainCoche = new MainCoche();
        AudiCoche audiCoche = new AudiCoche();

        mainCoche.recibirCoche(audiCoche);

    }
}
