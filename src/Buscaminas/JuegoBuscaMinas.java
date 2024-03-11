package Buscaminas;

import java.util.Random;

public class JuegoBuscaMinas {
	private Casilla[][] tablero;
	private int dificultad, tam, minasMarcadas;

	public JuegoBuscaMinas(int dificultad) {
		this.minasMarcadas = 0;
		this.dificultad = dificultad;

		if (dificultad == 1)
			this.tam = 8;
		if (dificultad == 2)
			this.tam = 12;
		if (dificultad == 3)
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

	public boolean descubrirCasilla(int fila, int columna) {
		if (fila >= 0 && fila < tam && columna >= 0 && columna < tam) {
			Casilla casilla = tablero[fila][columna];
			if (!casilla.isEstaMarcada() && casilla.isEstaOculta()) {
				casilla.setEstaOculta(false); // Cambiar el estado de oculto a revelado
				calcularMinasCercanas(fila, columna);
				if (casilla.getNumMinasCercanas() == 0 && !casilla.isTieneMina()) {
					descubrirCasillasCercanasSinMinasAlrededor(fila, columna);
				}
				return true;
			}
		}
		return false;
	}

	public boolean marcarCasilla(int fila, int columna) {
		if (fila >= 0 && fila < tam && columna >= 0 && columna < tam) {
			Casilla casilla = tablero[fila][columna];
			if (!casilla.isEstaMarcada() && casilla.isEstaOculta()) {
				casilla.setEstaMarcada(true);
				if (casilla.isTieneMina())
					minasMarcadas++;
				return true;
			}
		}
		return false;
	}

	public int causaTerminacionJuego() {
		if (haMarcadoTodasMinas())
			return 1; // Ha ganado
		if (haDescubiertoCasillaConMina())
			return 2; // Ha perdido
		if (haMarcadoCasillaSinMina())
			return 3; // Ha perdido
		return 0; // Continua partida
	}

	public int numeroMinas() {
		if (dificultad == 2)
			return 30;
		if (dificultad == 3)
			return 60;
		return 10;
	}

	public int getMinasMarcadas() {
		return minasMarcadas;
	}

	public void setMinasMarcadas(int minasMarcadas) {
		this.minasMarcadas = minasMarcadas;
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
	}

	private void imprimirCabeceraFilas() {
		System.out.print("     ");
		for (int columnas = 0; columnas < tablero.length; columnas++)
			System.out.print("|" + (columnas + 1) + "| ");
		System.out.println("\n");
	}

	private void calcularMinasCercanas(int fila, int columna) {
		if ((fila > 0 && fila < tam - 1) && (columna > 0 && columna < tam - 1))
			calcularMinasCercanasCasillaCentro(fila, columna);
		else if ((fila == 0 || fila == tam - 1) && (columna == 0 || columna == tam - 1))
			calcularMinasCercanasCasillaEsquina(fila, columna);
		else
			calcularMinasCercanasCasillaBorde(fila, columna);
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
		if (fila == 0 || fila == tam - 1)
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

	private void descubrirCasillasCercanasSinMinasAlrededor(int fila, int columna) {
		if ((fila > 0 && fila < tam - 1) && (columna > 0 && columna < tam - 1))
			descubrirCasillasCercanasCasillaCentroSinMinasAlrededor(fila, columna);
		else if ((fila == 0 || fila == tam - 1) && (columna == 0 || columna == tam - 1))
			descubrirCasillasCercanasCasillaEsquinaSinMinasAlrededor(fila, columna);
		else
			descubrirCasillasCercanasCasillaBordeSinMinasAlrededor(fila, columna);
	}

	private void descubrirCasillasCercanasCasillaCentroSinMinasAlrededor(int fila, int columna) {
		for (int i = -1; i <= 1; i += 2)
			for (int j = -1; j <= 1; j++)
				if (tablero[fila + i][columna + j].getNumMinasCercanas() == 0)
					descubrirCasilla(fila + i, columna + j);

		for (int j = -1; j <= 1; j += 2)
			if (tablero[fila][columna + j].getNumMinasCercanas() == 0)
				descubrirCasilla(fila, j);
	}

	private void descubrirCasillasCercanasCasillaEsquinaSinMinasAlrededor(int fila, int columna) {
		int incrementoFila, incrementoColumna;

		if (fila == 0)
			incrementoFila = 1;
		else
			incrementoFila = -1;

		if (columna == 0)
			incrementoColumna = 1;
		else
			incrementoColumna = -1;

		if (tablero[fila][columna + incrementoColumna].getNumMinasCercanas() == 0)
			descubrirCasilla(fila, columna + incrementoColumna);

		if (tablero[fila + incrementoFila][columna + incrementoColumna].getNumMinasCercanas() == 0)
			descubrirCasilla(fila + incrementoFila, columna + incrementoColumna);

		if (tablero[fila + incrementoFila][columna].getNumMinasCercanas() == 0)
			descubrirCasilla(fila + incrementoFila, columna);
	}

	private void descubrirCasillasCercanasCasillaBordeSinMinasAlrededor(int fila, int columna) {
		if (fila == 0 || fila == tam - 1)
			descubrirCasillasCercanasCasillaFilaBordeSinMinasAlrededor(fila, columna);
		else
			descubrirCasillasCercanasCasillaColumnaBordeSinMinasAlrededor(fila, columna);
	}

	private void descubrirCasillasCercanasCasillaFilaBordeSinMinasAlrededor(int fila, int columna) {
		int incremento;

		if (fila == 0)
			incremento = 1;
		else
			incremento = -1;

		for (int j = -1; j <= 1; j += 2)
			if (tablero[fila][columna + j].getNumMinasCercanas() == 0)
				descubrirCasilla(fila, columna + j);

		for (int j = -1; j <= 1; j++)
			if (tablero[fila + incremento][columna + j].getNumMinasCercanas() == 0)
				descubrirCasilla(fila + incremento, columna + j);
	}

	private void descubrirCasillasCercanasCasillaColumnaBordeSinMinasAlrededor(int fila, int columna) {
		int incremento;

		if (columna == 0)
			incremento = 1;
		else
			incremento = -1;

		for (int i = -1; i <= 1; i += 2)
			if (tablero[fila + i][columna].getNumMinasCercanas() == 0)
				descubrirCasilla(fila + i, columna + incremento);

		for (int i = -1; i <= 1; i++)
			if (tablero[fila + i][columna + incremento].getNumMinasCercanas() == 0)
				descubrirCasilla(fila + i, columna + incremento);
	}

	private boolean haMarcadoTodasMinas() {
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				Casilla casilla = tablero[i][j];
				if (casilla != null && casilla.isTieneMina() && !casilla.isEstaMarcada())
					return false;
			}
		}
		return true;
	}

	private boolean haDescubiertoCasillaConMina() {
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				Casilla casilla = tablero[i][j];
				if (casilla != null && casilla.isTieneMina() && !casilla.isEstaOculta())
					return true;
			}
		}
		return false;
	}

	private boolean haMarcadoCasillaSinMina() {
		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++) {
				Casilla casilla = tablero[i][j];
				if (casilla != null && !casilla.isTieneMina() && casilla.isEstaMarcada())
					return true;
			}
		}
		return false;
	}
}
