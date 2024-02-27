package Buscaminas;

public class Casilla {
	private boolean tieneMina, estaMarcada, estaOculta;
	private int numMinasCercanas;

	public Casilla() {
		tieneMina = false;
		estaMarcada = false;
		estaOculta = true;
		numMinasCercanas = 0;
	}

	public boolean isTieneMina() {
		return tieneMina;
	}

	public void setTieneMina(boolean tieneMina) {
		this.tieneMina = tieneMina;
	}

	public boolean isEstaMarcada() {
		return estaMarcada;
	}

	public void setEstaMarcada(boolean estaMarcada) {
		this.estaMarcada = estaMarcada;
	}

	public boolean isEstaOculta() {
		return estaOculta;
	}

	public void setEstaOculta(boolean estaOculta) {
		this.estaOculta = estaOculta;
	}

	public int getNumMinasCercanas() {
		return numMinasCercanas;
	}

	public void setNumMinasCercanas(int numMinasCercanas) {
		this.numMinasCercanas = numMinasCercanas;
	}
}
