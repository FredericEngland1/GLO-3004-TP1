public class Confiturerie {
	
	public static InterfaceUtilisateur UI;
	static BocalPool _bocalPool;
	
	boolean _pause = false;
	
	int _nbBocaux = 0;
	int _nbValves = 0;
	int _nbEtiqueteuse = 0;
	int _tempsSommeil = 0;
	
	ControleRemplissage _cRemplissage;
	ControleEtiquetage _cEtiquetage;
	
	public Confiturerie (int nbBocaux, int nbValves, int nbEtiqueteuse, int tempsSommeil, InterfaceUtilisateur interfaceUtilisateur) {
		
		this._nbBocaux = nbBocaux;
		this._nbValves = nbValves;
		this._nbEtiqueteuse = nbEtiqueteuse;
		this._tempsSommeil = tempsSommeil;
		Confiturerie.UI = interfaceUtilisateur;
		
		this._cRemplissage = new ControleRemplissage(_nbValves);
		this._cEtiquetage = new ControleEtiquetage(_nbEtiqueteuse);
		Confiturerie._bocalPool = new BocalPool(3);
		
		Thread tr = new Thread(() -> _cRemplissage.Run()); // Changer le 100 par tempsSommeil
		tr.start();
	    
		Thread te = new Thread(() -> _cEtiquetage.Run()); // Changer le 100 par tempsSommeil
		te.start();
		
	}
	
	public static synchronized void BocalPret (Bocal bocal, TypeBocal type) {
		
		_bocalPool.Release(bocal, type);
		_bocalPool.Aquire(type);
		
	}
}