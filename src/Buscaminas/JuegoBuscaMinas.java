package Buscaminas;

import java.util.Random;

public class JuegoBuscaMinas {
	private int[][] tablero;
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

		this.tablero = new int[tam][tam];
	}

	// Falta que solo sea el número de minas determinadas
	public void iniciarTablero() {
		int minas = numeroMinas();
		System.out.println(minas);

		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				if (ponerMina()) {
					tablero[i][j] = -1;
					minas--;
				}
				System.out.println("[" + i + "][" + j + "]=" + tablero[i][j]);
			}
		}
	}

	private int numeroMinas() {
		if (dificultad == 1)
			return 30;
		if (dificultad == 2)
			return 60;
		return 10;
	}

	private boolean ponerMina() {
		Random random = new Random();
		int hayMina = random.nextInt(2);

		if (hayMina == 0)
			return false;
		return true;
	}
}
