import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class ControleRemplissage {
	
	public ControleRemplissage (int nbrValves, int nbrBocaux) {
		_bocauxDispo = new Hashtable<TypeBocal, Vector<Bocal>>();
		for (TypeBocal type : TypeBocal.values()) {
			_bocauxDispo.put(type, new Vector<Bocal>());
		}

		_ruptures = new Hashtable<TypeBocal, Boolean>();
		for (TypeBocal type : TypeBocal.values()) {
			_ruptures.put(type, false);
		}

		this._nbrValves = nbrValves;
		
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
	private Hashtable<TypeBocal, Boolean> _ruptures; //Etrangement si on met "boolean" ca marche pas car cest un premitive type
	private int _nbrValves;
	private int _nbrBocaux;
	
	private ArrayList<Thread> _threads;
	private Hashtable<TypeBocal, Integer> _bocalCourant;
	
	private TypeBocal _currType;
	
	private int _bocauxEnvoyer;

	/*
	 * <Brief> Methode principale, run en thread, mais l'instance est unique. Elle doit tourner en boucle tant que la confiturerie
	 * 	n'est pas en arret. Si la confiturerie est en pause il faut rester dans la boucle sans executer le code.
	 * 	A chaque iteration il faut choisir le prochain bocal a faire passer en respectant les property du TP1 ainsi qu'une
	 * 	valve libre et assigner la valve au bocal en appelant la methode "RunRemplissage(V)" du bocal comme un
	 *  nouveau thread.
	 *
	 *  Il faut aussi tenir compte de la rupture
	 *
	 * 	C'est cette methode qui demarre le thread du bocal (comme ceci : "new Thread(() -> bocal.RunRemplissage(valve)).start();")
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

			if (_ruptures.get(_currType)) {
				nextType();
				continue;
			}
			
			if (_threads.size() == _nbrValves) {
				_threads.get(0).join();
				_threads.remove(0);
				continue;
			}
			
			boolean _trouverBocal = false;

			Vector<Bocal> bocauxClone = (Vector<Bocal>) _bocauxDispo.get(_currType).clone();
			for (Bocal bocal : bocauxClone) {
				if (bocal.GetID() == _bocalCourant.get(_currType) + 1) {
					
					_threads.add(new Thread(bocal::RunRemplissage));
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

	/*
	* <Brief> Methode qui demarre une rupture pour une type de bocal. La rupture est gerer dans le Run.
	* 	Cette methode s'occupe simplement de le notifier depuis la variable
	*/
	public void Rupture(TypeBocal type) {
		_ruptures.put(type, true);
	}

	/*
	 * <Brief> Methode qui réaprovisionne et enlève une rupture pour une type de bocal. La rupture est gerer dans le Run.
	 * 	Cette methode s'occupe simplement de le notifier depuis la variable
	 */

	public void Approvisionnement(TypeBocal type){
		_ruptures.put(type, false);
	}
}