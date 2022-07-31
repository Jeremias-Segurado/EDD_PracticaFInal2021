package TDAGrafo;

import TDALista.Position;

public class Arco<V, E> implements Edge<E> {

	protected E rotulo;
	protected Position<Arco<V, E>> posicionEnListaArcos;
	protected Vertice<V> pred, suces;

	public Arco(Position<Arco<V, E>> posicion) {
		posicionEnListaArcos = posicion;
	}

	public Arco() {

	}

	public E element() {
		return rotulo;
	}

	public void setElement(E elem) {
		rotulo = elem;

	}

	public Vertice<V> getPred() {
		return pred;
	}

	public Vertice<V> getSuces() {
		return suces;
	}

	public void setPred(Vertice<V> pred) {
		this.pred = pred;
	}

	public void setSuces(Vertice<V> suces) {
		this.suces = suces;
	}

	public void setPosicion(Position<Arco<V, E>> e) {
		posicionEnListaArcos = e;
	}

	public Position<Arco<V, E>> getPosicion() {
		return posicionEnListaArcos;
	}

}
