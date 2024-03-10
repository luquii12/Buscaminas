package Buscaminas;

import java.util.Scanner;

public class UsoJuegoBuscaMinas {

	static Scanner sc = new Scanner(System.in);
	static int dificultad = 1;
	static JuegoBuscaMinas juego;
	static boolean[] dificultadSeleccionada = new boolean[3];
	static boolean dificultaCambiada = false;

	public static void main(String[] args) {
		int opcion;
		int partidasJugadas = 0;
		dificultadSeleccionada[0] = true;

		System.out.println("=== BIENVENIDO AL JUEGO DEL BUSCAMINAS ===");
		do {
			opcion = pedirOpcion();
			System.out.println();

			switch (opcion) {
			case 1:
				mostrarInstrucciones();
				lineaSeparacion();
				break;
			case 2:
				configuracion();
				lineaSeparacion();
				break;
			case 3:
				jugar(partidasJugadas);
				partidasJugadas++;
				lineaSeparacion();
				break;
			case 4:
				System.out.println("Hasta luego");
				break;
			default:
				System.out.println("¡Opción incorrecta!");
				lineaSeparacion();
			}
		} while (opcion != 4);
	}

	public static void lineaSeparacion() {
		System.out.println();
		for (int i = 0; i < 42; i++)
			System.out.print("-");
		System.out.println();
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
				+ "\nseguido de la fila (1-8/12/16(1)) y la columna (1-8/12/16(1)) "
				+ "\nde la casilla que se quiere descubrir/marcar.");
		System.out.println("El formato tiene que ser el siguiente: d/m f,c\n");
		System.out.println("(1)\n8 -> Principiante\n12 -> Amateur\n16 -> Avanzado");
	}

	public static void configuracion() {
		int dificultadJuego = pedirDificultad();

		switch (dificultadJuego) {
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

		if (dificultadJuego >= 1 && dificultadJuego <= 3)
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
			if (i == dificultad - 1) {
				if (!dificultadSeleccionada[i]) {
					dificultadSeleccionada[i] = true;
					System.out.println("Dificultad cambiada");
					dificultaCambiada = true;
				} else {
					System.out.println("Ya estaba seleccionada esta dificultad");
					dificultaCambiada = false;
				}
			} else
				dificultadSeleccionada[i] = false;
		}
	}

	public static void jugar(int partidasJugadas) {
		boolean entradaValida = false;
		int fila = 0, columna = 0;

		System.out.println("Iniciando juego...\n\n");

		cargarTablero(partidasJugadas);

		juego.imprimirTablero();

		do {
			System.out.print("Seleccione una casilla: ");
			String casillaSeleccionada = sc.nextLine();
			System.out.println();

			switch (casillaSeleccionada.length()) {
			case 5:
				if (accionValidaCasillaInferiorA10x10(casillaSeleccionada)) {
					entradaValida = true;
					fila = Integer.parseInt(casillaSeleccionada.substring(2, 3));
					columna = Integer.parseInt(casillaSeleccionada.substring(4, 5));
				}
				break;
			case 6:
				if (casillaSeleccionada.indexOf(",") == 3) {
					if (accionValidaCasillaSoloColumnaMayorQue9(casillaSeleccionada)) {
						entradaValida = true;
						fila = Integer.parseInt(casillaSeleccionada.substring(2, 3));
						columna = Integer.parseInt(casillaSeleccionada.substring(4, 6));
					}
				}

				if (casillaSeleccionada.indexOf(",") == 4) {
					if (accionValidaCasillaSoloFilaMayorQue9(casillaSeleccionada)) {
						entradaValida = true;
						fila = Integer.parseInt(casillaSeleccionada.substring(2, 4));
						columna = Integer.parseInt(casillaSeleccionada.substring(5, 6));
					}
				}
				break;
			case 7:
				if (accionValidaCasillaSuperiorA9x9(casillaSeleccionada)) {
					entradaValida = true;
					fila = Integer.parseInt(casillaSeleccionada.substring(2, 4));
					columna = Integer.parseInt(casillaSeleccionada.substring(5, 7));
				}
				break;
			default:
				entradaValida = false;
			}

			if (!entradaValida)
				System.out.println("Entrada inválida, introduzca casilla en el formato correcto (ej. d 4,5).\n");
			else {
				if (casillaSeleccionada.charAt(0) == 'm')
					marcarCasilla(fila, columna);

				if (casillaSeleccionada.charAt(0) == 'd')
					descubrirCasilla(fila, columna);

				resultadoPartida();
			}
		} while (!entradaValida || juego.causaTerminacionJuego() == 0);
	}

	public static void cargarTablero(int partidasJugadas) {
		if (partidasJugadas == 0 || dificultaCambiada)
			juego = new JuegoBuscaMinas(dificultad);
		else
			juego.iniciarTablero();
	}

	public static boolean accionValidaCasillaInferiorA10x10(String casillaSeleccionada) {
		return casillaSeleccionada.charAt(0) == 'm' || casillaSeleccionada.charAt(0) == 'd'
				&& Character.isDigit(casillaSeleccionada.charAt(2)) && Character.isDigit(casillaSeleccionada.charAt(4));
	}

	public static boolean accionValidaCasillaSoloFilaMayorQue9(String casillaSeleccionada) {
		return casillaSeleccionada.charAt(0) == 'm' || casillaSeleccionada.charAt(0) == 'd'
				&& Character.isDigit(casillaSeleccionada.charAt(2)) && Character.isDigit(casillaSeleccionada.charAt(3))
				&& Character.isDigit(casillaSeleccionada.charAt(5));
	}

	public static boolean accionValidaCasillaSoloColumnaMayorQue9(String casillaSeleccionada) {
		return casillaSeleccionada.charAt(0) == 'm' || casillaSeleccionada.charAt(0) == 'd'
				&& Character.isDigit(casillaSeleccionada.charAt(2)) && Character.isDigit(casillaSeleccionada.charAt(4))
				&& Character.isDigit(casillaSeleccionada.charAt(5));
	}

	public static boolean accionValidaCasillaSuperiorA9x9(String casillaSeleccionada) {
		return casillaSeleccionada.charAt(0) == 'm' || casillaSeleccionada.charAt(0) == 'd'
				&& Character.isDigit(casillaSeleccionada.charAt(2)) && Character.isDigit(casillaSeleccionada.charAt(3))
				&& Character.isDigit(casillaSeleccionada.charAt(5)) && Character.isDigit(casillaSeleccionada.charAt(6));
	}

	public static void descubrirCasilla(int fila, int columna) {
		if (juego.descubrirCasilla(fila - 1, columna - 1)) {
			if (juego.causaTerminacionJuego() == 0)
				System.out.println("Casilla descubierta correctamente.\n");
		} else
			System.out.println("No se ha podido descubrir la casilla.\n");
	}

	public static void marcarCasilla(int fila, int columna) {
		if (juego.marcarCasilla(fila - 1, columna - 1)) {
			if (juego.causaTerminacionJuego() == 0) {
				System.out.println("Casilla marcada correctamente.");
				System.out.println("Minas marcadas: " + juego.getMinasMarcadas());
				System.out.println("Quedan " + (juego.numeroMinas() - juego.getMinasMarcadas()) + " minas\n");
			}
		} else
			System.out.println("No se ha podido marcar la casilla.\n");
	}

	public static void resultadoPartida() {
		if (juego.causaTerminacionJuego() == 0)
			juego.imprimirTablero();
		else if (juego.causaTerminacionJuego() == 1)
			System.out.println("Has ganado");
		else if (juego.causaTerminacionJuego() == 2)
			System.out.println("Has perdido, has descubierto una casilla con mina");
		else if (juego.causaTerminacionJuego() == 3)
			System.out.println("Has perdido, has marcado una casilla sin mina");
	}
}
