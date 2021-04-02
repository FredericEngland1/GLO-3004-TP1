
public class Etiqueteuse {

	public Etiqueteuse() {
		_dispo = true;
	}

	private boolean _dispo;

	public synchronized boolean Dispo() {
		return _dispo;
	}

	public synchronized void RequeteEtiquetage() {
		_dispo = false;
	}
	
	public synchronized void CommenceEtiquetage() {}
	
	public synchronized void TermineEtiquetage() {
		_dispo = true;
	}
}
