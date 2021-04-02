import org.jetbrains.annotations.Contract;

import java.util.Vector;

public class Confiturerie {

	private Confiturerie() {
		// classe statique, on ne permet pas l'acces au constructeur
	}

	private static Vector<Bocal> _bocaux;

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

		//TODO start the controller's threads
		_cRemplissage = new ControleRemplissage(_nbrValve);
		_cEtiquetage = new ControleEtiquetage(_nbrEtiquette);

		//TODO start the bocal's threads
		_bocaux = new Vector<Bocal>();
		for (TypeBocal type : TypeBocal.typesBocaux()) {
			for (int i = 1; i <= _nbrBocal; i++) {
				_bocaux.add(new Bocal(i, type));
			}
		}
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