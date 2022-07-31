package TDAGrafo;

import TDACola.*;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;
import TDAMapeo.*;
import Excepciones.EmptyListException;
import Excepciones.EmptyQueueException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;

public class DFSandBFS {

	public static <V, E> void DFSShell(Graph<V, E> grafo) {
		for (Vertex<V> ver : grafo.vertices())
			ver.setEstado(false);
		for (Vertex<V> v1 : grafo.vertices())
			if (v1.getEstado() == false)
				DFS(grafo, v1);
	}

	private static <V, E> void DFS(Graph<V, E> grafo, Vertex<V> vert) {
		try {
			System.out.println(vert.element());
			vert.setEstado(true);
			Iterable<Edge<E>> adyacentes = grafo.incidentEdges(vert);
			for (Edge<E> e : adyacentes) {
				Vertex<V> w = grafo.opposite(vert, e);
				if (w.getEstado() == false)
					DFS(grafo, w);
			}
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}

	}

	public static <V, E> void BFSShell(Graph<V, E> grafo) {
		for (Vertex<V> vert : grafo.vertices())
			vert.setEstado(false);
		for (Vertex<V> vert : grafo.vertices())
			if (vert.getEstado() == false)
				BFS(grafo, vert);
	}

	private static <V, E> void BFS(Graph<V, E> g, Vertex<V> v) {
		try {
			Queue<Vertex<V>> cola = new ArrayQueue<Vertex<V>>();
			cola.enqueue(v);
			while (!cola.isEmpty()) {
				Vertex<V> w = cola.dequeue();
				System.out.println(w.element());
				w.setEstado(true);
				for (Edge<E> e : g.incidentEdges(w)) {
					Vertex<V> x = g.opposite(w, e);
					if (x.getEstado() == false) {
						cola.enqueue(x);
						x.setEstado(true);
					}
				}
			}
		} catch (InvalidEdgeException | InvalidVertexException | EmptyQueueException e) {

		}

	}

	public static <V, E> boolean DFSstShell(Graph<V, E> grafo, Vertex<V> origen, Vertex<V> destino,
			PositionList<Vertex<V>> camino) {

		for (Vertex<V> vert : grafo.vertices())
			vert.setEstado(false);
		return DFSst(grafo, origen, destino, camino);

	}

	public static <V, E> boolean DFSst(Graph<V, E> grafo, Vertex<V> origen, Vertex<V> destino,
			PositionList<Vertex<V>> camino) {
		try {
			origen.setEstado(true);
			camino.addLast(origen);
			if (origen == destino)
				return true;
			else {
				boolean encontre = false;
				for (Edge<E> e : grafo.incidentEdges(origen)) {
					Vertex<V> w = grafo.opposite(origen, e);
					if (w.getEstado() == false) {
						encontre = DFSst(grafo, w, destino, camino);
						if (encontre)
							return true;
					}
				}
			}
			camino.remove(camino.last());
			return false;
		} catch (InvalidEdgeException | InvalidVertexException | InvalidPositionException | EmptyListException e) {

		}
		return false;
	}

	public static void main(String args[]) {

		Graph<Integer, String> grafo = new GrafoListaDeAdyacencia<Integer, String>();
		// inserto vertices al grafo
		Vertex<Integer> v1 = grafo.insertVertex(1);
		Vertex<Integer> v2 = grafo.insertVertex(2);
		Vertex<Integer> v3 = grafo.insertVertex(3);
		Vertex<Integer> v4 = grafo.insertVertex(4);
		Vertex<Integer> v5 = grafo.insertVertex(5);
		Vertex<Integer> v6 = grafo.insertVertex(6);
		Vertex<Integer> v7 = grafo.insertVertex(7);

		// uno los vertices los arcos
		try {
			grafo.insertEdge(v1, v2, "a");
			grafo.insertEdge(v2, v3, "b");
			grafo.insertEdge(v3, v4, "g");
			grafo.insertEdge(v1, v4, "f");
			grafo.insertEdge(v3, v6, "d");
			grafo.insertEdge(v3, v5, "c");
			grafo.insertEdge(v5, v6, "e");
			grafo.insertEdge(v1, v7, "j");
			grafo.insertEdge(v7, v2, "h");

		} catch (InvalidVertexException e) {
		}
		PositionList<Vertex<Integer>> ruta = new DoubleLinkedList<Vertex<Integer>>();
		// System.out.println(DFSstShell(grafo, v1, v5, ruta));
		/*
		 * for (Vertex<Integer> v : ruta) { VerticeAd<Integer,String> verticexd=
		 * (VerticeAd<Integer,String>) v; System.out.println(verticexd.element()); }
		 */

		// DFSShell(grafo);
		BFSShell(grafo);

	}

}
