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
		imprimirCabeceraFilas();

		for (int i = 0; i < tablero.length; i++) {
			if (i < 9)
				System.out.print("|" + (i + 1) + "|  ");
			else
				System.out.print("|" + (i + 1) + "| ");
			for (int j = 0; j < tablero.length; j++) {
				if (j < 9) {
					if (tablero[i][j].isEstaOculta() && !tablero[i][j].isEstaMarcada())
						System.out.print("|-| ");
					if (tablero[i][j].isEstaMarcada())
						System.out.print("|F| ");
					if (!tablero[i][j].isEstaOculta() && !tablero[i][j].isEstaMarcada()) {
						calcularMinasCercanas(i, j);
						System.out.print("|" + tablero[i][j].getNumMinasCercanas() + "| ");
					}
				} else {
					if (tablero[i][j].isEstaOculta() && !tablero[i][j].isEstaMarcada())
						System.out.print("|--| ");
					if (tablero[i][j].isEstaMarcada())
						System.out.print("|F | ");
					if (!tablero[i][j].isEstaOculta() && !tablero[i][j].isEstaMarcada()) {
						calcularMinasCercanas(i, j);
						System.out.print("|" + tablero[i][j].getNumMinasCercanas() + " | ");
					}
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
		// Pruebas --> Hasta que estén hechas marcaCasilla() y descubrirCasilla()
		tablero[5][5].setEstaMarcada(true);

		tablero[0][0].setEstaOculta(false);
		tablero[0][4].setEstaOculta(false);
		tablero[0][7].setEstaOculta(false);
		tablero[2][7].setEstaOculta(false);
		tablero[3][3].setEstaOculta(false);
		tablero[5][0].setEstaOculta(false);
		tablero[7][0].setEstaOculta(false);
		tablero[7][2].setEstaOculta(false);
		tablero[7][7].setEstaOculta(false);
	}

	private int numeroMinas() {
		if (dificultad == 1)
			return 30;
		if (dificultad == 2)
			return 60;
		return 10;
	}

	private void imprimirCabeceraFilas() {
		System.out.print("     ");
		for (int columnas = 0; columnas < tablero.length; columnas++)
			System.out.print("|" + (columnas + 1) + "| ");
		System.out.println("\n");
	}

	private void calcularMinasCercanas(int fila, int columna) {
		if ((fila > 0 && fila < 7) && (columna > 0 && columna < 7)) {
			calcularMinasCercanasCasillaCentro(fila, columna);
		} else if ((fila == 0 || fila == 7) && (columna == 0 || columna == 7))
			calcularMinasCercanasCasillaEsquina(fila, columna);
		else {
			calcularMinasCercanasCasillaBorde(fila, columna);
		}
	}

	private void calcularMinasCercanasCasillaCentro(int fila, int columna) {
		int minasCercanas = 0;

		for (int i = -1; i <= 1; i += 2)
			for (int j = -1; j <= 1; j++)
				if (tablero[fila + i][columna + j].isTieneMina())
					minasCercanas++;

		for (int j = -1; j <= 1; j += 2)
			if (tablero[fila][columna + j].isTieneMina())
				minasCercanas++;

		tablero[fila][columna].setNumMinasCercanas(minasCercanas);
	}

	private void calcularMinasCercanasCasillaEsquina(int fila, int columna) {
		int minasCercanas = 0;
		int incrementoFila, incrementoColumna;

		if (fila == 0)
			incrementoFila = 1;
		else
			incrementoFila = -1;

		if (columna == 0)
			incrementoColumna = 1;
		else
			incrementoColumna = -1;

		if (tablero[fila][columna + incrementoColumna].isTieneMina())
			minasCercanas++;

		if (tablero[fila + incrementoFila][columna + incrementoColumna].isTieneMina())
			minasCercanas++;

		if (tablero[fila + incrementoFila][columna].isTieneMina())
			minasCercanas++;

		tablero[fila][columna].setNumMinasCercanas(minasCercanas);
	}

	private void calcularMinasCercanasCasillaBorde(int fila, int columna) {
		if (fila == 0 || fila == 7)
			calcularMinasCercanasCasillaFilaBorde(fila, columna);
		else
			calcularMinasCercanasCasillaColumnaBorde(fila, columna);
	}

	private void calcularMinasCercanasCasillaFilaBorde(int fila, int columna) {
		int minasCercanas = 0;
		int incremento;

		if (fila == 0)
			incremento = 1;
		else
			incremento = -1;

		for (int j = -1; j <= 1; j += 2)
			if (tablero[fila][columna + j].isTieneMina())
				minasCercanas++;

		for (int j = -1; j <= 1; j++)
			if (tablero[fila + incremento][columna + j].isTieneMina())
				minasCercanas++;

		tablero[fila][columna].setNumMinasCercanas(minasCercanas);
	}

	private void calcularMinasCercanasCasillaColumnaBorde(int fila, int columna) {
		int minasCercanas = 0;
		int incremento;

		if (columna == 0)
			incremento = 1;
		else
			incremento = -1;

		for (int i = -1; i <= 1; i += 2)
			if (tablero[fila + i][columna].isTieneMina())
				minasCercanas++;

		for (int i = -1; i <= 1; i++)
			if (tablero[fila + i][columna + incremento].isTieneMina())
				minasCercanas++;

		tablero[fila][columna].setNumMinasCercanas(minasCercanas);
	}
}
