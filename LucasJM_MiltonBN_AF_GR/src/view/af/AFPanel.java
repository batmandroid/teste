package view.af;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import model.Automato;
import model.Estado;
import model.Transicao;
import util.OperacoesConstantes;
import view.MainView;
import view.MenuOption;
import controller.Controller;

public class AFPanel extends JPanel implements ActionListener, TableModelListener {

	private static final long serialVersionUID = 4834066306800198068L;

	Controller controller;
	MainView mainView;
	JLabel nomeLabel;
	JTextField nomeText;
	JButton fecharBtn;
	JButton adicionarLinhaBtn;
	JButton adicionarColunaBtn;
	JButton removerLinhaBtn;
	JButton removerColunaBtn;;
	JPanel operacoesTabelaPanel;
	JPanel operacoesAFPanel;
	JPanel topoPanel;
	JPanel nomePanel;
	JScrollPane scrollPanel;
	JTable tabelaAF;
	AFTableModel modeloTabelaAF;
	JMenuBar menuOperacoes;
	JMenuItem determinizarItem;
	JMenuItem minimizarItem;
	JMenuItem gerarGRItem;
	JMenuItem salvarItem;

	public AFPanel(Controller controller, MainView mainView, String operacao, String nomePai) {
		this.controller = controller;
		this.mainView = mainView;
		modeloTabelaAF = new AFTableModel();

		definaComponentes();
		posicioneComponentes();

		if (operacao.equals(OperacoesConstantes.NOVO)) {
			this.setBorder(BorderFactory.createTitledBorder("Autômato Finito"));
		} else if (operacao.equals(OperacoesConstantes.DETERMINIZACAO)) {
			this.setBorder(BorderFactory.createTitledBorder("AF determinizado do AF " + nomePai));
		} else if (operacao.equals(OperacoesConstantes.MINIMIZACAO)) {
			this.setBorder(BorderFactory.createTitledBorder("AF minimizado do AF " + nomePai));
		}

	}

	public AFPanel(Controller controller, MainView mainView, String operacao) {
		this(controller, mainView, operacao, "");
	}

	private void definaComponentes() {
		tabelaAF = new JTable(modeloTabelaAF);
		tabelaAF.setRowSelectionAllowed(false);
		tabelaAF.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAF.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPanel = new JScrollPane(tabelaAF);
		scrollPanel.setPreferredSize(new Dimension(300, 150));
		tabelaAF.getModel().addTableModelListener(this);

		ajustaTamanhoColunas();

		nomeLabel = new JLabel("Nome");
		nomeText = new JTextField(20);
		fecharBtn = new JButton(new ImageIcon("src/image/close.png", "Fechar"));
		fecharBtn.addActionListener(this);
		fecharBtn.setPreferredSize(new Dimension(16, 16));
		adicionarLinhaBtn = new JButton("+ Lin");
		adicionarLinhaBtn.addActionListener(this);
		adicionarColunaBtn = new JButton("+ Col");
		adicionarColunaBtn.addActionListener(this);
		removerLinhaBtn = new JButton("- Lin");
		removerLinhaBtn.addActionListener(this);
		removerColunaBtn = new JButton("- Col");
		removerColunaBtn.addActionListener(this);

		criaMenuOperacoes();

		operacoesAFPanel = new JPanel();
		operacoesTabelaPanel = new JPanel();
		nomePanel = new JPanel();
		topoPanel = new JPanel(new BorderLayout());
	}

	private void criaMenuOperacoes() {
		menuOperacoes = new JMenuBar();
		menuOperacoes.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JMenu menu;
		menu = new JMenu("Operações do Autômato");
		determinizarItem = new JMenuItem("Determinizar");
		determinizarItem.setActionCommand(MenuOption.DETERMINIZAR.name());
		determinizarItem.addActionListener(this);
		menu.add(determinizarItem);
		minimizarItem = new JMenuItem("Minimizar");
		minimizarItem.setActionCommand(MenuOption.MINIMIZAR.name());
		minimizarItem.addActionListener(this);
		menu.add(minimizarItem);
		gerarGRItem = new JMenuItem("Gerar GR");
		gerarGRItem.setActionCommand(MenuOption.GERAR_GR.name());
		gerarGRItem.addActionListener(this);
		menu.add(gerarGRItem);
		salvarItem = new JMenuItem("Salvar");
		salvarItem.setActionCommand(MenuOption.SALVAR.name());
		salvarItem.addActionListener(this);
		menu.add(salvarItem);

		menuOperacoes.add(menu);
	}

	private void posicioneComponentes() {
		operacoesTabelaPanel.add(adicionarLinhaBtn);
		operacoesTabelaPanel.add(adicionarColunaBtn);
		operacoesTabelaPanel.add(removerLinhaBtn);
		operacoesTabelaPanel.add(removerColunaBtn);
		operacoesAFPanel.add(menuOperacoes);
		nomePanel.add(fecharBtn);
		nomePanel.add(nomeLabel);
		nomePanel.add(nomeText);

		topoPanel.add(nomePanel, BorderLayout.NORTH);
		topoPanel.add(operacoesTabelaPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add("North", topoPanel);
		this.add("Center", scrollPanel);
		this.add("South", operacoesAFPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adicionarLinhaBtn) {
			modeloTabelaAF.adicionarLinha();
		} else if (e.getSource() == adicionarColunaBtn) {
			String input = JOptionPane.showInputDialog(null, "Digite o valor da nova coluna.");
			if (modeloTabelaAF.getColunas().contains(input.trim())) {
				JOptionPane.showMessageDialog(null, "Essa transição já existe.");
			} else if (!input.trim().equals("") && input.trim().matches("^([a-z]|[0-9])$")) {
				modeloTabelaAF.adicionarColuna(input);
				ajustaTamanhoColunas();
			} else {
				JOptionPane.showMessageDialog(null, "Símbolos terminais deve ser apenas 1 letra minúscula ou dígito.");
			}
		} else if (e.getSource() == removerLinhaBtn) {
			int lin = tabelaAF.getSelectedRow();
			if (lin != -1) {
				modeloTabelaAF.removerLinha(lin);
			}
		} else if (e.getSource() == removerColunaBtn) {
			int col = tabelaAF.getSelectedColumn();
			if (col == 0 || col == 1 || col == 2) {
				JOptionPane.showMessageDialog(null, "Essa coluna não pode ser deletada.");
			} else if (col != -1) {
				modeloTabelaAF.removerColuna(col);
				ajustaTamanhoColunas();
			}
		} else if (e.getSource() == minimizarItem) {
			if (validaTabela()) {
				minimizarAutomato();
			}
		} else if (e.getSource() == determinizarItem) {
			if (validaTabela()) {
				determinizarAutomato();
			}
		} else if (e.getSource() == gerarGRItem) {

		} else if (e.getSource() == salvarItem) {
			controller.getPersistencia().salvarComo(this, geraAutomatoDaTabela());
		} else if (e.getSource() == fecharBtn) {
			mainView.removePanel(this);
		}
	}

	private boolean validaTabela() {
		int inicios = 0;
		int finais = 0;
		for (int i = 0; i < modeloTabelaAF.getItens().size(); i++) {
			if (((Boolean) modeloTabelaAF.getItens().get(i).get(0))) {
				inicios++;
			}
			if (((Boolean) modeloTabelaAF.getItens().get(i).get(1))) {
				finais++;
			}
		}
		if (inicios != 1 && finais == 0) {
			JOptionPane.showMessageDialog(null, "Deve haver apenas 1 estado inicial e no mínimo 1 estado final.");
			return false;
		} else if (inicios != 1 && finais != 0) {
			JOptionPane.showMessageDialog(null, "Deve haver apenas 1 estado inicial.");
			return false;
		} else if (inicios == 1 && finais == 0) {
			JOptionPane.showMessageDialog(null, "Deve haver no mínimo 1 estado final.");
			return false;
		} else {
			return true;
		}
	}

	private void determinizarAutomato() {
		Automato automato = geraAutomatoDaTabela();
		mainView.gerarAutomatoDeterminizado(controller.determinizaAutomato(automato));
	}

	private void minimizarAutomato() {
		Automato automato = geraAutomatoDaTabela();
		mainView.gerarAutomatoMinimizado(controller.minimizaAutomato(automato));
	}

	private Automato geraAutomatoDaTabela() {
		Automato automato = new Automato(nomeText.getText(), "");

		Map<String, Estado> estados = new LinkedHashMap<String, Estado>();

		for (List<Object> linha : modeloTabelaAF.getItens()) {
			Estado estado = new Estado(((String) linha.get(2)).trim(), (Boolean) linha.get(0), (Boolean) linha.get(1));
			estados.put(((String) linha.get(2)).trim(), estado);
		}

		for (int j = 0; j < modeloTabelaAF.getItens().size(); j++) {
			List<Transicao> transicoes = new ArrayList<Transicao>();
			for (int i = 3; i < modeloTabelaAF.getColunas().size(); i++) {
				String[] transicoesSeparadas = ((String) modeloTabelaAF.getItens().get(j).get(i)).trim().split(",");
				for (int h = 0; h < transicoesSeparadas.length; h++) {
					Transicao transicao = new Transicao(modeloTabelaAF.getColunas().get(i).trim().charAt(0), estados.get(transicoesSeparadas[h]
							.trim()));
					transicoes.add(transicao);
				}
			}
			estados.get(((String) modeloTabelaAF.getItens().get(j).get(2)).trim()).setTransicoes(transicoes);
		}

		List<Estado> estadosLista = new ArrayList<Estado>();
		for (Estado estado : estados.values()) {
			estadosLista.add(estado);
		}
		automato.setEstados(estadosLista);
		return automato;
	}

	private void ajustaTamanhoColunas() {
		TableColumn column = null;
		for (int i = 0; i < tabelaAF.getColumnCount(); i++) {
			column = tabelaAF.getColumnModel().getColumn(i);
			if (i == 0 || i == 1) {
				column.setMaxWidth(20);
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.UPDATE:
			int row = tabelaAF.getSelectedRow();
			int col = tabelaAF.getSelectedColumn();

			if (row != -1 && col != -1) {
				validaEntrada(row, col);
			}
		}

	}

	private void validaEntrada(int row, int col) {
		switch (col) {
		case 0:
			break;
		case 1:
			break;
		default:
			String typed = (String) tabelaAF.getValueAt(row, col);
			typed = typed.trim();

			if (!typed.equals("") && !typed.matches("^(q)[0-9]+(,(q)[0-9]+)*$")) {
				JOptionPane.showMessageDialog(null, "O valor deve ser 'q_', 'q_q_...' ou q_,q_...");
				tabelaAF.setValueAt(" ", row, col);
			}
			break;
		}
	}

	public void setAutomato(Automato automato) {
		nomeText.setText(automato.getNome());
		Set<String> colunas = new HashSet<String>();
		List<List<Object>> itens = new ArrayList<List<Object>>();

		for (Estado estado : automato.getEstados()) {
			List<Object> linha = new ArrayList<Object>();
			linha.add(estado.isInicial());
			linha.add(estado.isEstFinal());
			linha.add(estado.getNome());
			String trans = "";
			for (Transicao transicao : estado.getTransicoes()) {
				if (!trans.equals("")) {
					trans += ",";
				}
				trans += transicao.getEstadoDestino().getNome();
				colunas.add(transicao.getSimbolo().toString());
			}
			linha.add(trans);
			itens.add(linha);
		}
		modeloTabelaAF.setItens(itens);
		modeloTabelaAF.addColunas(colunas);
		ajustaTamanhoColunas();
	}

}
