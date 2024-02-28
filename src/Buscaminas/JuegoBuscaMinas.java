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
	}

	// Falta que solo sea el número de minas determinadas
	public void iniciarTablero() {
		Random random = new Random();
		int fila, columna;
		int minasColocadas = 0;

		for (int minas = numeroMinas(); minas > 0; minas--) {
			System.out.println(minas);
			do {
				fila = random.nextInt(tam);
				columna = random.nextInt(tam);
			} while (tablero[fila][columna] != 0);
			minasColocadas++;
			tablero[fila][columna] = -1;
		}
		System.out.println("\n" + minasColocadas + "\n");
		for (int i = 0; i < tablero.length; i++)
			for (int j = 0; j < tablero.length; j++)
				System.out.println(Casilla);

	}

	private int numeroMinas() {
		if (dificultad == 1)
			return 30;
		if (dificultad == 2)
			return 60;
		return 10;
	}

}
