
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


	public void RunRemplissage() {

		if (_etat != EtatBocal.VIDE) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " n'est pas VIDE !");
			return;
		}

		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a commence le remplissage");

		try {
			Thread.sleep(Confiturerie.GetTempsSommeil());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		_etat = EtatBocal.REMPLIT;

		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a termine le remplissage");

		Confiturerie.GetCEtiquetage().AjouterBocal(this);
	}
	
	public void RunEtiquetage() {


		if (_etat != EtatBocal.REMPLIT) {
			Confiturerie.AjouterTexte("ERREUR : Le bocal " + _type.toString() + "." + this._id + " n'est pas REMPLIT !");
			return;
		}


		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a commence l'etiquetage");

		try {
			Thread.sleep(Confiturerie.GetTempsSommeil());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " a termine l'etiquetage");

		_etat = EtatBocal.ETIQUETTE;


		Confiturerie.AjouterTexte("Le bocal " + _type.toString() + "." + this._id + " est pret !");

		Confiturerie.BocalPret(this);
	}
}
