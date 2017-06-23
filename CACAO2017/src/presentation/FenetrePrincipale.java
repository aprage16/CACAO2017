package presentation;
import abstraction.distributeur.europe.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import control.CtrlBtnNext;
import control.CtrlCheckBoxHistorique;
import control.CtrlCheckBoxJournal;
import control.CtrlJTextField;
import control.CtrlCheckBoxGraphique;
import control.CtrlLabelEtape;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.MondeV0;

/**
 * Classe modelisant la fenetre principale de l'interface.
 * C'est cette classe qui comporte la methode main de l'application.
 * 
 * @author Romuald Debruyne
 */
public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = 1L;

	public FenetrePrincipale() {
		super("Prime CACAO");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Monde.LE_MONDE = new MondeV1(); 
		Monde.LE_MONDE.peupler();
		this.setLayout(new BorderLayout());

		// LABEL Etape indiquant l'etape a laquelle on est.
		JLabel labelEtape = new JLabel("Etape : 0");
		Monde.LE_MONDE.addObserver(new CtrlLabelEtape(labelEtape));
		this.add(labelEtape, BorderLayout.NORTH);

		// Indicateurs
		JPanel pGauche = new JPanel();
		pGauche.setLayout(new BoxLayout(pGauche, BoxLayout.Y_AXIS));
		pGauche.add(Box.createVerticalGlue());
		ArrayList<Indicateur> indicateurs = Monde.LE_MONDE.getIndicateurs();
		
		JPanel hIndic = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JLabel iChart = new JLabel(new ImageIcon("chart.png",
                "Graphique"));
		iChart.setBorder(new EmptyBorder(0, 1, 0, 1));
		hIndic.add(iChart);

		JLabel iHistory = new JLabel( new ImageIcon("history.png",
                "Historique"));
		iHistory.setBorder(new EmptyBorder(0, 1, 0, 1));
		hIndic.add(iHistory);

		pGauche.add(hIndic);

		for (Indicateur i : indicateurs){
			JPanel pIndic = new JPanel();
			pIndic.setLayout(new BorderLayout());
			JPanel pReste = new JPanel();
			
			// Nom de l'indicateur
			JLabel lIndic = new JLabel( i.getNom());
			lIndic.setAlignmentX(RIGHT_ALIGNMENT);
			pReste.add(lIndic);
			
			// Champ de saisie permettant de modifier la valeur de l'indicateur
			JTextField tIndic = new JTextField(12);
			tIndic.setHorizontalAlignment(SwingConstants.RIGHT);
			NumberFormat dc = NumberFormat.getInstance(Locale.FRANCE);
			dc.setMaximumFractionDigits(2);
			dc.setMinimumFractionDigits(2);
			String formattedText = dc.format(i.getValeur());
			tIndic.setText(formattedText);
			CtrlJTextField controlJTextField = new CtrlJTextField(tIndic, i);
			tIndic.addActionListener(controlJTextField);
			i.addObserver(controlJTextField);
			tIndic.setMinimumSize(new Dimension(400,tIndic.getSize().height));
			tIndic.setAlignmentX(RIGHT_ALIGNMENT);
			pReste.add(tIndic);
			pIndic.add(pReste, BorderLayout.EAST);
			
			// Case a cocher "Graphique" permettant d'afficher/cacher le graphique de l'indicateur
			JCheckBox cGraphiqueIndic = new JCheckBox(); 
			FenetreGraphique graphique = new FenetreGraphique(i.getNom(), 800, 600);
            graphique.ajouter(i.getCourbe());
			// Controleur permettant quand on clique sur la fermeture 
			// de la fenetre graphique de mettre a jour la case a cocher "graphique"
			// et quand on clique sur la case a cocher d'afficher/masquer le graphique
			CtrlCheckBoxGraphique ctg = new CtrlCheckBoxGraphique(graphique, cGraphiqueIndic);
            i.addObserver(ctg);
			cGraphiqueIndic.addActionListener(ctg);
			graphique.addWindowListener(ctg);
			cGraphiqueIndic.setAlignmentX(RIGHT_ALIGNMENT);
			cGraphiqueIndic.setBorder(BorderFactory.createEmptyBorder());

			pReste.add(cGraphiqueIndic);
			
			// Case a cocher "Historique" permettant d'afficher/cacher l'historique de l'indicateur
			JCheckBox cHistorique = new JCheckBox();
			FenetreHistorique fenetreHistorique = new FenetreHistorique(i);
			CtrlCheckBoxHistorique cth = new CtrlCheckBoxHistorique(cHistorique, fenetreHistorique);
			fenetreHistorique.addWindowListener(cth);
			i.getHistorique().addObserver(cth);
			cHistorique.addActionListener(cth);
			cHistorique.setAlignmentX(RIGHT_ALIGNMENT);
			cHistorique.setBorder(BorderFactory.createEmptyBorder());

			pReste.add(cHistorique);

			pGauche.add(Box.createVerticalGlue());
			pGauche.add(pIndic);
		}
		JPanel pIndicateurs = new JPanel();
		pIndicateurs.setBorder(BorderFactory.createTitledBorder("Indicateurs"));

		pIndicateurs.setLayout(new BorderLayout());
		pIndicateurs.add(pGauche, BorderLayout.CENTER);
		
		JPanel pDroit = new JPanel();
		pDroit.setLayout(new BoxLayout(pDroit, BoxLayout.Y_AXIS));
		pDroit.setBorder(BorderFactory.createTitledBorder("Journaux"));
		for (Journal j : Monde.LE_MONDE.getJournaux()) {
			JPanel pJournal = new JPanel();
			pJournal.setBorder(new EmptyBorder(0, 0, 0, 10));
			JLabel lJournal = new JLabel(j.getNom());
			lJournal.setAlignmentX(RIGHT_ALIGNMENT);
			pJournal.setLayout(new BorderLayout());
			pJournal.add(lJournal, BorderLayout.CENTER);
			JCheckBox cJournal = new JCheckBox(); 
			FenetreJournal fenetreJournal = new FenetreJournal(j);
			fenetreJournal.setCheckBox(cJournal);
			CtrlCheckBoxJournal controlJournal = new CtrlCheckBoxJournal(cJournal, fenetreJournal);
			j.addObserver(controlJournal);
			cJournal.addActionListener(controlJournal);

			cJournal.setAlignmentX(RIGHT_ALIGNMENT);
			pJournal.add(cJournal, BorderLayout.WEST);
			pDroit.add(Box.createVerticalGlue());
			pDroit.add(pJournal);
		}
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				pIndicateurs, pDroit);
		splitPane.setResizeWeight(0.5);
		Dimension minimumSize = new Dimension(100, 100);
		pIndicateurs.setMinimumSize(minimumSize);
		pDroit.setMinimumSize(minimumSize);
		this.add(splitPane, BorderLayout.CENTER);

		JPanel pSouth = new JPanel();
		pSouth.setLayout(new GridLayout(1,3));
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new CtrlBtnNext(Monde.LE_MONDE, 1));
		pSouth.add(btnNext);
		JButton btnNext10 = new JButton("10 Nexts");
		btnNext10.addActionListener(new CtrlBtnNext(Monde.LE_MONDE, 10));
		pSouth.add(btnNext10);
		JButton btnNext100 = new JButton("100 Nexts");
		btnNext100.addActionListener(new CtrlBtnNext(Monde.LE_MONDE,100));
		pSouth.add(btnNext100);
		
		this.add(pSouth, BorderLayout.SOUTH);
		this.pack();
	}

	public static void main(String[] args) {
		new FenetrePrincipale().setVisible(true);
	}
}
