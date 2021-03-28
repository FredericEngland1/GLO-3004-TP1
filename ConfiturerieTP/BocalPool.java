import java.util.Hashtable;
import java.util.LinkedList;

public class BocalPool {
	Hashtable<TypeBocal, LinkedList<Bocal>> _bocaux = new Hashtable<TypeBocal, LinkedList<Bocal>>();
	Hashtable<TypeBocal, Integer> _ids = new Hashtable<TypeBocal, Integer>();
	
	public BocalPool () {
		for (TypeBocal type : TypeBocal.values()) {
			_bocaux.put(type, new LinkedList<Bocal>());
			_ids.put(type, 1);
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