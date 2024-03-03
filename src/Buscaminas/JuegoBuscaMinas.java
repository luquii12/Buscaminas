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

	public void imprimirTablero() {
		imprimirCabezeraFilas();

		for (int i = 0; i < tablero.length; i++) {
			if (i < 9)
				System.out.print("|" + (i + 1) + "|  ");
			else
				System.out.print("|" + (i + 1) + "| ");
			for (int j = 0; j < tablero.length; j++) {
				if (j < 9) {
					if (tablero[i][j].isEstaOculta())
						System.out.print("|-| ");
					if (tablero[i][j].isEstaMarcada())
						System.out.print("|F| ");
					if (!tablero[i][j].isEstaOculta() && !tablero[i][j].isEstaMarcada())
						System.out.print("|" + tablero[i][j].getNumMinasCercanas() + "| ");
				} else {
					if (tablero[i][j].isEstaOculta())
						System.out.print("|--| ");
					if (tablero[i][j].isEstaMarcada())
						System.out.print("|F | ");
					if (!tablero[i][j].isEstaOculta() && !tablero[i][j].isEstaMarcada())
						System.out.print("|" + tablero[i][j].getNumMinasCercanas() + " | ");
				}
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

		for (int minas = numeroMinas(); minas > 0; minas--) {
			do {
				fila = random.nextInt(tam);
				columna = random.nextInt(tam);
			} while (tablero[fila][columna].isTieneMina());
			tablero[fila][columna].setTieneMina(true);
		}
		tablero[12][12].setEstaMarcada(true);
		tablero[12][12].setEstaOculta(false);
		tablero[15][15].setEstaOculta(false);
	}

	private int numeroMinas() {
		if (dificultad == 1)
			return 30;
		if (dificultad == 2)
			return 60;
		return 10;
	}

	private void imprimirCabezeraFilas() {
		System.out.print("     ");
		for (int columnas = 0; columnas < tablero.length; columnas++)
			System.out.print("|" + (columnas + 1) + "| ");
		System.out.println("\n");
	}
}
