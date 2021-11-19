package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Vare vare = new Vare();

        Vare[] array = new Vare[20];

        for (int i = 0; i < 20; i++) {
            array[i] = new Vare();
        }

        int antalVarer;

        antalVarer = laesFraFil(array);

        udskriv(array, antalVarer);

        skrivDataFil(array, antalVarer);

        udskriv(array, antalVarer);

        antalVarer = laesDataFil(array);

        udskriv(array, antalVarer);

        samletPris(array, antalVarer);

    }


    //Skrivning til txt.fil
    public static int laesFraFil(Vare[] array) throws IOException {

        File bestilling = new File("Bestilling.txt");
        if (bestilling.createNewFile()) {
            System.out.println("File created!");
        } else {
            System.out.println("File Already exists!");
        }

        Scanner input = new Scanner(bestilling);

        int i = 0;

        while (input.hasNext()) {

            array[i].setAntal(input.nextInt());
            array[i].setVarer(input.next());
            array[i].setPris(input.nextDouble());
            i++;

        }

        return i;
    }
    //Skrivning til ser.fil
    public static void skrivDataFil(Vare [] array, int antal) throws FileNotFoundException {

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Varer.ser"));

            for (int i = 0; i < antal; i++) {
                out.writeObject(array[i]);
            }

            out.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Læsning fra ser.fil
    public static int laesDataFil(Vare [] array) throws FileNotFoundException {

        int i = 0;

        try {
            FileInputStream f = new FileInputStream("Varer.ser");

            ObjectInputStream input = new ObjectInputStream(f);

            while (f.available() > 0) {
                array[i] = (Vare) input.readObject();
                i++;
            }

            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }


    public static void udskriv (Vare [] a, int antal) {

        for (int i = 0; i < antal; i++) {

            System.out.format("%d %s %.2f \n",a[i].getAntal(),a[i].getVarer(),a[i].getPris());

        }

    }
    public static void samletPris (Vare [] a, int antal) {
        double prisIaltUdenRabat=0.0;
        double samletRabat=0.0;
        double rabat=0.0;

        System.out.format("\n%-7s %-20s  %20s  %20s\n","Stk", "Varenavn", "Samlet pris","Rabat");
        for (int i = 0; i < antal; i++) {
            rabat=0.0;
            prisIaltUdenRabat+=a[i].getAntal()*a[i].getPris();
            if(a[i].getAntal()>10) {
                rabat= a[i].getAntal() * a[i].getPris() * 0.15;
                samletRabat+=rabat;
            }
            System.out.format("%-7d %-20s  %20.2f  %20.2f\n",a[i].getAntal(), a[i].getVarer(), a[i].getAntal() * a[i].getPris(),rabat);
        }
        System.out.format("\nSamlet pris uden rabat: %f",prisIaltUdenRabat);
        System.out.format("\nSamlet rabat: %f",samletRabat);
        System.out.format("\nSamlet pris med rabat: %f",prisIaltUdenRabat-samletRabat);
    }

}
