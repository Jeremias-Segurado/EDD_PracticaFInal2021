package TDAGrafo;

import TDALista.*;

public class VerticeAd<V, E> implements Vertex<V> {

	private V rotulo;
	private PositionList<ArcoAd<V, E>> adyacentes;
	private Position<VerticeAd<V, E>> posicionEnNodos;
	private boolean estado;

	public VerticeAd(V rotulo) {
		this.rotulo = rotulo;
		adyacentes = new DoubleLinkedList<ArcoAd<V, E>>();
	}

	public V element() {
		return rotulo;
	}

	public PositionList<ArcoAd<V, E>> getAdyacentes() {
		return adyacentes;
	}

	public Position<VerticeAd<V, E>> getPosicion() {
		return posicionEnNodos;
	}

	public void setElement(V elem) {
		rotulo = elem;
	}

	public void setPosicion(Position<VerticeAd<V, E>> pos) {
		posicionEnNodos = pos;
	}

	public boolean getEstado() {
		return estado;
	}

	@Override
	public void setEstado(boolean est) {
		estado = est;

	}

}
