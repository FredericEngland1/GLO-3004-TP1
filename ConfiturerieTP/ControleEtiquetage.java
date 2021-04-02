import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

public class ControleEtiquetage {
	
	public ControleEtiquetage (int nbrEtiqueteuses) {
		_bocauxDispo = new Hashtable<TypeBocal, Vector<Bocal>>();
		//TODO initialiser les vector de bocaux pour chaque type de bocal(J'ai creer une methode utile pour ca dans typeBocal)

		_etiqueteuses = new Vector<Etiqueteuse>();
		for (int i = 0; i < nbrEtiqueteuses; i++)
			_etiqueteuses.add(new Etiqueteuse(i));
	}

	private Vector<Etiqueteuse> _etiqueteuses;
	private Hashtable<TypeBocal, Vector<Bocal>> _bocauxDispo;

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
	public void Run () {
		
		// TODO System peut etre ameliorer, pas vraiment de raisons d'utiliser un arraylist pour les etiqueteuses dispo, puisqu'on attend
		// qu'elles le soit toutes avant de les assigner
		
		while (true) {
			ArrayList<Etiqueteuse> etiqueteusesDispo = new ArrayList<Etiqueteuse>();
			
			for (Etiqueteuse etiqueteuse : _etiqueteuses) {
				if (etiqueteuse.Dispo()) etiqueteusesDispo.add(etiqueteuse);
			}
			
			if (etiqueteusesDispo.size() != _etiqueteuses.size()) continue; 
			
			for (TypeBocal type : TypeBocal.values()) {
				LinkedList<Bocal> bocaux = Confiturerie._bocalPool._bocaux.get(type);
				
				int counter = 0;
				
				for (Bocal bocal : bocaux) {
					if (bocal._etat == EtatBocal.REMPLIT) {
						bocal.AttribueEtiqueteuse(etiqueteusesDispo.get(counter));
						
						counter++;
						
						Thread t = new Thread(() -> bocal.RunEtiquetage(100)); // Changer le 100 par tempsSommeil
					    t.start();
						
						if (counter == etiqueteusesDispo.size()) break; // TODO changer pour le nombre de bocaux max que l'on veux en meme temps, le nombre d'etiqueteuse dispo ?
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
}
