package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Automato;
import model.Estado;
import model.Transicao;
import util.OperacoesConstantes;
import view.af.AFPanel;
import view.gr.GRPanel;
import controller.Controller;

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
			principalPanel.add(new AFPanel(controller, this, OperacoesConstantes.NOVO));
			repaint();
			pack();
			break;
		case CRIAR_GR:
			principalPanel.removeAll();
			principalPanel.add(new GRPanel(controller, this));
			repaint();
			pack();
			break;
		case CARREGAR_AF:
			principalPanel.removeAll();
			AFPanel afp = new AFPanel(controller, this, OperacoesConstantes.NOVO);
			Automato automato = controller.getPersistencia().carregar(principalPanel);
			
			for (Estado estado : automato.getEstados()) {
				for (Transicao transicao : estado.getTransicoes()) {
					System.out.println(transicao.getEstadoDestino().getNome());
				}
			}
			
			afp.setAutomato(automato);
			principalPanel.add(afp);
			repaint();
			pack();
			break;
		case ABOUT:
			aboutDialog = new AboutDialog(this);
			break;
		}
	}

	public void gerarAutomatoDeterminizado(Automato automatoDet) {
		AFPanel afp = new AFPanel(controller, this, OperacoesConstantes.DETERMINIZACAO, automatoDet.getNome());
		afp.setAutomato(automatoDet);
		principalPanel.add(afp);
		pack();
	}

	public void removePanel(JPanel panel) {
		principalPanel.remove(panel);
		repaint();
	}

	public void gerarAutomatoMinimizado(Automato automatoMin) {
		AFPanel afp = new AFPanel(controller, this, OperacoesConstantes.MINIMIZACAO, automatoMin.getNome());
		afp.setAutomato(automatoMin);
		principalPanel.add(afp);
		pack();		
	}

}
