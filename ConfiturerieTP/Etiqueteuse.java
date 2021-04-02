
public class Etiqueteuse {

	public Etiqueteuse(int id) {
		_id	= id;
		_dispo = true;
	}

	private int _id;
	private boolean _dispo;

	public synchronized boolean Dispo() {
		return _dispo;
	}

	public int GetID() {
		return _id;
	}

	public synchronized void RequeteEtiquetage() {
		_dispo = false;
	}
	
	public synchronized void CommenceEtiquetage() {}
	
	public synchronized void TermineEtiquetage() {
		_dispo = true;
	}
}
