import java.util.Vector;

public class Confiturerie {

	private Confiturerie() {
		// classe statique, on ne permet pas l'acces au constructeur
	}

	private static int _tempsSommeil;

	private static boolean _pause;
	private static boolean _arret;

	private static InterfaceUtilisateur _ui;

	private static ControleRemplissage _cRemplissage;
	private static ControleEtiquetage _cEtiquetage;

	public static void InitConfiturerie(int nbrBocal, int nbrValve, int nbrEtiquette, int tempsSommeil, InterfaceUtilisateur ui) {
		_tempsSommeil = tempsSommeil;

		_pause = false;
		_arret = false;

		_ui = ui;

		_cRemplissage = new ControleRemplissage(nbrValve, nbrBocal);
		_cEtiquetage = new ControleEtiquetage(nbrEtiquette, nbrBocal);

		for (TypeBocal type : TypeBocal.values()) {
			for (int i = 1; i <= nbrBocal; i++) {
				_cRemplissage.AjouterBocal(new Bocal(i, type));
			}
		}

		new Thread(() -> {
			try {
				_cRemplissage.Run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				_cEtiquetage.Run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public static void ArretConfiturerie() {
		_arret = true;
	}

	public static void MiseAJourTempsSommeil(int tempsSommeil) {
		_tempsSommeil = tempsSommeil;
	}

	public static void Rupture(TypeBocal type) {
		_cRemplissage.Rupture(type);
	}

	public static void Approvisionnement(TypeBocal type) {
		_cRemplissage.Approvisionnement(type);
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
		bocal.Reinitialiser();
		_cRemplissage.AjouterBocal(bocal);
	}

	public static synchronized void AjouterTexte(String ligne) {
		_ui.AjouterTexte(ligne);
	}
}