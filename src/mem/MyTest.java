package mem;

import java.util.ArrayList;
import java.util.Iterator;

public class MyTest {
	private MyHashMap<String, Person> mapy = new MyHashMap<>();
	
	public void setMapy(MyHashMap<String, Person> mapy) {
		this.mapy = mapy;
	}

	public MyHashMap<String, Person> getMapy() {
		return mapy;
	}

	
	private Caretaker caretaker = new Caretaker();
	private Originator originator = new Originator();
	
	public void addToMap(Person p ) {
		getMapy().put(p.getName(), p);
		originator.setState(getMapy());
		caretaker.addMemento(originator.save());
	}
	
	public void undo() {
		this.mapy= originator.restore(caretaker.getMemento());
	}
	
	public void redo() {
		this.mapy= originator.restore(caretaker.redoMemento());
	}
	


	

//	public Person next() {
//
//		if (it.hasNext()) {
//			MyMap.Entry pair = (MyMap.Entry) it.next();
//			Person p =  (Person) pair.getValue();
//			
//			
//			return p;
//			 
//		}
//		return null;
//
//	}
	
	

	public static void main(String[] args) {
		Person p1 =new Person("tomi", "oscar");
		Person p2 =new Person("shreder", "evil");
		Person p3 =new Person("master", "splinter");
		Person p4 =new Person("kobi", "briant");
		MyTest cmd = new MyTest();
		cmd.addToMap(p1);
		cmd.addToMap(p2);
		cmd.addToMap(p3);
		System.out.println("after adding 3 persons");
		for (String p : cmd.getMapy().keySet()) {
			System.out.println(p);
			
		}
		cmd.undo();
		System.out.println("after one undo therefor the last person that added should deleted");
		for (String p : cmd.getMapy().keySet()) {
			System.out.println(p);
			
		}
		cmd.undo();
		System.out.println("after 2 undo therefor the two last persons that added should deleted");
		for (String p : cmd.getMapy().keySet()) {
			System.out.println(p);
			
		}
		
		
		System.out.println("after one redo therfor the last person that deleted sould comback");
		cmd.redo();
		for (String p : cmd.getMapy().keySet()) {
			System.out.println(p);
			
		}
		
		
		
		

	}

}

class Memento {
	private MyHashMap<String, Person> state;

	public Memento(MyHashMap<String, Person> state) {
		this.state =  new MyHashMap<>();
		for (String key :state.keySet() )
		{
			this.state.put(key, state.get(key));
		}
	}

	public MyHashMap<String, Person> getState() {
		return state;
	}
}

class Originator {
	private MyHashMap<String, Person> state;

	public void setState(MyHashMap<String, Person> state) {
		
		this.state = state;
	}

	public Memento save() {		
		return new Memento(state);
	}

	public MyHashMap<String, Person> restore(Memento m) {
        state = m.getState();
        return state;
       
    }
}

class Caretaker {
	private ArrayList<Memento> mementos = new ArrayList<>();
	private int index;

	public void addMemento(Memento m) {
		mementos.add(m);
		index = mementos.size()-1;
	}

	public Memento getMemento() {
		if(mementos.isEmpty()||index<=0) {
			return null;
		}
		return mementos.get(--index);
	}
	
	public Memento redoMemento() {
		if(mementos.isEmpty()||index >= mementos.size() - 1) {
			return null;
		}
		return mementos.get(++index);
	}
}
