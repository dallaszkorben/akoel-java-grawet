package hu.akoel.grawit;

import java.util.ArrayList;

public class Blabla<E> {
	private E value;

	public Blabla( E value ){
		this.value = value;
	}
	
	public E get(){
		return value;
	}
	
	public static void main( String[] args ){
		ArrayList<Blabla<?>> l = new ArrayList<>();
		
		l.add( new Blabla<Integer>(5));
		l.add( new Blabla<String>("hello"));
		
		System.err.println(l.get(0).value );
		System.err.println(l.get(1).value);
	}
}

