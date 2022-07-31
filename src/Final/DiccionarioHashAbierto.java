package Final;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyTreeException;
import Excepciones.InvalidEntryException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;

import TDAArbolBinario.ArbolBinarioEnlazado;
import TDAArbolBinario.BTNodo;
import TDAArbolBinario.BinaryTree;
import TDALista.*;


public class DiccionarioHashAbierto<K, V> implements Dictionary<K, V> {

	protected PositionList<Entry<K, V>> bucket[];
	protected int cant;
	protected int tamaño = 13;
	protected final double fc = 0.7;

	//Punto b): Constructor() e insertar()
	public DiccionarioHashAbierto() {
		bucket = (PositionList<Entry<K, V>>[]) new DoubleLinkedList[tamaño];
		cant = 0;
		for (int i = 0; i < bucket.length; i++) {
			bucket[i] = (PositionList) new DoubleLinkedList<Entrada<K, V>>();
		}
	}

	
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key==null) throw new InvalidKeyException("ERROR clave nula.");
		if (cant / tamaño >= fc)
			rehash();
		int clave = H(key);
		PositionList<Entry<K, V>> l = bucket[clave];
		Entry<K, V> nueva = new Entrada<K, V>(key, value);
		bucket[clave].addLast(nueva);
		cant++;
		return nueva;
	}
	/*
	 * T(rehash) tiene un recorrido completo del bicket con un recorrido de las listas de cada 
	 * luego tiene otro recorrido para crear las nuevas listas del bucket 
	 * luego otro recorrido de las entradas viejas
	 * T(..) = m*m + n + m = O(m*m + n + m)
	 * con n=tamaño del bucket ACTUAL, m tamaño del bucket viejo
	 * -->Tendria que agregar el tiempo de T(proximo_primo) y T(esPrimo)<--
	 */
	private void rehash() {
		PositionList<Entry<K, V>> entradas = new DoubleLinkedList<Entry<K, V>>();
		for (int i = 0; i < tamaño; i++) {
			for (Entry<K, V> en : bucket[i]) {
				entradas.addLast(en);
			}
		}
		tamaño = proximo_primo(tamaño * 2);
		bucket = (PositionList<Entry<K, V>>[]) new DoubleLinkedList[tamaño];
		cant = 0;
		for (int i = 0; i < tamaño; i++)
			bucket[i] = (PositionList) new DoubleLinkedList<Entrada<K, V>>();
		for (Entry<K, V> e : entradas)
			try {
				insert(e.getKey(), e.getValue());
			} catch (InvalidKeyException ex) {ex.getMessage();}						
	}


	private int proximo_primo(int n) {
		boolean es = false;
		n++;
		while (!es) {
			if (esPrimo(n))
				es = true;
			else
				n++;
		}
		return n;
	}

	private boolean esPrimo(int n) {
		boolean es = false;
		int divisor = 2;
		while (divisor < n && !es) {
			if (n % divisor == 0)
				es = true;
			else
				divisor++;
		}

		return es;
	}
	
	private int H(K key) {
		return key.hashCode() % tamaño;
	}
 
	//Punto c):
	
	
	public Dictionary<K, Integer> ejercicio2(ArbolBinarioEnlazado<K> B){
		Dictionary<K,Integer> toReturn = new DiccionarioHashAbierto(); //c2
		try {
			//Inicia la altura con -1 ya que al llegar al fondo se suma 1 quedando en altura=0.
			postorden(B, B.root(), -1,(DiccionarioHashAbierto<K, Integer>) toReturn);
		} catch (EmptyTreeException e) {e.printStackTrace();}					
		return toReturn; //c3
	}
	
	
	private void postorden(ArbolBinarioEnlazado<K> B, Position<K>  nodo, int altura, DiccionarioHashAbierto<K, Integer> diccionario) {
	
		try
		{
			if (B.hasLeft(nodo))
				postorden(B, B.left(nodo), altura, diccionario);
			if (B.hasRight(nodo))
				postorden(B, B.right(nodo), altura, diccionario);
			altura++;												//c1
			diccionario.insert(nodo.element(), altura); 			
		}catch (InvalidPositionException | BoundaryViolationException | InvalidKeyException e) { e.printStackTrace(); }			
	}
	
	/*
	 * TIEMPO DE EJECUCION 
	 * T(postorden) = t*(c1 + T(insert)), con t=cantidad nodos en el arbol
	 * En el peor de los casos que se tenga re restructurar el bucket: 
	 * T(insert) = T(rehash) + T(c1,c2,...) 
	 * 		T(rehash) = O(m*m + n + m)
	 * 		T(c1, c2,...) = O(1)
	 * ==> T(insert) = T(rehash) = O(m*m + n + m)
	 * 	
	 * Resulta el tiempo en el peor de los casos:
	 * T(postorden) = O(t*(m*m + n + m))
	 * -->NO es reaista porque no se agrandaria el bucket en cada iteracion<---
	 * 
	 * Luego
	 * 	T(ejercicio2) = c2 + T(postorden) + c3 = O(t*(m*m + n + m))
	 */
		
}