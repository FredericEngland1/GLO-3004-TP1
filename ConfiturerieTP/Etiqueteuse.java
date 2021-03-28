
public class Etiqueteuse {

	int _id;
	boolean _dispo = true;
	
	public Etiqueteuse (int id) {
		this._id = id;
	}
	
	public boolean Dispo () {
		return _dispo;
	}
	
	public void CommencerEtiquetage () {
		_dispo = false;
	}
	
	public void TerminerEtiquetage () {
		_dispo = true;
	}
}
