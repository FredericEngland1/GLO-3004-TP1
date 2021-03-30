import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class ControleRemplissage {

	public static Vector<Valve> _valves = new Vector<Valve>();
	
	public ControleRemplissage (int nbValves) {
		for (int i = 1; i <= nbValves; i++) {
			_valves.add(new Valve(i));
		}
	}
	
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
	
}