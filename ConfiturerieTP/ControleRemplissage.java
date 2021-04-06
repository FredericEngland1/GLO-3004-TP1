import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class ControleRemplissage {
	public ControleRemplissage (int nbrValves) {
		_bocauxDispo = new Hashtable<TypeBocal, Vector<Bocal>>();
		//TODO initialiser les vector de bocaux pour chaque type de bocal (J'ai creer une methode utile pour ca dans typeBocal)

		_valves = new Vector<Valve>();
		for (int i = 0; i < nbrValves; i++)
			_valves.add(new Valve(i));

		_ruptures = new Hashtable<TypeBocal, Boolean>();
		//TODO initialiser les boolean pour chaque type de bocaux(J'ai creer une methode utile pour ca dans typeBocal)

		this._nbrValves = nbrValves;
	}

	private Vector<Valve> _valves;
	private Hashtable<TypeBocal, Vector<Bocal>> _bocauxDispo;
	private Hashtable<TypeBocal, Boolean> _ruptures; //Etrangement si on met "boolean" ca marche pas car cest un premitive type
	boolean brupture = false;
	boolean arupture = false;
	private int _nbrValves;

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

		boolean tourTypeB = false;

		while (!Confiturerie.EstArret()) {
			int nbValvesDispo = _nbrValves;

			if (Confiturerie.EstPause()) {
				try {
					Thread.sleep(Confiturerie.GetTempsSommeil());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			ArrayList<Thread> threads = new ArrayList<Thread>();

			if(brupture == false || arupture == false){
				if (tourTypeB == false){
					if (_bocauxDispo.get(TypeBocal.A).isEmpty() || arupture == true){
						int nbbocauxprets = _bocauxDispo.get(TypeBocal.B).size();
						int iv = 1;
						while(nbbocauxprets > 0){
							if (nbbocauxprets < nbValvesDispo){
								iv = nbbocauxprets;
							}
							else {
								iv = nbValvesDispo;
							}
							for (int i = (nbbocauxprets-1); i >= (nbbocauxprets-iv) && i >= 0; i--){
								int finalI = i;
								Thread t = new Thread(() -> _bocauxDispo.get(TypeBocal.B).get(finalI).RunRemplissage());
								t.start();
								threads.add(t);
								_bocauxDispo.remove(i);
							}
							for (Thread thread : threads) {
								thread.join();
							}
							nbbocauxprets = nbbocauxprets-iv;
						}
					}
					else if (arupture == false) {
						int nbbocauxprets = _bocauxDispo.get(TypeBocal.A).size();
						int iv = 1;
						while(nbbocauxprets > 0) {
							if (nbbocauxprets < nbValvesDispo) {
								iv = nbbocauxprets;
							} else {
								iv = nbValvesDispo;
							}
							for (int i = iv; i > -1; i--) {
								int finalI = i;
								Thread t = new Thread(() -> _bocauxDispo.get(TypeBocal.A).get(finalI).RunRemplissage());
								t.start();
								threads.add(t);
								_bocauxDispo.remove(i);
							}
							for (Thread thread : threads) {
								thread.join();
							}
							tourTypeB = true;
							nbbocauxprets = nbbocauxprets - iv;
						}
					}
				}
				else{
					if (_bocauxDispo.get(TypeBocal.B).isEmpty() || brupture == true){
						int nbbocauxprets = _bocauxDispo.get(TypeBocal.A).size();
						int iv = 1;
						while(nbbocauxprets > 0){
							if (nbbocauxprets < nbValvesDispo){
								iv = nbbocauxprets;
							}
							else {
								iv = nbValvesDispo;
							}
							for (int i = iv; i > -1; i--){
								int finalI = i;
								Thread t = new Thread(() -> _bocauxDispo.get(TypeBocal.A).get(finalI).RunRemplissage());
								t.start();
								threads.add(t);
								_bocauxDispo.remove(i);
							}
							for (Thread thread : threads) {
								thread.join();
							}
							nbbocauxprets = nbbocauxprets-iv;
						}
					}
					else if (brupture == false){
						int nbbocauxprets = _bocauxDispo.get(TypeBocal.B).size();
						int iv = 1;
						while(nbbocauxprets > 0) {
							if (nbbocauxprets < nbValvesDispo) {
								iv = nbbocauxprets;
							} else {
								iv = nbValvesDispo;
							}
							for (int i = iv; i > -1; i--) {
								int finalI = i;
								Thread t = new Thread(() -> _bocauxDispo.get(TypeBocal.B).get(finalI).RunRemplissage());
								t.start();
								threads.add(t);
								_bocauxDispo.remove(i);
							}
							for (Thread thread : threads) {
								thread.join();
							}
							tourTypeB = false;
							nbbocauxprets = nbbocauxprets - iv;
						}
					}
				}
			}

		}


		/*while (true) {
			boolean tourTypeB = false;
			int nbValvesDispo = nbValves;
			int compteur = 0;
			for (Valve valve : _valves) {
				if (valve.Dispo()) compteur++;
			}
			if (compteur != _valves.size()) {
				Thread.sleep(Confiturerie.GetTempsSommeil());
				continue;
			}
			else {
				if(brupture == false || arupture == false){
					if (tourTypeB == false){
						if (_bocauxDispo.get(A).isEmpty() || arupture == true){
							int nbbocauxprets = _bocauxDispo.get(B).size();
							int iv = 1;
							while(nbbocauxprets > 0){
								if (nbbocauxprets < nbValvesDispo){
									iv = nbbocauxprets;
								}
								else {
									iv = nbValvesDispo;
								}
								for (int i = (nbbocauxprets-1); i >= (nbbocauxprets-iv) && i >= 0; i--){
									Thread t = new Thread(() -> _bocauxDispo.get(B).get(i).RunRemplissage());
									t.start();
									_bocauxDispo.remove(i)
								}
								nbbocauxprets = nbbocauxprets-iv;
							}
						}
						else if (arupture == false) {
							int nbbocauxprets = _bocauxDispo.get(A).size();
							int iv = 1;
							while(nbbocauxprets > 0) {
								if (nbbocauxprets < nbValvesDispo) {
									iv = nbbocauxprets;
								} else {
									iv = nbValvesDispo;
								}
								for (int i = iv; i > -1; i--) {
									Thread t = new Thread(() -> _bocauxDispo.get(A).get(i).RunRemplissage());
									t.start();
									_bocauxDispo.remove(i);
								}
								tourTypeB = true;
								nbbocauxprets = nbbocauxprets - iv;
							}
						}
					}
					else{
						if (_bocauxDispo.get(B).isEmpty() || brupture == true){
							int nbbocauxprets = _bocauxDispo.get(A).size();
							int iv = 1;
							while(nbbocauxprets > 0){
								if (nbbocauxprets < nbValvesDispo){
									iv = nbbocauxprets;
								}
								else {
									iv = nbValvesDispo;
								}
								for (int i = iv; i > -1; i--){
									Thread t = new Thread(() -> _bocauxDispo.get(A).get(i).RunRemplissage());
									t.start();
									_bocauxDispo.remove(i);
								}
								nbbocauxprets = nbbocauxprets-iv;
							}
						}
						else if (brupture == false){
							int nbbocauxprets = _bocauxDispo.get(B).size();
							int iv = 1;
							while(nbbocauxprets > 0) {
								if (nbbocauxprets < nbValvesDispo) {
									iv = nbbocauxprets;
								} else {
									iv = nbValvesDispo;
								}
								for (int i = iv; i > -1; i--) {
									Thread t = new Thread(() -> _bocauxDispo.get(B).get(i).RunRemplissage());
									t.start();
									_bocauxDispo.remove(i);
								}
								tourTypeB = false;
								nbbocauxprets = nbbocauxprets - iv;
							}
						}
					}
				}

			}


			try {
				Thread.sleep(100); // Changer le 100 par tempsSommeil
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}*/
		
		/*while (true) {
			ArrayList<Valve> valvesDispo = new ArrayList<Valve>();
			
			for (Valve valve : _valves) {
				if (valve.Dispo()) valvesDispo.add(valve);
			}
			
			if (valvesDispo.size() != _valves.size()) continue; 
			
			for (TypeBocal type : TypeBocal.values()) {
				LinkedList<Bocal> bocaux = Confiturerie._bocalPool._bocaux.get(type);
				
				int counter = 0;
				
				for (Bocal bocal : bocaux) {
					if (bocal._etat == EtatBocal.VIDE) {
						bocal.AttribueValve(valvesDispo.get(counter));
						
						counter++;
						
						Thread t = new Thread(() -> bocal.RunRemplissage(100)); // Changer le 100 par tempsSommeil
					    t.start();
						
						if (counter == valvesDispo.size()) break; // TODO changer pour le nombre de bocaux max que l'on veux en meme temps, le nombre d'etiqueteuse dispo ?
					}
				}
				
				if (counter != 0) break; 
			}
			
			try {
				Thread.sleep(100); // Changer le 100 par tempsSommeil
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} */
	}

	/*
	 * <Brief> Methode simple qui ajoute un bocal a sa liste de bocaux pret a etre etiquette
	 * 	Il faut verifier le type du bocal pour l'ajouter au bon vector du hashTable
	 */
	public void AjouterBocal(Bocal bocal) {
		if (bocal.GetType() == TypeBocal.A){
			_bocauxDispo.get(TypeBocal.A).add(bocal);
		}
		if (bocal.GetType() == TypeBocal.B){
			_bocauxDispo.get(TypeBocal.B).add(bocal);
		}
	}

	/*
	* <Brief> Methode qui demarre une rupture pour une type de bocal. La rupture est gerer dans le Run.
	* 	Cette methode s'occupe simplement de le notifier depuis la variable
	*/
	public void Rupture(TypeBocal type) {
		if (type == TypeBocal.A){
			this.arupture = true;
		}
		if (type == TypeBocal.B){
			this.brupture = true;
		}
	}

	/*
	 * <Brief> Methode qui réaprovisionne et enlève une rupture pour une type de bocal. La rupture est gerer dans le Run.
	 * 	Cette methode s'occupe simplement de le notifier depuis la variable
	 */

	public void Approvisionnement(TypeBocal type){
			if (type == TypeBocal.A){
				this.arupture = false ;
			}
			if (type == TypeBocal.B){
				this.brupture = false;
			}
	}
}