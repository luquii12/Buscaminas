package Buscaminas;

import java.util.Random;

public class JuegoBuscaMinas {
	private Casilla[][] tablero;
	private int dificultad;
	private int tam;

	public JuegoBuscaMinas(int dificultad) {
		this.dificultad = dificultad;

		if (dificultad == 0)
			this.tam = 8;
		if (dificultad == 1)
			this.tam = 12;
		if (dificultad == 2)
			this.tam = 16;

		this.tablero = new Casilla[tam][tam];

		iniciarTablero();
	}

	public void iniciarTablero() {
		instanciarCasillas();

		colocarMinas();
	}

	// Falta cuadrar el tablero cuando el nº de fila o columna es de dos dígitos
	public void imprimirTablero() {
		// Columnas cabecera
		System.out.print("    ");
		for (int columnas = 0; columnas < tablero.length; columnas++)
			System.out.print("|" + (columnas + 1) + "| ");
		System.out.println("\n");

		// Resto tablero
		for (int i = 0; i < tablero.length; i++) {
			System.out.print("|" + (i + 1) + "| ");
			for (int j = 0; j < tablero.length; j++) {
				if (tablero[i][j].isEstaOculta())
					System.out.print("|-| ");
			}
			System.out.println("\n");
		}
	}

	// Métodos privados

	private void instanciarCasillas() {
		for (int i = 0; i < tablero.length; i++)
			for (int j = 0; j < tablero.length; j++)
				tablero[i][j] = new Casilla();
	}

	private void colocarMinas() {
		Random random = new Random();
		int fila, columna;
		int minasColocadas = 0;

		for (int minas = numeroMinas(); minas > 0; minas--) {
			do {
				fila = random.nextInt(tam);
				columna = random.nextInt(tam);
			} while (tablero[fila][columna].isTieneMina());
			minasColocadas++;
			tablero[fila][columna].setTieneMina(true);
		}
	}

	private int numeroMinas() {
		if (dificultad == 1)
			return 30;
		if (dificultad == 2)
			return 60;
		return 10;
	}
}
