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

	JButton btnCommencer;
	JButton btnPause;
	JButton btnRedemarrer;
	JButton btnArret;
	JButton btnMAJTempsSleep;
	JButton btnRupture;
	JButton btnApprovisionnement;
	JComboBox comboBox;

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

		spinnerBocaux.addChangeListener(e -> _nbBocaux = (int)((JSpinner)e.getSource()).getValue());
		spinnerValves.addChangeListener(e -> _nbValves = (int)((JSpinner)e.getSource()).getValue());
		spinnerEtiquettes.addChangeListener(e -> _nbEtiquette = (int)((JSpinner)e.getSource()).getValue());
		spinnerSleepTime.addChangeListener(e -> _tempsSomeil = (int)((JSpinner)e.getSource()).getValue());

		btnCommencer = new JButton("Commencer la simulation");
		btnPause = new JButton("Pause");
		btnRedemarrer = new JButton("Redemarrer");
		btnArret = new JButton("Arret");
		btnMAJTempsSleep = new JButton("MAJ Temps Sommeil");
		btnRupture = new JButton("Rupture");
		btnApprovisionnement = new JButton("Approvisionnement");
		JLabel lblRupture = new JLabel("Type :");
		comboBox = new JComboBox(TypeBocal.values());

		btnCommencer.addActionListener(e -> DebutConfiturerie());
		btnPause.addActionListener(e -> Pause());
		btnRedemarrer.addActionListener(e -> Redemarre());
		btnArret.addActionListener(e -> ArretConfiturerie());
		btnMAJTempsSleep.addActionListener(e -> MAJTempsSommeil());
		btnRupture.addActionListener(e -> Rupture());
		btnApprovisionnement.addActionListener(e -> Approvisionnement());

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
		panelBtn.add(btnApprovisionnement, constraints);
		panelBtn.add(lblRupture);
		panelBtn.add(comboBox);

		frame.getContentPane().add(BorderLayout.NORTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, panelConsole);
		frame.getContentPane().add(BorderLayout.SOUTH, panelBtn);

		btnPause.setEnabled(false);
		btnRedemarrer.setEnabled(false);
		btnArret.setEnabled(false);
		btnMAJTempsSleep.setEnabled(false);
		btnRupture.setEnabled(false);
		btnApprovisionnement.setEnabled(false);

		frame.setVisible(true);
	}

	public void DebutConfiturerie () {
		Confiturerie.InitConfiturerie(_nbBocaux, _nbValves, _nbEtiquette, _tempsSomeil, this);
		textConsole.setText("");
		AjouterTexte("Debut");

		btnCommencer.setEnabled(false);
		btnArret.setEnabled(true);

		btnPause.setEnabled(true);
		btnMAJTempsSleep.setEnabled(true);
		btnRupture.setEnabled(true);
		btnApprovisionnement.setEnabled(true);
	}
	
	public void ArretConfiturerie () {
		Confiturerie.ArretConfiturerie();
		AjouterTexte("Arret");

		btnCommencer.setEnabled(true);
		btnArret.setEnabled(false);

		btnPause.setEnabled(false);
		btnRedemarrer.setEnabled(false);
		btnMAJTempsSleep.setEnabled(false);
		btnRupture.setEnabled(false);
		btnApprovisionnement.setEnabled(false);
	}
	
	public void MAJTempsSommeil () {
		Confiturerie.MiseAJourTempsSommeil(_tempsSomeil);
		AjouterTexte("Temps Sommeil : " + Integer.toString(_tempsSomeil));
	}

	public void Rupture () {
		Confiturerie.Rupture(TypeBocal.values()[comboBox.getSelectedIndex()]);
		AjouterTexte("Rupture : " + TypeBocal.values()[comboBox.getSelectedIndex()].toString());
	}

	public void Approvisionnement () {
		Confiturerie.Approvisionnement(TypeBocal.values()[comboBox.getSelectedIndex()]);
		AjouterTexte("Approvisionnement : " + TypeBocal.values()[comboBox.getSelectedIndex()].toString());
	}
	
	public void Pause () {
		Confiturerie.Pause();
		AjouterTexte("Pause");

		btnRedemarrer.setEnabled(true);
		btnPause.setEnabled(false);
	}
	
	public void Redemarre () {
		Confiturerie.Redemarre();
		AjouterTexte("Redemarrage");

		btnRedemarrer.setEnabled(false);
		btnPause.setEnabled(true);
	}
	
	public synchronized void AjouterTexte (String texte) {
		System.out.print(texte + "\n");
		textConsole.append(texte + "\n");
	}
}
