package org.jallaby.beans.swing.editor.util;

import static java.util.Collections.binarySearch;

import java.util.*;

public class OrderedIndexFinder<C extends Comparable<? super C>> {
	private class DataAccessor extends AbstractList<C> implements RandomAccess {
		private DataAccessorStrategy<? extends C> strategy;
		
		DataAccessor(DataAccessorStrategy<? extends C> strategy){
			if(strategy == null){
				throw new IllegalArgumentException("strategy must not be null!");
			}
			
			this.strategy = strategy;
		}
		
		public C get(int index){
			return strategy.getElementAt(index);
		}
		
		public int size(){
			return strategy.getSize();
		}
	}
	
	private DataAccessor dataAccessor;
	
	public OrderedIndexFinder(DataAccessorStrategy<? extends C> strategy){
		if(strategy == null){
			throw new IllegalArgumentException("strategy must not be null!");
		}
		
		this.dataAccessor = new DataAccessor(strategy);
	}
	
	public int getOrderedIndex(C input){
		int position = (position = binarySearch(dataAccessor, input)) <= -1 ?
				-position - 1 : position;
		
		return position;
	}
}