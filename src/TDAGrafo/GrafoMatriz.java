package TDAGrafo;

import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class GrafoMatriz<V, E> implements Graph<V, E> {

	protected PositionList<VerticeM<V>> vertices;
	protected PositionList<ArcoM<V, E>> arcos;
	protected Edge<E>[][] matriz;
	protected int cantVertices;

	public GrafoMatriz(int n) {
		vertices = new DoubleLinkedList<VerticeM<V>>();
		arcos = new DoubleLinkedList<ArcoM<V, E>>();
		matriz = (Edge<E>[][]) new ArcoM[n][n];
		cantVertices = 0;
	}

	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista = new DoubleLinkedList<Vertex<V>>();
		for (Vertex<V> v : vertices) {
			lista.addLast(v);
		}

		return lista;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		for (Edge<E> e : arcos) {
			lista.addLast(e);
		}

		return lista;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {

		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeM<V> vv = (VerticeM<V>) v;
		int fila = vv.getIndice();
		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		for (int col = 0; col < cantVertices; col++)
			if (matriz[fila][col] != null)
				lista.addLast(matriz[fila][col]);

		return lista;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {

		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeM<V> vv = (VerticeM<V>) v;
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");
		ArcoM<V, E> ee = (ArcoM<V, E>) e;
		Vertex<V> salida = null;
		if (ee.getPred() == v)
			salida = ee.getSuces();
		else {
			if (ee.getSuces() == v)
				salida = ee.getPred();
			else
				throw new InvalidEdgeException("Ninguno de los extremos coincide con el vertice");
		}
		return salida;
	}

	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");
		Vertex<V>[] salida = (VerticeM<V>[]) new VerticeM[2];
		ArcoM<V, E> ee = (ArcoM<V, E>) e;
		salida[0] = ee.getPred();
		salida[1] = ee.getSuces();

		return salida;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {

		if (v == null || w == null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeM<V> vv = (VerticeM<V>) v;
		VerticeM<V> ww = (VerticeM<V>) w;
		int i = vv.getIndice();
		int j = ww.getIndice();

		return matriz[i][j] != null;
	}

	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");

		VerticeM<V> vv = (VerticeM<V>) v;
		V removed = v.element();
		vv.setElement(x);

		return removed;
	}

	public Vertex<V> insertVertex(V x) {
		VerticeM<V> vv = new VerticeM<V>(x, cantVertices++);
		vertices.addLast(vv);
		try {
			vv.setPosicion(vertices.last());

		} catch (EmptyListException e) {
			System.out.println(e.getMessage());
		}

		return vv;
	}

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {

		if (v == null || w == null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeM<V> vv = (VerticeM<V>) v;
		VerticeM<V> ww = (VerticeM<V>) w;
		int fila = vv.getIndice();
		int col = ww.getIndice();
		ArcoM<V, E> arco = new ArcoM<V, E>(e, vv, ww);
		matriz[fila][col] = matriz[col][fila] = arco;
		arcos.addLast(arco);
		try {
			arco.setPosicion(arcos.last());
		} catch (EmptyListException exc) {
			System.out.println(exc.getMessage());
		}

		return arco;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeM<V> vv = (VerticeM<V>) v;
		V removed = v.element();
		int fila = vv.getIndice();
		try {
			for (int col = 0; col < cantVertices; col++) {
				if (matriz[fila][col] != null) {
					ArcoM<V, E> arc = (ArcoM<V, E>) matriz[fila][col];
					arcos.remove(arc.getPosicion());
					matriz[fila][col] = matriz[col][fila] = null;
				}
			}

			vertices.remove(vv.getPosicion());
			cantVertices--;

		} catch (InvalidPositionException e) {
			System.out.println(e.getMessage());
		}

		return removed;
	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");

		ArcoM<V, E> ee = (ArcoM<V, E>) e;
		E removed = e.element();
		int fila = ee.getPred().getIndice();
		int col = ee.getSuces().getIndice();
		matriz[fila][col] = matriz[col][fila] = null;
		try {
			arcos.remove(ee.getPosicion());
			ee.setElement(null);
			ee.setPosicion(null);
			ee.setPred(null);
			ee.setSuces(null);
		} catch (InvalidPositionException exc) {
			System.out.println(exc.getMessage());
		}

		return removed;
	}

}
