
public class Valve {

	public Valve(int id) {
		_id = id;
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

	public synchronized void RequeteValve() {
		_dispo = false;
	}

	public synchronized void OuvreValve() {}

	public synchronized void FermeValve() {
		_dispo = true;
	}
}
