
public class Bocal {

	public Bocal (int id, TypeBocal type) {
		this._id = id;
		this._type = type;
		this._etat = EtatBocal.VIDE;
	}

	private int _id;

	private TypeBocal _type;
	private EtatBocal _etat;

	public void Reinitialiser() {
		this._etat = EtatBocal.VIDE;
	}

	public int GetID() {
		return _id;
	}

	public TypeBocal GetType() {
		return _type;
	}

	public EtatBocal GetEtat() {
		return _etat;
	}

	// Valve doit etre a false, donc avoir ete attribuer au bocal
	
	public boolean RunRemplissage(Valve valve) {
		
		if (valve == null || valve.Dispo()) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " ne c'est pas fait attribuer une valve valide !");
			return false;
		}
		else if (_etat != EtatBocal.VIDE) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " n'est pas VIDE !");
			return false;
		}
		
		valve.OuvreValve();
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a commencer le remplissage avec la valve #" + valve.GetID());
		
		try {
			Thread.sleep(Confiturerie.GetTempsSommeil());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		_etat = EtatBocal.REMPLIT;
		
		valve.FermeValve();
		
		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a terminer le remplissage avec la valve #" + valve.GetID());
		
		Confiturerie.GetCEtiquetage().AjouterBocal(this);
		
		return true;
	}
	
	// Etiqueteuse doit etre a false, donc avoir ete attribuer au bocal
	
	public boolean RunEtiquetage(Etiqueteuse etiqueteuse) {
		
		if (etiqueteuse == null || etiqueteuse.Dispo()) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " ne c'est pas fait attribuer d'etiqueteuse !");
			return false;
		}
		else if (_etat != EtatBocal.REMPLIT) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " n'est pas REMPLIT !");
			return false;
		}
		
		etiqueteuse.CommenceEtiquetage();
		
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
