import java.util.Hashtable;
import java.util.LinkedList;

public class BocalPool {
	Hashtable<TypeBocal, LinkedList<Bocal>> _bocaux = new Hashtable<TypeBocal, LinkedList<Bocal>>();
	Hashtable<TypeBocal, Integer> _ids = new Hashtable<TypeBocal, Integer>();
	
	public BocalPool (int nbBocaux) {
		for (TypeBocal type : TypeBocal.values()) {
			
			LinkedList<Bocal> bocaux = new LinkedList<Bocal>();
			
			for (int i = 0; i < nbBocaux; i++) {
				bocaux.add(new Bocal(i + 1, type));
			}
			
			_bocaux.put(type, bocaux);
			_ids.put(type, nbBocaux + 1);
		}
	}
	
	public synchronized void Aquire (TypeBocal type) {
		_bocaux.get(type).add(new Bocal(_ids.get(type), type));
		_ids.replace(type, _ids.get(type) + 1);
	}
	
	public synchronized void Release (Bocal bocal, TypeBocal type) {
		_bocaux.get(type).remove(bocal);
	}
}