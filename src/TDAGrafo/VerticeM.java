package TDAGrafo;

import TDALista.Position;

public class VerticeM<V> implements Vertex<V> {

	protected V rotulo;
	protected Position<VerticeM<V>> posicionEnListaVertices;
	protected int indice;
	protected boolean estado;

	public VerticeM() {
		rotulo = null;
		posicionEnListaVertices = null;
		indice = 0;
	}

	public VerticeM(V rotulo, int ind) {
		this.rotulo = rotulo;
		indice = ind;
	}

	public void setPosicion(Position<VerticeM<V>> e) {
		posicionEnListaVertices = e;
	}

	public void setElement(V rotulo) {
		this.rotulo = rotulo;
	}

	public Position<VerticeM<V>> getPosicion() {
		return posicionEnListaVertices;
	}

	public V element() {
		return rotulo;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int i) {
		indice = i;
	}

	public boolean getEstado() {
		return estado;
	}

	@Override
	public void setEstado(boolean est) {
		estado = est;

	}

}