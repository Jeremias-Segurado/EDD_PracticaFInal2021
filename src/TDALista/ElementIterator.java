package TDALista;

import java.lang.*;
import java.util.*;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;

public class ElementIterator<E> implements Iterator<E> {
	
	protected PositionList<E> list;
	protected Position<E> cursor;
	
	public ElementIterator(PositionList <E> l) {
		list=l;
		if(list.isEmpty())
			cursor=null;
		else
			try {
				cursor=list.first();
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
	}

	public boolean hasNext() {
		return cursor!=null;
	}

	@Override
	public E next() throws NoSuchElementException {
		if(cursor==null)
			throw new NoSuchElementException("Error: No hay siguiente");
		E toReturn= cursor.element();
		try {
			cursor= (cursor==list.last())? null: list.next(cursor);
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}

}
