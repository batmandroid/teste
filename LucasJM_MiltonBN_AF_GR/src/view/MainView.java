package view;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.af.AFPanel;
import view.gr.GRPanel;


public class MainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5597128573950583380L;

	JDialog aboutDialog;
	JPanel principalPanel;

	public MainView() {
		super("Lucas Just Meller - Milton Bittencourt");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		principalPanel = new JPanel();
		add(principalPanel);
		setResizable(true);
		setLocationRelativeTo(null);
		setJMenuBar(new MenuBar(this));
		Dimension dimension = new Dimension(600, 450);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		pack();
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		MenuOption option = MenuOption.valueOf(e.getActionCommand());

		switch (option) {
		case EXIT:
			System.exit(0);
		case CRIAR_AF:
			principalPanel.removeAll();
			principalPanel.add(new AFPanel());
			pack();
			break;
		case CRIAR_GR:
			principalPanel.removeAll();
			principalPanel.add(new GRPanel());
			pack();
			break;
		case ABOUT:
			aboutDialog = new AboutDialog(this);
			break;
		}
	}

}
