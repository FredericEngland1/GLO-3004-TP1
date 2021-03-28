
public class Confiturerie {
	
	public static InterfaceUtilisateur UI;
	static BocalPool _bocalPool = new BocalPool();
	
	boolean _pause = false;
	
	int _nbBocaux = 0;
	int _nbValves = 0;
	int _nbEtiqueteuse = 0;
	int _tempsSommeil = 0;
	
	ControleRemplissage _cRemplissage = new ControleRemplissage();
	ControleEtiquetage _cEtiquetage = new ControleEtiquetage();
	
	public Confiturerie (int nbBocaux, int nbValves, int nbEtiqueteuse, int tempsSommeil, InterfaceUtilisateur interfaceUtilisateur) {
		
		this._nbBocaux = nbBocaux;
		this._nbValves = nbValves;
		this._nbEtiqueteuse = nbEtiqueteuse;
		this._tempsSommeil = tempsSommeil;
		Confiturerie.UI = interfaceUtilisateur;
		
	}
	
	public static synchronized void BocalPret (Bocal bocal, TypeBocal type) {
		
		_bocalPool.Release(bocal, type);
		_bocalPool.Aquire(type);
		
	}
}