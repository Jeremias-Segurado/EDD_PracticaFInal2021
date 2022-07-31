package TDAGrafo;

import TDALista.Position;

public class ArcoM<V, E> implements Edge<E> {

	protected E rotulo;
	protected Position<ArcoM<V, E>> posicionEnListaArcos;
	protected VerticeM<V> pred, suces;

	public ArcoM(Position<ArcoM<V, E>> posicion) {
		posicionEnListaArcos = posicion;
	}

	public ArcoM() {

	}

	public ArcoM(E rotulo, VerticeM<V> v1, VerticeM<V> v2) {
		this.rotulo = rotulo;
		pred = v1;
		suces = v2;
	}

	public E element() {
		return rotulo;
	}

	public void setElement(E elem) {
		rotulo = elem;

	}

	public VerticeM<V> getPred() {
		return pred;
	}

	public VerticeM<V> getSuces() {
		return suces;
	}

	public void setPred(VerticeM<V> pred) {
		this.pred = pred;
	}

	public void setSuces(VerticeM<V> suces) {
		this.suces = suces;
	}

	public void setPosicion(Position<ArcoM<V, E>> e) {
		posicionEnListaArcos = e;
	}

	public Position<ArcoM<V, E>> getPosicion() {
		return posicionEnListaArcos;
	}

}
