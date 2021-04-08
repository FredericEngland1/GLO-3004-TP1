import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class ControleEtiquetage {
	
	public ControleEtiquetage (int nbrEtiqueteuses, int nbrBocaux) {
		_bocauxDispo = new Hashtable<TypeBocal, Vector<Bocal>>();
		for (TypeBocal type : TypeBocal.values()) {
			_bocauxDispo.put(type, new Vector<Bocal>());
		}

		this._nbrEtiqueteuses = nbrEtiqueteuses;
		
		_threads = new ArrayList<Thread>();
		_bocalCourant = new Hashtable<TypeBocal, Integer>();
		
		for (TypeBocal type : TypeBocal.values()) {
			_bocalCourant.put(type, 0);
		}
		
		_currType = TypeBocal.A;
		_bocauxEnvoyer = 0;
		_nbrBocaux = nbrBocaux;
	}

	private Hashtable<TypeBocal, Vector<Bocal>> _bocauxDispo;
	private int _nbrEtiqueteuses;
	private int _nbrBocaux;
	
	private ArrayList<Thread> _threads;
	private Hashtable<TypeBocal, Integer> _bocalCourant;
	
	private TypeBocal _currType;
	
	private int _bocauxEnvoyer;

	/*
	* <Brief> Methode principale, run en thread, mais l'instance est unique. Elle doit tourner en boucle tant que la confiturerie
	* 	n'est pas en arret. Si la confiturerie est en pause il faut rester dans la boucle sans executer le code.
	* 	A chaque iteration il faut choisir le prochain bocal a faire passer en respectant les property du TP1 ainsi qu'une
	* 	etiqueteuse libre et assigner l'etiqueteuse au bocal en appelant la methode "RunEtiquetage(E)" du bocal comme un
	*  	nouveau thread.
	*
	* 	C'est cette methode qui demarre le thread du bocal (comme ceci : "new Thread(() -> bocal.RunEtiquetage(Etiqueteuse)).start();")
	*
	* */
	public void Run () throws InterruptedException {
		while (!Confiturerie.EstArret()) {

			try {
				Thread.sleep(Confiturerie.GetTempsSommeil());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (Confiturerie.EstPause()) {
				continue;
			}
			
			if (_threads.size() == _nbrEtiqueteuses) {
				_threads.get(0).join();
				_threads.remove(0);
				continue;
			}
			
			boolean _trouverBocal = false;
			
			for (Bocal bocal : _bocauxDispo.get(_currType)) {
				if (bocal.GetID() == _bocalCourant.get(_currType) + 1) {
					
					_threads.add(new Thread(() -> bocal.RunEtiquetage()));
					_threads.get(_threads.size() - 1).start();
					
					_bocauxDispo.get(_currType).remove(bocal);
					
					_bocalCourant.put(_currType, (_bocalCourant.get(_currType) + 1) % _nbrBocaux);
					_bocauxEnvoyer++;
					
					_trouverBocal = true;
					
					break;
				}
			}
			
			if (!_trouverBocal) {
				nextType();
			}
			
			if (_bocauxEnvoyer == _nbrBocaux) {
				nextType();
			}
		}
	}
	
	private void nextType () {
		_currType = _currType.nextType();
		for (Thread t : _threads) {
			try {
				t.join();
			} catch (InterruptedException e) {}
		}
		_threads.clear();
		_bocauxEnvoyer = 0;
	}
	
	/*
	* <Brief> Methode simple qui ajoute un bocal a sa liste de bocaux pret a etre etiquette
	* 	Il faut verifier le type du bocal pour l'ajouter au bon vector du hashTable
	 */
	public void AjouterBocal(Bocal bocal) {
		_bocauxDispo.get(bocal.GetType()).add(bocal);
	}
}
