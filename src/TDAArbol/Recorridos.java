package TDAArbol;

import java.util.Iterator;

import Excepciones.EmptyQueueException;
import Excepciones.EmptyTreeException;
import Excepciones.InvalidPositionException;
import TDACola.ArrayQueue;
import TDACola.Queue;
import TDALista.*;

public class Recorridos {
	public static void PreOrdenShell(Tree<Character> t) {
		try {
			PreOrden(t, t.root());
		} catch (EmptyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void PreOrden(Tree<Character> t, Position<Character> v) {
		System.out.println(v.element());
		try {
			for (Position<Character> hijo : t.children(v)) {
				PreOrden(t, hijo);
			}
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void PosOrdenShell(Tree<Integer> t) {
		try {
			PosOrden(t, t.root());
			System.out.println(t.root().element());
		} catch (EmptyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void PosOrden(Tree<Integer> t, Position<Integer> v) {
		try {
			for (Position<Integer> hijo : t.children(v)) {
				PosOrden(t, hijo);
				System.out.println(hijo.element());
			}

		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}

	public static void MostrarPorNiveles(Tree<Integer> tree) {

		Queue<Position<Integer>> cola = new ArrayQueue<Position<Integer>>();
		try {
			cola.enqueue(tree.root());
			cola.enqueue(null);
			while (!cola.isEmpty()) {
				Position<Integer> v = cola.dequeue();
				if (v != null) {
					System.out.println(v.element());
					for (Position<Integer> w : tree.children(v))
						cola.enqueue(w);
				} else {
					System.out.println("null");
					if (!cola.isEmpty())
						cola.enqueue(null);
				}
			}
		} catch (EmptyQueueException | EmptyTreeException | InvalidPositionException e) {

		}

	}

	public static boolean AgregarNodo(Tree<Character> tree, char r, char p) {
		boolean esta = false;
		try {
			Iterable<Position<Character>> lista = tree.positions();
			Iterator<Position<Character>> it = lista.iterator();
			Position<Character> pos = null;

			while (it.hasNext() && !esta) {
				Position<Character> aux = it.next();
				if (aux.element().equals(p)) {
					esta = true;
					pos = aux;
				}
			}
			if (esta)
				tree.addLastChild(pos, r);
		} catch (InvalidPositionException e) {
			e.getMessage();
		}
		return esta;
	}

	/*
	 * public static void main(String args[]) { Tree<Character>arbol=new
	 * Arbol<Character>();
	 * 
	 * try { arbol.createRoot('a'); Position<Character>r=arbol.root();
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * arbol.addLastChild(r,'b'); arbol.addLastChild(r,'c');
	 * arbol.addLastChild(r,'d');
	 * 
	 * AgregarNodo(arbol,'h','b'); AgregarNodo(arbol,'f','b');
	 * AgregarNodo(arbol,'k','h'); AgregarNodo(arbol,'g','h');
	 * AgregarNodo(arbol,'e','c'); AgregarNodo(arbol,'l','d');
	 * AgregarNodo(arbol,'z','d'); AgregarNodo(arbol,'m','l');
	 * AgregarNodo(arbol,'p','m');
	 * 
	 * arbol.ejercicio7d('h'); PreOrdenShell(arbol);
	 * 
	 * } catch (InvalidOperationException | EmptyTreeException |
	 * InvalidPositionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * }
	 */
}