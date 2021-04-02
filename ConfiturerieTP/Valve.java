
public class Valve {

	public Valve() {
		_dispo = true;
	}

	private boolean _dispo;

	public synchronized boolean Dispo() {
		return _dispo;
	}

	public synchronized void RequeteValve() {
		_dispo = false;
	}

	public synchronized void OuvreValve() {}

	public synchronized void FermeValve() {
		_dispo = true;
	}
}
