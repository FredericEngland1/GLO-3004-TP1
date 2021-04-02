
public class Bocal {

	public Bocal (int id, TypeBocal type) {
		this._id = id;
		this._type = type;
		this._etat = EtatBocal.VIDE;
	}

	private int _id;

	private TypeBocal _type;
	private EtatBocal _etat;

	public int GetID() {
		return _id;
	}

	public TypeBocal GetType() {
		return _type;
	}

	public EtatBocal GetEtat() {
		return _etat;
	}

	// Si la valve n'as pas ete set, return false
	//TODO reviser l'acces au sommeil, l'appel aux methodes des valves et etiqueteuses
	public boolean RunRemplissage(Valve valve) {
		
		if (valve == null) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " ne c'est pas fait attribuer de valve !");
			return false;
		}
		else if (_etat != EtatBocal.VIDE) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " n'est pas VIDE !");
			return false;
		}
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a commencer le remplissage avec la valve #" + valve.GetID());
		
		try {
			Thread.sleep(Confiturerie.GetTempsSommeil());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a terminer le remplissage avec la valve #" + valve.GetID());
		
		_etat = EtatBocal.REMPLIT;
		
		return true;
	}
	
	// Si l'etiqueteuse n'as pas ete set, return false
	
	public boolean RunEtiquetage(Etiqueteuse etiqueteuse) {
		
		if (etiqueteuse == null || _etat != EtatBocal.REMPLIT) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " ne c'est pas fait attribuer d'etiqueteuse !");
			return false;
		}
		else if (_etat != EtatBocal.REMPLIT) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " n'est pas REMPLIT !");
			return false;
		}
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a commencer l'etiquetage avec l'etiqueteuse #" + etiqueteuse.GetID());
		
		try {
			Thread.sleep(Confiturerie.GetTempsSommeil());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a terminer l'etiquetage avec l'etiqueteuse #" + etiqueteuse.GetID());
		
		_etat = EtatBocal.ETIQUETTE;
		
		etiqueteuse.TermineEtiquetage();
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " est pret !");
		
		Confiturerie.BocalPret(this);
		
		return true;
	}
}
