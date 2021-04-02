import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

public class ControleRemplissage {
	
	public ControleRemplissage (int nbrValves) {
		_bocauxDispo = new Hashtable<TypeBocal, Vector<Bocal>>();
		//TODO initialiser les vector de bocaux pour chaque type de bocal

		_valves = new Vector<Valve>();
		for (int i = 0; i < nbrValves; i++)
			_valves.add(new Valve(i));

		_ruptures = new Hashtable<TypeBocal, Boolean>();
		//TODO initialiser les boolean pour chaque type de bocaux
	}

	private Vector<Valve> _valves;
	private Hashtable<TypeBocal, Vector<Bocal>> _bocauxDispo;
	private Hashtable<TypeBocal, Boolean> _ruptures; //Etrangement si on met "boolean" ca marche pas car cest un premitive type

	/*
	 * <Brief> Methode principale, run en thread, mais l'instance est unique. Elle doit tourner en boucle tant que la confiturerie
	 * 	n'est pas en arret. Si la confiturerie est en pause il faut rester dans la boucle sans executer le code.
	 * 	A chaque iteration il faut choisir le prochain bocal a faire passer en respectant les property du TP1 ainsi qu'une
	 * 	valve libre et assigner la valve au bocal en appelant la methode "RunRemplissage(V)" du bocal comme un
	 *  nouveau thread.
	 *
	 *  Il faut aussi tenir compte de la rupture
	 *
	 * */
	public void Run () {
		
		// TODO System peut etre ameliorer, pas vraiment de raisons d'utiliser un arraylist pour les etiqueteuses dispo, puisqu'on attend
		// qu'elles le soit toutes avant de les assigner
		
		while (true) {
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
		}
	}

	/*
	 * <Brief> Methode simple qui ajoute un bocal a sa liste de bocaux pret a etre etiquette
	 * 	Il faut verifier le type du bocal pour l'ajouter au bon vector du hashTable
	 */
	public void AjouterBocal(Bocal bocal) {
		//TODO implement
	}

	/*
	* <Brief> Methode qui demarre une rupture pour une type de bocal. La rupture est gerer dans le Run.
	* 	Cette methode s'occupe simplement de le notifier depuis la variable
	 */
	public void Rupture(TypeBocal type) {
		//TODO implement
	}
}