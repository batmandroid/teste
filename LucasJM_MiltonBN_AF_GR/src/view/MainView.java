package view;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Automato;

import controller.Controller;

import view.af.AFPanel;
import view.gr.GRPanel;


public class MainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5597128573950583380L;

	JDialog aboutDialog;
	JPanel principalPanel;
	Controller controller;
	
	public MainView(Controller controller) {
		super("Lucas Just Meller - Milton Bittencourt");
		this.controller = controller;
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
			principalPanel.add(new AFPanel(controller, this));
			pack();
			break;
		case CRIAR_GR:
			principalPanel.removeAll();
			principalPanel.add(new GRPanel(controller, this));
			pack();
			break;
		case ABOUT:
			aboutDialog = new AboutDialog(this);
			break;
		}
	}

	public void gerarAutomato(Automato automatoDet) {
		AFPanel afp = new AFPanel(controller, this);
		afp.setAutomato(automatoDet);
		principalPanel.add(afp);
		pack();
	}

}
