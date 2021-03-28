
public class Bocal {
	
	int _id;
	
	EtatBocal _etat = EtatBocal.VIDE;
	TypeBocal _typeBocal;
	
	Valve _valve;
	Etiqueteuse _etiqueteuse;
	
	public Bocal (int id, TypeBocal type) {
		this._id = id;
		this._typeBocal = type;
	}
	
	// Si la valve n'as pas ete set, return false
	
	public boolean RunRemplissage(int tempsSommeil) {
		
		if (_valve == null || _etat != EtatBocal.VIDE) {
			return false;
		}
		
		Confiturerie.UI.AjouterTexte("Le bocal #" + this._id + " a commencer le remplissage avec la valve #" + _valve._id);
		
		try {
			Thread.sleep(tempsSommeil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Confiturerie.UI.AjouterTexte("Le bocal #" + this._id + " a terminer le remplissage avec la valve #" + _valve._id);
		
		_etat = EtatBocal.REMPLIT;
		
		return true;
	}
	
	// Si l'etiqueteuse n'as pas ete set, return false
	
	public boolean RunEtiquetage(int tempsSommeil) {
		
		if (_etiqueteuse == null || _etat != EtatBocal.REMPLIT) {
			return false;
		}
		
		Confiturerie.UI.AjouterTexte("Le bocal #" + this._id + " a commencer l'etiquetage avec l'etiqueteuse #" + _etiqueteuse._id);
		
		try {
			Thread.sleep(tempsSommeil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Confiturerie.UI.AjouterTexte("Le bocal #" + this._id + " a terminer l'etiquetage avec l'etiqueteuse #" + _etiqueteuse._id);
		
		_etat = EtatBocal.ETIQUETTE;
		
		ControleEtiquetage._etiqueteuses.elementAt(_etiqueteuse._id - 1).TerminerEtiquetage();
		
		Confiturerie.UI.AjouterTexte("Le bocal " + this._id + " est pret !");
		
		Confiturerie.BocalPret(this, this._typeBocal);
		
		return true;
	}
	
	public void AttribueValve(Valve valve) {
		
		this._valve = valve;
	}
	
	public void AttribueEtiquette(Etiqueteuse etiqueteuse) {
		
		this._etiqueteuse = etiqueteuse;
	}
}
