import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;

public class InterfaceUtilisateur {

	// Valeurs par default

	int _nbBocaux = 2;
	int _nbValves = 2;
	int _nbEtiquette = 2;
	int _tempsSomeil = 100;

	JComboBox comboBox;

	Console _console = new Console();
	TextArea textConsole;

	public InterfaceUtilisateur() {
		JFrame frame = new JFrame("Confiturerie");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,500);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		JLabel lblBocaux = new JLabel("Nombre de bocaux :");
		JLabel lblValves = new JLabel("Nombre de valves :");
		JLabel lblEtiquettes = new JLabel("Nombre d'etiquettes :");
		JLabel lblSleepTime = new JLabel("Temps de sommeil :");

		SpinnerModel modelBocaux = new SpinnerNumberModel(_nbBocaux,0,1000,1);
		SpinnerModel modelValves = new SpinnerNumberModel(_nbValves,0,1000,1);
		SpinnerModel modelEtiquettes = new SpinnerNumberModel(_nbEtiquette,0,1000,1);
		SpinnerModel modelSleepTime = new SpinnerNumberModel(_tempsSomeil,0,10000,100);

		JSpinner spinnerBocaux = new JSpinner(modelBocaux);
		JSpinner spinnerValves = new JSpinner(modelValves);
		JSpinner spinnerEtiquettes = new JSpinner(modelEtiquettes);
		JSpinner spinnerSleepTime = new JSpinner(modelSleepTime);

		spinnerBocaux.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				_nbBocaux = (int)((JSpinner)e.getSource()).getValue();
			}
		});
		spinnerValves.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				_nbValves = (int)((JSpinner)e.getSource()).getValue();
			}
		});
		spinnerEtiquettes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				_nbEtiquette = (int)((JSpinner)e.getSource()).getValue();
			}
		});
		spinnerSleepTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				_tempsSomeil = (int)((JSpinner)e.getSource()).getValue();
			}
		});

		JButton btnCommencer = new JButton("Commencer la simulation");
		JButton btnPause = new JButton("Pause");
		JButton btnRedemarrer = new JButton("Redemarrer");
		JButton btnArret = new JButton("Arret");
		JButton btnMAJTempsSleep = new JButton("MAJ Temps Sommeil");
		JButton btnRupture = new JButton("Rupture");
		JLabel lblRupture = new JLabel("Type Rupture :");
		comboBox = new JComboBox(TypeBocal.values());

		btnCommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DebutConfiturerie();
			}
		});
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pause();
			}
		});
		btnRedemarrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Redemarre();
			}
		});
		btnArret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArretConfiturerie();
			}
		});
		btnMAJTempsSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MAJTempsSommeil();
			}
		});
		btnRupture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rupture();
			}
		});

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;

		panel.add(lblBocaux, constraints);
		panel.add(lblValves, constraints);
		panel.add(lblEtiquettes, constraints);
		panel.add(lblSleepTime, constraints);

		constraints.gridx = 1;

		panel.add(spinnerBocaux, constraints);
		panel.add(spinnerValves, constraints);
		panel.add(spinnerEtiquettes, constraints);
		panel.add(spinnerSleepTime, constraints);

		JPanel panelConsole = new JPanel();

		textConsole = new TextArea(20, 130);

		panelConsole.add(textConsole);

		JPanel panelBtn = new JPanel();

		panelBtn.add(btnCommencer, constraints);
		panelBtn.add(btnPause, constraints);
		panelBtn.add(btnRedemarrer, constraints);
		panelBtn.add(btnArret, constraints);
		panelBtn.add(btnMAJTempsSleep, constraints);
		panelBtn.add(btnRupture, constraints);
		panelBtn.add(lblRupture);
		panelBtn.add(comboBox);

		frame.getContentPane().add(BorderLayout.NORTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, panelConsole);
		frame.getContentPane().add(BorderLayout.SOUTH, panelBtn);

		frame.setVisible(true);
	}

	public void DebutConfiturerie () {
		Confiturerie.InitConfiturerie(_nbBocaux, _nbValves, _nbEtiquette, _tempsSomeil, this);
		AjouterTexte("Debut");
	}
	
	public void ArretConfiturerie () {
		Confiturerie.ArretConfiturerie();
		AjouterTexte("Arret");
	}
	
	public void MAJTempsSommeil () {
		Confiturerie.MiseAJourTempsSommeil(_tempsSomeil);
		AjouterTexte("Temps Sommeil : " + Integer.toString(_tempsSomeil));
	}
	
	public void Rupture () {
		Confiturerie.Rupture(TypeBocal.values()[comboBox.getSelectedIndex()]);
		AjouterTexte("Rupture : " + TypeBocal.values()[comboBox.getSelectedIndex()].toString());
	}
	
	public void Pause () {
		Confiturerie.Pause();
		AjouterTexte("Pause");
	}
	
	public void Redemarre () {
		Confiturerie.Redemarre();
		AjouterTexte("Redemarrage");
	}
	
	// TODO changer le moment de l'affichage pour faire du sens
	
	public synchronized void AjouterTexte (String texte) {
		_console.AjouterTexte(texte);
		System.out.print(texte + "\n");
		this.AfficherTexte();
	}

	public void AfficherTexte(){
		textConsole.setText(String.join("\n", _console.GetContent()));
	}
}
