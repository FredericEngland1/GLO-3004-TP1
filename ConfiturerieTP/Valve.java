
public class Valve {

	int _id;
	boolean _dispo = true;
	
	public Valve (int id) {
		this._id = id;
	}
	
	public boolean Dispo () {
		return _dispo;
	}
	
	public void OuvrirValve () {
		_dispo = false;
	}
	
	public void FermerValve () {
		_dispo = true;
	}
}