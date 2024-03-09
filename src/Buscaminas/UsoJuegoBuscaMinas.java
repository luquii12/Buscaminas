package Buscaminas;

import java.util.Scanner;

public class UsoJuegoBuscaMinas {

	public static void main(String[] args) {
		JuegoBuscaMinas juego = new JuegoBuscaMinas(0);
		System.out.println("");
		System.out.println("Menú principal:");
		System.out.println("1-Instrucciones");
		System.out.println("2-Configuración");
		System.out.println("3-Nuevo juego");
		System.out.println("4-Salir");
		System.out.print("Seleccione una opción: ");

		Scanner scanner = new Scanner(System.in);
		int opcion = scanner.nextInt();

		switch (opcion) {
			case 1:

				break;
			case 2:

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
				System.out.println("Saliendo");

				break;
			default:
				System.out.println("Error al introducir la opcion");
				break;

		}
	}

}
