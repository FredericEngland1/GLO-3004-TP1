import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class ControleEtiquetage {
	
	public static Vector<Etiqueteuse> _etiqueteuses;
	
	public ControleEtiquetage (int nbEtiqueteuses) {
		for (int i = 1; i <= nbEtiqueteuses; i++) {
			_etiqueteuses.add(new Etiqueteuse(i));
		}
	}
	
	public void Run () {
		
		// TODO System peut etre ameliorer, pas vraiment de raisons d'utiliser un arraylist pour les etiqueteuses dispo, puisqu'on attend
		// qu'elles le soit toutes avant de les assigner
		
		while (true) {
			ArrayList<Integer> etiqueteuseDispo = new ArrayList<Integer>();
			
			for (Etiqueteuse etiqueteuse : _etiqueteuses) {
				if (etiqueteuse.Dispo()) etiqueteuseDispo.add(etiqueteuse._id);
			}
			
			if (etiqueteuseDispo.size() != _etiqueteuses.size()) continue; 
			
			for (TypeBocal type : TypeBocal.values()) {
				LinkedList<Bocal> bocaux = Confiturerie._bocalPool._bocaux.get(type);
				
				int counter = 0;
				
				for (Bocal bocal : bocaux) {
					if (bocal._etat == EtatBocal.REMPLIT) {
						counter++;
						
						Thread t = new Thread(() -> bocal.RunEtiquetage(100));
					    t.start();
						
						if (counter == etiqueteuseDispo.size()) break; // TODO changer pour le nombre de bocaux max que l'on veux en meme temps, le nombre d'etiqueteuse dispo ?
					}
				}
				
				if (counter != 0) break; 
			}
		}
	}
	
}
