
public class InterfaceUtilisateur {
	
	// Valeurs par default
	
	int _nbBocaux = 2;
	int _nbValves = 2;
	int _nbEtiquette = 2;
	int _tempsSomeil = 100;

	Console _console = new Console();
	
	Confiturerie confiturerie;
	
	public void DebutConfiturerie () {
		if (confiturerie == null) confiturerie = new Confiturerie(_nbBocaux, _nbValves, _nbEtiquette, _tempsSomeil, this);
	}
	
	public void ArretConfiturerie () {
			
	}
	
	public void MAJTempsSommeil () {
		
	}
	
	public void Rupture () {
		
	}
	
	public void Pause () {
		
	}
	
	public void Redemarre () {
		
	}
	
	// TODO changer le moment de l'affichage pour faire du sens
	
	public synchronized void AjouterTexte (String texte) {
		_console.AjouterTexte(texte);
		_console.AfficherTexte();
	}
}
