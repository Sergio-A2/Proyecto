package javafx_archetype_simple.Proyecto;

import java.util.*;

//se cambio la clase para que se use dentro de un arbol
class Intento {
 int[] numeros;
 int famas;
 int toques;
 int valorNumerico; 

 public Intento(int[] numeros, int famas, int toques) {
     this.numeros = numeros;
     this.famas = famas;
     this.toques = toques;
     
     this.valorNumerico = 0;
     for (int n : numeros) {
         this.valorNumerico = this.valorNumerico * 10 + n;
     }
 }
 
 public int obtenerPuntaje() {
     return (famas * 10) + toques;
 }

 @Override
 public String toString() {
     return Arrays.toString(numeros) + " -> Famas: " + famas + ", Toques: " + toques;
 }
}

class NodoArbol {
 Intento dato;
 NodoArbol izquierda, derecha;

 public NodoArbol(Intento dato) {
     this.dato = dato;
     this.izquierda = this.derecha = null;
 }
}

public class Main {
 static int[] codigoSecreto;
 static Scanner sc = new Scanner(System.in);
 static List<Intento> historial = new ArrayList<>();
 
 //  arbol
 static NodoArbol raizArbol = null;

 public static void main(String[] args) {
     System.out.println("--- Codigo Secreto");
     codigoSecreto = generarCodigoSecreto();
      System.out.println("DEBUG: " + Arrays.toString(codigoSecreto)); // debug
     
     jugarRecursivo(1);
 }

 static void jugarRecursivo(int turno) {
     if (turno > 6) {
         System.out.println("¡Perdiste! El código era: " + Arrays.toString(codigoSecreto));
         mostrarRanking(); 
         return;
     }

     System.out.println("\nTurno " + turno + ". Ingresa 4 números (0-9):");
     int[] entrada = new int[4];
     try {
         for (int i = 0; i < 4; i++) entrada[i] = sc.nextInt();
     } catch (Exception e) {
         System.out.println("Error: Entrada inválida.");
         sc.nextLine(); 
         jugarRecursivo(turno);
         return;
     }

    
     int valorEntrada = convertirAInt(entrada);
     if (buscarEnArbol(raizArbol, valorEntrada)) {
         System.out.println(" Ya intentaste esa combinación antes. No cuenta turno.");
         jugarRecursivo(turno);
         return;
     }

     // verificaciones
     int famas = 0;
     for (int i = 0; i < 4; i++) if (entrada[i] == codigoSecreto[i]) famas++;

     int toques = 0;
     for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
             if (i != j && entrada[i] == codigoSecreto[j]) toques++;
         }
     }

     System.out.println("Resultado: " + famas + " Famas, " + toques + " Toques.");
     
     Intento nuevoIntento = new Intento(entrada, famas, toques);
     historial.add(nuevoIntento);
     
     // Insertar en el arbol
     raizArbol = insertarEnArbol(raizArbol, nuevoIntento);

     if (famas == 4) {
         System.out.println("¡GANASTE! Felicidades.");
         mostrarRanking(); 
     } else {
         jugarRecursivo(turno + 1);
     }
 }

 // arbol
 static NodoArbol insertarEnArbol(NodoArbol nodo, Intento valor) {
     if (nodo == null) return new NodoArbol(valor);
     if (valor.valorNumerico < nodo.dato.valorNumerico) {
         nodo.izquierda = insertarEnArbol(nodo.izquierda, valor);
     } else if (valor.valorNumerico > nodo.dato.valorNumerico) {
         nodo.derecha = insertarEnArbol(nodo.derecha, valor);
     }
     return nodo;
 }

 static boolean buscarEnArbol(NodoArbol nodo, int valorBusqueda) {
     if (nodo == null) return false;
     if (valorBusqueda == nodo.dato.valorNumerico) return true;
     return valorBusqueda < nodo.dato.valorNumerico
         ? buscarEnArbol(nodo.izquierda, valorBusqueda)
         : buscarEnArbol(nodo.derecha, valorBusqueda);
 }

 static void mostrarRanking() {
     System.out.println("\n--- TUS MEJORES JUGADAS (Ordenamiento Burbuja) ---");
     
     Intento[] arr = historial.toArray(new Intento[0]);
     int n = arr.length;
     
     for (int i = 0; i < n - 1; i++) {
         for (int j = 0; j < n - i - 1; j++) {
             
             if (arr[j].obtenerPuntaje() < arr[j + 1].obtenerPuntaje()) {
                 Intento temp = arr[j];
                 arr[j] = arr[j + 1];
                 arr[j + 1] = temp;
             }
         }
     }

     for (Intento intento : arr) {
         System.out.println(intento.toString());
     }
 }

 // crear el codigo
 static int[] generarCodigoSecreto() {
     List<Integer> numeros = new ArrayList<>();
     for (int i = 0; i <= 9; i++) numeros.add(i);
     Collections.shuffle(numeros);
     int[] codigo = new int[4];
     for (int i = 0; i < 4; i++) codigo[i] = numeros.get(i);
     return codigo;
 }

 static int convertirAInt(int[] arr) {
     int val = 0;
     for (int n : arr) val = val * 10 + n;
     return val;
 }
}