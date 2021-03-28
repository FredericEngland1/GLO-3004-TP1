
import java.util.ArrayList;

public class Console {

	ArrayList<String> _content;
	
	public Console () {
		_content = new ArrayList<String>();
	}
	
	public void AjouterTexte (String texte) {
		_content.add(texte);
	}
	
	// TODO doit etre changer pour fonctionner avec le UI choisi, ne devrais pas clear le content a chaque fois
	
	public void AfficherTexte () {
		for(String texte : _content) {
			System.out.println(texte);
		}
		Clear();
	}
	
	public void Clear () {
		_content.clear();
	}
	
	public ArrayList<String> GetContent () {
		return _content;
	}
	
}
