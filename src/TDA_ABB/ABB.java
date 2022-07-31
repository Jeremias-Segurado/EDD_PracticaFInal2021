package TDA_ABB;

import java.util.Comparator;

public class ABB<E extends Comparable<E>> {
	protected NodoABB<E> raiz;
	protected int size;
	protected Comparator<E> comp;
	
	public ABB(Comparator<E> comp) {
		raiz = new NodoABB<E>(null, null);
		size = 0;
		this.comp = comp;
	}
	
	public boolean pertenece(E k) {
		return buscar(k).getRotulo() != null;
	}
	
	private NodoABB<E> buscar(E k){
		return buscarAux( k, raiz );
	}
	
	private NodoABB<E> buscarAux(E k, NodoABB<E> p) {
		if (p.getRotulo() == null)
			return p;
		else 
		{
			int c = comp.compare(k, p.getRotulo());
			if (c == 0)			
				return p;
			else 			
				if (c < 0)				
					return buscarAux(k, p.getIzq());
				else
					return buscarAux(k, p.getDer());
		}
	}
	
	public void insertar(E k) {
		NodoABB<E> aux = buscar(k);		
		if (aux.getRotulo() == null)
		{
			aux.setRotulo(k);
			aux.setIzq(new NodoABB<E>(null, aux));
			aux.setDer(new NodoABB<E>(null, aux));
			size++;
		}
	}
	
	public void insertar_recursivo(E k) {
		insertar_aux(k, raiz);
	}
	/*
	 * ALGORITMO insertar (calve, nodo)
	 * Si el nodo esta vacio, entonces crear un nodo hoja con rotulo "clave" que reemplata el nodo vacio.
	 * si no,
	 * 		si clave<clave(nodo) entonces
	 * 			insertar(clave, hijo izquierdo del nodo)
	 * 		si no, si clave>clave(nodo) entonces
	 * 			insertar(clave, hijo derecho del nodo)
	 * 		si no, si clave = clave(nodo) entonces
	 * 			reemplazar rotulo de nodo 
	 * 			
	 */
	public void insertar_aux(E clave, NodoABB<E> nodo) {
		if (nodo.getRotulo() == null) {
			nodo.setRotulo(clave);
			nodo.setIzq(new NodoABB<E>(null, nodo));
			nodo.setDer(new NodoABB<E>(null, nodo));
			size++;
		}else {
			int c = comp.compare(clave, nodo.getRotulo());
			if (c < 0) {
				insertar_aux(clave, nodo.getIzq());
			}else 
				if (c > 0) {
					insertar_aux(clave, nodo.getDer());
				}else {/*No hace nada en el caso de que las claves sean iguales.*/}
		}		
	}
	
	public NodoABB<E> root(){
		return raiz;
	}
	
	public int alturaTotal() {
		int altura = 0;
		if (raiz!=null)
			altura = altura_aux(raiz, 0);
		return altura;
	}
	
	private int altura_aux( NodoABB<E> v, int altura) {
		int altura_izq=0;
		int altura_der=0;
		if (v.getIzq() != null)
			altura_izq = altura_aux( v, altura++);
		if (v.getDer() != null)
			altura_der = altura_aux( v, altura++);
		return altura + Math.max(altura_izq, altura_der);
	}
}
