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
								System.out.println("Iniciando juego");

				juego.iniciarTablero();
				juego.imprimirTablero();
				// problematicas posibles
					// longitud de la cadena -> ej. d4,5 (longitud = 4)
					// primer digito no sea ni 'm' ni 'd'
					// que la fila y la columna no sean numeros y que no este acorde a la dificultad
					// la coma nos la pela

				boolean entradaValida = false;
				do {
					scanner.nextLine();
					System.out.print("Seleccione una casilla: ");
					String casillaSeleccionada = scanner.nextLine();
					

					// Verificar las condiciones problemáticas
					if (casillaSeleccionada.length() != 4
							|| casillaSeleccionada.charAt(0) != 'm' && casillaSeleccionada.charAt(0) != 'd'
							|| !Character.isDigit(casillaSeleccionada.charAt(1))
							|| !Character.isDigit(casillaSeleccionada.charAt(3))) {
						System.out.println(
								"Entrada inválida, introduzca casilla en el formato correcto (ej. d4,5).");
					} else {
						entradaValida = true;

						int fila = Integer.parseInt(casillaSeleccionada.substring(1, 2));
						int columna = Integer.parseInt(casillaSeleccionada.substring(3, 4));						

						System.out.println(fila);
						System.out.println(columna);

						if (casillaSeleccionada.charAt(0) != 'm') {
							if (juego.marcarCasilla(fila, columna))
								System.out.println("Casilla marcada correctamente.");
							else
								System.out.println("No se ha podido marcar la casilla.");
						}

						if (casillaSeleccionada.charAt(0) != 'd') {
							if (juego.descubrirCasilla(fila, columna))
								System.out.println("Casilla descubierta correctamente.");
							else
								System.out.println("No se ha podido descubrir la casilla.");
						}

						juego.imprimirTablero();
					}

				} while (!entradaValida || juego.causaTerminacionJuego() == 0);

				if (juego.causaTerminacionJuego()==1) {
					System.out.println("Has ganado");
				}else if (juego.causaTerminacionJuego()==2) {
					System.out.println("Has perdido, has decubierto casilla con mina");
				}else if (juego.causaTerminacionJuego()==2) {
					System.out.println("Has perdido, has marcado casilla sin mina");
				}
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
