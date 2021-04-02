import org.jetbrains.annotations.Contract;

public class Confiturerie {

	private Confiturerie() {
		// classe statique, on ne permet pas l'acces au constructeur
	}

	private static int _nbrBocal;
	private static int _nbrValve;
	private static int _nbrEtiquette;

	private static int _tempsSommeil;

	private static boolean _pause;
	private static boolean _arret;

	private static InterfaceUtilisateur _ui;

	private static ControleRemplissage _cRemplissage;
	private static ControleEtiquetage _cEtiquetage;

	public static void InitConfiturerie(int nbrBocal, int nbrValve, int nbrEtiquette, int tempsSommeil, InterfaceUtilisateur ui) {
		_nbrBocal = nbrBocal;
		_nbrValve = nbrValve;
		_nbrEtiquette = nbrEtiquette;

		_tempsSommeil = tempsSommeil;

		_pause = false;
		_arret = false;

		_ui = ui;

		//TODO add the controllers
		_cRemplissage = ;
		_cEtiquetage = ;

		//TODO start running bocals
	}

	public static void ArretConfiturerie() {
		_arret = true;
	}

	public static void MiseAJourTempsSommeil(int tempsSommeil) {
		_tempsSommeil = tempsSommeil;
	}

	public static void Rupture(TypeBocal type) {
		//TODO implement
	}

	public static boolean EstPause() {
		return _pause;
	}

	public static boolean EstArret() {
		return _arret;
	}

	public static void Pause() {
		_pause = true;
	}

	public static void Redemarre() {
		_pause = false;
	}

	public static ControleRemplissage GetCRemplissage() {
		return _cRemplissage;
	}

	public static ControleEtiquetage GetCEtiquetage() {
		return _cEtiquetage;
	}

	public static int GetTempsSommeil() {
		return _tempsSommeil;
	}

	public static void BocalPret(Bocal bocal) {
		//TODO implement
	}

	public static synchronized void AjouterTexte(String ligne) {
		_ui.AjouterTexte(ligne);
	}
}