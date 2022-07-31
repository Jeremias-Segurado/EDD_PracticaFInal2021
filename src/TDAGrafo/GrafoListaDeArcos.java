package TDAGrafo;

import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class GrafoListaDeArcos<V, E> implements Graph<V, E> {

	protected PositionList<Vertice<V>> nodos;
	protected PositionList<Arco<V, E>> arcos;

	public GrafoListaDeArcos() {
		nodos = new DoubleLinkedList<Vertice<V>>();
		arcos = new DoubleLinkedList<Arco<V, E>>();
	}

	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista = new DoubleLinkedList<Vertex<V>>();
		for (Vertice<V> vertice : nodos) {
			lista.addLast(vertice);
		}

		return lista;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		for (Arco<V, E> arco : arcos)
			lista.addLast(arco);

		return lista;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		if (v == null) {
			throw new InvalidVertexException("Vertice invalido");
		}
		for (Arco<V, E> arco : arcos) {
			if (arco.getPred() == v || arco.getSuces() == v) {
				lista.addLast(arco);
			}
		}

		return lista;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {

		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");
		Vertex<V> salida = null;
		Arco<V, E> arco = (Arco<V, E>) e;
		if (arco.getSuces() == v)
			salida = arco.getPred();
		else {
			if (arco.getPred() == v)
				salida = arco.getSuces();
			else
				throw new InvalidEdgeException("Ninguno de los extremos coincide con el vertice");
		}
		return salida;
	}

	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		if (e == null) {
			throw new InvalidEdgeException("Arco invalido");
		}
		Vertex<V>[] salida = (Vertex<V>[]) new Vertice[2];
		Arco<V, E> arco = (Arco<V, E>) e;
		salida[0] = arco.getPred();
		salida[1] = arco.getSuces();

		return salida;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		boolean salida = false;
		if (v == null || w == null)
			throw new InvalidVertexException("Vertice Invalido");
		Arco<V, E> a;
		Iterator<Arco<V, E>> itArcos = arcos.iterator();
		while (itArcos.hasNext() && !salida) {
			a = itArcos.next();
			if (a.getPred() == v && a.getSuces() == w)
				salida = true;
			else if (a.getPred() == w && a.getSuces() == v)
				salida = true;
		}

		return salida;
	}

	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		V salida = v.element();
		Vertice<V> vertice = (Vertice<V>) v;
		vertice.setElement(x);
		return salida;
	}

	public Vertex<V> insertVertex(V x) {
		Vertice<V> nodoAInsertar = new Vertice<V>();
		nodoAInsertar.setElement(x);
		nodos.addLast(nodoAInsertar);
		try {
			nodoAInsertar.setPosicion(nodos.last());
		} catch (EmptyListException e) {
			System.out.println(e.getMessage());
		}

		return nodoAInsertar;
	}

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Arco<V, E> arcoAInsertar = new Arco<V, E>();

		if (v != null && w != null) {
			Vertice<V> v1 = (Vertice<V>) v;
			Vertice<V> w1 = (Vertice<V>) w;
			arcoAInsertar.setElement(e);
			arcoAInsertar.setPred(v1);
			arcoAInsertar.setSuces(w1);
			arcos.addLast(arcoAInsertar);
			try {
				arcoAInsertar.setPosicion(arcos.last());
			} catch (EmptyListException p) {
				System.out.println(p.getMessage());
			}
		} else {
			throw new InvalidVertexException("Vertice invalido");
		}

		return arcoAInsertar;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		Vertice<V> vertice = (Vertice<V>) v;
		V removed = vertice.element();

		try {
			for (Arco<V, E> arco : arcos) {
				if (arco.getPred() == v || arco.getSuces() == v) {
					arcos.remove(arco.getPosicion());
					arco.setElement(null);
					arco.setPred(null);
					arco.setSuces(null);
				}
			}
			nodos.remove(vertice.getPosicion());
		} catch (InvalidPositionException e) {
			System.out.println(e.getMessage());
		}

		return removed;

	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");
		E removed;
		Arco<V, E> arco = (Arco<V, E>) e;
		removed = arco.element();
		try {
			arcos.remove(arco.getPosicion());
			arco.setElement(null);
			arco.setPred(null);
			arco.setSuces(null);
		} catch (InvalidPositionException e1) {
			System.out.println(e1.getMessage());
		}
		return removed;
	}

}
