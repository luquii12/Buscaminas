package Buscaminas;

import java.util.Scanner;

public class UsoJuegoBuscaMinas {

	static Scanner sc = new Scanner(System.in);
	static JuegoBuscaMinas juego;
	static int dificultad = 1;
	static boolean[] dificultadSeleccionada = new boolean[3];

	public static void main(String[] args) {
		int opcion;
		dificultadSeleccionada[0] = true;

		System.out.println("=== BIENVENIDO AL JUEGO DEL BUSCAMINAS ===");
		do {
			opcion = pedirOpcion();
			System.out.println();

			switch (opcion) {
			case 1:
				mostrarInstrucciones();
				break;
			case 2:
				configuracion();
				break;
			case 3:
				// Jugar
				break;
			case 4:
				System.out.println("Hasta luego");
				break;
			default:
				System.out.println("¡Opción incorrecta!");
			}
		} while (opcion != 4);
	}

	public static int pedirOpcion() {
		System.out.println("\n1 - Instrucciones");
		System.out.println("2 - Configuración");
		System.out.println("3 - Jugar");
		System.out.println("4 - Salir");
		System.out.print("Opción: ");
		return Integer.parseInt(sc.nextLine());
	}

	public static void mostrarInstrucciones() {
		System.out.println("Introduzca una \"d\", descubrir, o un \"m\", marcar, "
				+ "\nseguido de la fila (1-8/12/16) y la columna (1-8/12/16) " + "\nde la casilla que se quiere descubrir/marcar.");
		System.out.println("8 -> Principiante \n12 -> Amateur \n16 -> Avanzado");
		System.out.println("El formato tiene que ser el siguiente: d/m f,c");
	}

	public static void configuracion() {
		dificultad = pedirDificultad();

		switch (dificultad) {
		case 1:
			dificultad = 1;
			break;
		case 2:
			dificultad = 2;
			break;
		case 3:
			dificultad = 3;
			break;
		default:
			System.out.println("¡Opción incorrecta!");
		}

		if (dificultad >= 1 && dificultad <= 3)
			indicarSeleccionDificultad();
	}

	public static int pedirDificultad() {
		String actual = " (Actual)";

		System.out.println("Seleccione la dificultad:");
		if (dificultadSeleccionada[0])
			System.out.println("1 - Principiante" + actual);
		else
			System.out.println("1 - Principiante");
		if (dificultadSeleccionada[1])
			System.out.println("2 - Amateur" + actual);
		else
			System.out.println("2 - Amateur");
		if (dificultadSeleccionada[2])
			System.out.println("3 - Avanzado" + actual);
		else
			System.out.println("3 - Avanzado");
		System.out.print("Dificultad: ");
		return Integer.parseInt(sc.nextLine());
	}

	public static void indicarSeleccionDificultad() {
		for (int i = 0; i < dificultadSeleccionada.length; i++) {
			if (i == dificultad - 1)
				if (!dificultadSeleccionada[i]) {
					dificultadSeleccionada[i] = true;
					System.out.println("Dificultad cambiada");
				} else
					System.out.println("Ya estaba seleccionada esta dificultad");
			else
				dificultadSeleccionada[i] = false;
		}
	}
}
