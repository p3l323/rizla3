import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ArrayListMod<E> extends ArrayList<E>{

	public ArrayListMod() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayListMod(Collection<? extends E> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public ArrayListMod(int initialCapacity) {
		super(initialCapacity);
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean add(E e){
		if (!this.contains(e))
			super.add(e);
		return true;
	}
	

	
	
	
	

}
