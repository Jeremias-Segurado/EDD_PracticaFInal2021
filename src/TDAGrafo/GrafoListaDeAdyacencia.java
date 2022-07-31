package TDAGrafo;

import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class GrafoListaDeAdyacencia<V, E> implements Graph<V, E> {

	protected PositionList<VerticeAd<V, E>> nodos;
	protected PositionList<ArcoAd<V, E>> arcos;

	public GrafoListaDeAdyacencia() {
		nodos = new DoubleLinkedList<VerticeAd<V, E>>();
		arcos = new DoubleLinkedList<ArcoAd<V, E>>();
	}

	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista = new DoubleLinkedList<Vertex<V>>();
		for (VerticeAd<V, E> pos : nodos) {
			lista.addLast(pos);
		}
		return lista;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		for (ArcoAd<V, E> pos : arcos) {
			lista.addLast(pos);

		}

		return lista;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");

		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		VerticeAd<V, E> vertice = (VerticeAd<V, E>) v;
		for (ArcoAd<V, E> arco : vertice.getAdyacentes()) {
			lista.addLast(arco);
		}

		return lista;
	}
	public Iterable<Edge<E>> emergentEdges(Vertex<V> v) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");

		PositionList<Edge<E>> lista = new DoubleLinkedList<Edge<E>>();
		VerticeAd<V, E> vertice = (VerticeAd<V, E>) v;
		for (ArcoAd<V, E> arco : vertice.getAdyacentes()) {
			lista.addLast(arco);
		}

		return lista;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");

		Vertex<V> salida = null;
		ArcoAd<V, E> arco = (ArcoAd<V, E>) e;
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
		VerticeAd<V, E>[] salida = (VerticeAd<V, E>[]) new VerticeAd[2];
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");
		ArcoAd<V, E> arco = (ArcoAd<V, E>) e;
		salida[0] = arco.getPred();
		salida[1] = arco.getSuces();

		return salida;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		if (v == null || w == null)
			throw new InvalidVertexException("Vertice invalido");
		boolean salida = false;
		ArcoAd<V, E> a;
		Iterator<ArcoAd<V, E>> itArcos = arcos.iterator();
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
		V salida = null;
		VerticeAd<V, E> vertice = (VerticeAd<V, E>) v;
		salida = vertice.element();
		vertice.setElement(x);

		return salida;
	}

	public Vertex<V> insertVertex(V x) {
		VerticeAd<V, E> nuevo = new VerticeAd<V, E>(x);
		nodos.addLast(nuevo);
		try {
			nuevo.setPosicion(nodos.last());
		} catch (EmptyListException e) {
			System.out.println(e.getMessage());
		}

		return nuevo;
	}

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		if (v == null || w == null)
			throw new InvalidVertexException("Vertice invalido");
		VerticeAd<V, E> vv = (VerticeAd<V, E>) v;
		VerticeAd<V, E> ww = (VerticeAd<V, E>) w;
		ArcoAd<V, E> nuevo = new ArcoAd<V, E>(e, vv, ww);

		try {
			vv.getAdyacentes().addLast(nuevo);
			nuevo.setPosPred(vv.getAdyacentes().last());
			ww.getAdyacentes().addLast(nuevo);
			nuevo.setPosSuces(ww.getAdyacentes().last());
			arcos.addLast(nuevo);
			nuevo.setPosicionEnAdyacentes(arcos.last());

		} catch (EmptyListException e1) {
			System.out.println(e1.getMessage());
		}

		return nuevo;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		if (v == null)
			throw new InvalidVertexException("Vertice invalido");
		V removed = v.element();
		VerticeAd<V, E> vv = (VerticeAd<V, E>) v;
		try {
			for (ArcoAd<V, E> arco : vv.getAdyacentes()) {
				arcos.remove(arco.getPosicion());
				arco.getPred().getAdyacentes().remove(arco.getPosPred());
				arco.getSuces().getAdyacentes().remove(arco.getPosSuces());
				arco.setElement(null);
				arco.setPred(null);
				arco.setSuces(null);
			}

			nodos.remove(vv.getPosicion());
			vv.setElement(null);
			vv.setPosicion(null);
		} catch (InvalidPositionException e) {
			System.out.println(e.getMessage());
		}

		return removed;
	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		if (e == null)
			throw new InvalidEdgeException("Arco invalido");
		E removed = e.element();
		ArcoAd<V, E> arco = (ArcoAd<V, E>) e;

		try {
			arcos.remove(arco.getPosicion());
			VerticeAd<V, E> p = arco.getPred();
			VerticeAd<V, E> s = arco.getSuces();
			p.getAdyacentes().remove(arco.getPosPred());
			s.getAdyacentes().remove(arco.getPosSuces());

			arco.setElement(null);
			arco.setPred(null);
			arco.setSuces(null);

		} catch (InvalidPositionException e1) {
			System.out.println(e1.getMessage());
		}
		return removed;
	}

}
