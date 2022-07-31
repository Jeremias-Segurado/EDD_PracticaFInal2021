package TDAGrafo;

import TDALista.Position;

public class Vertice<V> implements Vertex<V> {

	protected V rotulo;
	protected Position<Vertice<V>> posicionEnListaVertices;
	protected boolean estado;

	public Vertice() {
		rotulo = null;
		posicionEnListaVertices = null;
	}

	public Vertice(V rotulo, Position<Vertice<V>> posicion) {
		posicionEnListaVertices = posicion;
		this.rotulo = rotulo;
	}

	public void setPosicion(Position<Vertice<V>> e) {
		posicionEnListaVertices = e;
	}

	public void setElement(V rotulo) {
		this.rotulo = rotulo;
	}

	public Position<Vertice<V>> getPosicion() {
		return posicionEnListaVertices;
	}

	public V element() {
		return rotulo;
	}

	@Override
	public boolean getEstado() {
		return estado;
	}

	@Override
	public void setEstado(boolean est) {
		estado = est;

	}

}
