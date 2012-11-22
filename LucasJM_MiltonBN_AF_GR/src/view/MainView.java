package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Automato;
import model.GramaticaRegular;
import util.OperacoesConstantes;
import view.af.AFPanel;
import view.gr.GRPanel;
import controller.Controller;

public class MainView extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5597128573950583380L;

	JDialog aboutDialog;
	JPanel principalPanel;
	Controller controller;
	JScrollPane scrollPanel;

	public MainView(Controller controller) {
		super("Lucas Just Meller - Milton Bittencourt");
		this.controller = controller;
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		principalPanel = new JPanel();
		scrollPanel = new JScrollPane(principalPanel);
		add(scrollPanel);
		setResizable(true);
		setJMenuBar(new MenuBar(this));
		Dimension dimension = new Dimension(800, 450);
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
			principalPanel.add(new GRPanel(controller, this, OperacoesConstantes.NOVO));
			repaint();
			pack();
			break;
		case CARREGAR_AF:
			principalPanel.removeAll();
			AFPanel afp = new AFPanel(controller, this, OperacoesConstantes.NOVO);
			Automato automato = controller.getPersistencia().carregar(principalPanel);
			afp.setAutomato(automato);
			principalPanel.add(afp);
			repaint();
			pack();
			break;
		case CARREGAR_GR:
			principalPanel.removeAll();
			GRPanel grp = new GRPanel(controller, this, OperacoesConstantes.NOVO);
			GramaticaRegular gramatica = controller.getPersistencia().carregarGramaticaRegular(principalPanel);
			grp.setGramatica(gramatica);
			principalPanel.add(grp);
			repaint();
			pack();
			break;
		case ABOUT:
			aboutDialog = new AboutDialog(this);
			break;
		default:
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

	public void gerarGR(GramaticaRegular gramatica) {
		GRPanel grp = new GRPanel(controller, this, OperacoesConstantes.GERAR_GR);
		grp.setGramatica(gramatica);
		principalPanel.add(grp);
		pack();
	}

	public void gerarAutomatoDaGramatica(Automato automato) {
		AFPanel afp = new AFPanel(controller, this, OperacoesConstantes.NOVO, automato.getNome());
		afp.setAutomato(automato);
		principalPanel.add(afp);
		pack();
	}

}
