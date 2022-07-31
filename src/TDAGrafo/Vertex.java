package TDAGrafo;

import TDALista.Position;

public interface Vertex<E> extends Position<E> {

	public boolean getEstado();

	public void setEstado(boolean est);

}
