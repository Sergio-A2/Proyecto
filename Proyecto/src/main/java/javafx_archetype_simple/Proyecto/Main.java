package javafx_archetype_simple.Proyecto;

import java.util.*;

class Intento {
 int[] numeros;
 int famas;
 int toques;

 public Intento(int[] numeros, int famas, int toques) {
     this.numeros = numeros;
     this.famas = famas;
     this.toques = toques;
 }
}

public class Main {
 // Prueba de codigo secreto
 static int[] secreto = {1, 2, 3, 4}; 
 static Scanner sc = new Scanner(System.in);
 static List<Intento> historial = new ArrayList<>();

 public static void main(String[] args) {
     System.out.println("--- Codigo Secreto  ---");
     System.out.println("Adivina los 4 números (del 0 al 9). El secreto es fijo: 1 2 3 4 (Debug)");
     
     jugarRecursivo(1);
 }

 static void jugarRecursivo(int turno) {
	 // turnos con recursividad
     if (turno > 6) {
         System.out.println("Te quedaste sin intentos. Perdiste.");
         return;
     }

     System.out.println("\nTurno " + turno + ". Ingresa 4 números separados por espacio:");
     int[] entrada = new int[4];
     try {
         for (int i = 0; i < 4; i++) {
             entrada[i] = sc.nextInt();
         }
     } catch (Exception e) {
         System.out.println("Error: Ingresa solo números.");
         sc.next(); 
         jugarRecursivo(turno); 
         return;
     }

     // logica de las fmas
     int famas = 0;
     for (int i = 0; i < 4; i++) {
         if (entrada[i] == secreto[i]) {
             famas++;
         }
     }

     // logica de los toques
     int toques = 0;
     for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
             if (i != j && entrada[i] == secreto[j]) {
                 toques++;
             }
         }
     }

     System.out.println("Resultado: " + famas + " Famas, " + toques + " Toques.");
     
     historial.add(new Intento(entrada, famas, toques));

     if (famas == 4) {
         System.out.println("¡FELICIDADES! Adivinaste el código.");
     } else {
         jugarRecursivo(turno + 1);
     }
 }
}