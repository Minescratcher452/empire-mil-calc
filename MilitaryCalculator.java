package com.empire;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MilitaryCalculator extends JFrame implements ActionListener {

	ResultHelper resultHelper;
	private static final String VICTORY_RESULT_PATH = "victory_results.txt";
	private static final String TIE_RESULT_PATH = "tie_results.txt";
	private static final String WINNER_TAG = "<WINNER>";
	private static final String LOSER_TAG = "<LOSER>";

	JButton generate;
	JLabel sideA, sideA_Mil_l, sideA_Name_l, sideA_Troops_l, sideA_Bonuses_l, sideB, sideB_Mil_l, sideB_Name_l,
			sideB_Troops_l, sideB_Bonuses_l;
	JTextField sideA_Name, sideA_Mil, sideA_Troops, sideA_Bonuses, sideB_Name, sideB_Mil, sideB_Troops, sideB_Bonuses;
	JTextArea outcome;
	JPanel aPanel, bPanel, tPanel, oPanel, wPanel;
	JCheckBox sideA_spec, sideB_spec;

	String sideAName = "A";
	String sideBName = "B";

	int sideAMil = 0;
	int sideBMil = 0;

	int sideATroops = 0;
	int sideBTroops = 0;

	int sideABonuses = 0;
	int sideBBonuses = 0;

	int difference = 0;

	public MilitaryCalculator() {
		super("EMPIRE! 4 Military Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		resultHelper = new ResultHelper(VICTORY_RESULT_PATH, TIE_RESULT_PATH);

		// Panels
		aPanel = new JPanel();
		bPanel = new JPanel();
		oPanel = new JPanel();
		wPanel = new JPanel();
		tPanel = new JPanel();

		// Input
		generate = new JButton("Generate");
		generate.addActionListener(this);

		// Side A
		sideA_Name = new JTextField(20);
		sideA_Mil = new JTextField(4);
		sideA_Troops = new JTextField(4);
		sideA_Bonuses = new JTextField(4);

		sideA = new JLabel("\tSide A");
		sideA_Name_l = new JLabel("Name");
		sideA_Mil_l = new JLabel("Mil Score");
		sideA_Troops_l = new JLabel("Troops");
		sideA_Bonuses_l = new JLabel("Other Bonuses");

		sideA_spec = new JCheckBox("Military Specialization");

		// Side B
		sideB_Name = new JTextField(20);
		sideB_Mil = new JTextField(4);
		sideB_Troops = new JTextField(4);
		sideB_Bonuses = new JTextField(4);

		sideB = new JLabel("\tSide B");
		sideB_Name_l = new JLabel("Name");
		sideB_Mil_l = new JLabel("Mil Score");
		sideB_Troops_l = new JLabel("Troops");
		sideB_Bonuses_l = new JLabel("Other Bonuses");

		sideB_spec = new JCheckBox("Military Specialization");

		// Output
		outcome = new JTextArea(20, 5);

		// Layout
		BoxLayout aLayout = new BoxLayout(aPanel, BoxLayout.Y_AXIS);
		aPanel.setLayout(aLayout);
		BoxLayout bLayout = new BoxLayout(bPanel, BoxLayout.Y_AXIS);
		bPanel.setLayout(bLayout);
		BorderLayout oLayout = new BorderLayout();
		oPanel.setLayout(oLayout);

		GridLayout tLayout = new GridLayout(1, 2);
		tPanel.setLayout(tLayout);

		GridLayout windowLayout = new GridLayout(1, 2, 15, 10);
		wPanel.setLayout(windowLayout);

		BorderLayout mainLayout = new BorderLayout();
		setLayout(mainLayout);

		// Add
		aPanel.add(sideA_Name_l);
		aPanel.add(sideA_Name);
		aPanel.add(sideA_Mil_l);
		aPanel.add(sideA_Mil);
		aPanel.add(sideA_Troops_l);
		aPanel.add(sideA_Troops);
		aPanel.add(sideA_Bonuses_l);
		aPanel.add(sideA_Bonuses);
		aPanel.add(sideA_spec);

		bPanel.add(sideB_Name_l);
		bPanel.add(sideB_Name);
		bPanel.add(sideB_Mil_l);
		bPanel.add(sideB_Mil);
		bPanel.add(sideB_Troops_l);
		bPanel.add(sideB_Troops);
		bPanel.add(sideB_Bonuses_l);
		bPanel.add(sideB_Bonuses);
		bPanel.add(sideB_spec);

		wPanel.add(aPanel);
		wPanel.add(bPanel);

		oPanel.add(generate, "North");
		oPanel.add(outcome, "Center");

		tPanel.add(sideA);
		tPanel.add(sideB);

		add("North", tPanel);
		add("Center", wPanel);
		add("South", oPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] arguments) {
		MilitaryCalculator milGen = new MilitaryCalculator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == generate) {
			Random random = new Random();

			generateDescription(generateResult(random), random);
		}
	}

	/** 
	 * Pulls information from the text input fields and rolls dice. Also prints winning side and difference between rolls to output.
	 * <p>
	 * 
	 * @param	random	Random object used for dice.
	 * @return	Returns 0 for tie, 1 for side A victory, 2 for side B victory
	 */
	private int generateResult(Random random) {
		// Names
		sideAName = sideA_Name.getText();
		sideBName = sideB_Name.getText();

		// Mil scores
		sideAMil = Integer.parseInt(sideA_Mil.getText());
		sideBMil = Integer.parseInt(sideB_Mil.getText());

		// Troops
		sideATroops = Integer.parseInt(sideA_Troops.getText());
		sideBTroops = Integer.parseInt(sideB_Troops.getText());

		// Bonuses
		sideABonuses = Integer.parseInt(sideA_Bonuses.getText());
		sideBBonuses = Integer.parseInt(sideB_Bonuses.getText());

		// Generate
		int sideARoll, sideBRoll;
		if (!sideA_spec.isSelected()) {
			sideARoll = (random.nextInt(10) + 1) + (random.nextInt(10) + 1);
		} else {
			sideARoll = (random.nextInt(12) + 1) + (random.nextInt(12) + 1);
		}

		if (!sideB_spec.isSelected()) {
			sideBRoll = (random.nextInt(10) + 1) + (random.nextInt(10) + 1);
		} else {
			sideBRoll = (random.nextInt(12) + 1) + (random.nextInt(12) + 1);
		}

		sideARoll = sideARoll + sideAMil + sideATroops + sideABonuses;
		sideBRoll = sideBRoll + sideBMil + sideBTroops + sideBBonuses;

		String out = sideAName + ": " + sideARoll + System.lineSeparator() + sideBName + ": " + sideBRoll
				+ System.lineSeparator() + System.lineSeparator();

		int result = 0;
		if (sideARoll > sideBRoll) {
			difference = sideARoll - sideBRoll;
			out = out.concat("Side A '" + sideAName + "' wins by " + difference);
			result = 1;
		}

		else if (sideARoll < sideBRoll) {
			difference = sideBRoll - sideARoll;
			out = out.concat("Side B '" + sideBName + "' wins by " + difference);
			result = 2;
		}

		else {
			difference = 0;
			out = out.concat("Tie");
		}

		outcome.setText(out);
		repaint();

		return result;
	}

	/** 
	 * Sets the winner and loser of the battle, then chooses a description using resultHelper.
	 * <p>
	 * WINNER_TAG represents the winner if not a tie, or side A if tied.
	 * LOSER_TAG represents the loser if not a tie, or side B if tied.
	 * 
	 * @param	result	The result in integer form returned by generateResult().
	 * @param	random	Random object passed to resultHelper.getRandomResult().
	 * @return	void
	 */
	private void generateDescription(int result, Random random) {

		String out, winner, loser;
		if (result == 0) { // Tie
			out = outcome.getText() + "\n\n*\t*\t*\t\n\n" + resultHelper.getRandomDescription(random, true, difference);
			winner = sideAName;
			loser = sideBName;
		} else {
			out = outcome.getText() + "\n\n*\t*\t*\t\n\n" + resultHelper.getRandomDescription(random, false, difference);
			if (result == 1) { // Side A win
				winner = sideAName;
				loser = sideBName;
			} else { // Side B win
				winner = sideBName;
				loser = sideAName;
			}
		}

		out = out.replace(WINNER_TAG, winner).replace(LOSER_TAG, loser);

		//System.out.println(out);
		outcome.setText(out);
		repaint();
	}
}
