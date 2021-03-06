package view.af;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	JLabel enumerarLabel;
	JTextField enumerarText;
	JLabel sentencaLabel;
	JTextField sentencaText;
	JButton fecharBtn;
	JButton adicionarLinhaBtn;
	JButton adicionarColunaBtn;
	JButton removerLinhaBtn;
	JButton removerColunaBtn;;
	JPanel operacoesAFPanel;
	JPanel sentencaPanel;
	JPanel centerPanel;
	JPanel operacoesTabelaPanel;
	JPanel nomePanel;
	JPanel topoPanel;
	JPanel enumerarPanel;
	JPanel rodapePanel;
	JScrollPane scrollPanel;
	JTable tabelaAF;
	AFTableModel modeloTabelaAF;
	JMenuBar menuOperacoes;
	JMenuItem determinizarItem;
	JMenuItem minimizarItem;
	JMenuItem gerarGRItem;
	JMenuItem salvarItem;
	JMenuItem validarSentencaItem;
	JMenuItem enumerarItem;

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

		enumerarLabel = new JLabel("Limite para enumeração");
		enumerarText = new JTextField(11);
		nomeLabel = new JLabel("Nome");
		nomeText = new JTextField(20);
		sentencaLabel = new JLabel("Sentença");
		sentencaText = new JTextField(20);
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
		sentencaPanel = new JPanel();
		centerPanel = new JPanel(new BorderLayout());
		operacoesTabelaPanel = new JPanel();
		enumerarPanel = new JPanel();
		rodapePanel = new JPanel(new BorderLayout());
		nomePanel = new JPanel();
		topoPanel = new JPanel(new BorderLayout());

		enumerarText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar())) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	private void criaMenuOperacoes() {
		menuOperacoes = new JMenuBar();
		menuOperacoes.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		JMenu menu;
		menu = new JMenu("Operações");
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
		validarSentencaItem = new JMenuItem("Validar sentença");
		validarSentencaItem.setActionCommand(MenuOption.VALIDAR_SENTENCA.name());
		validarSentencaItem.addActionListener(this);
		menu.add(validarSentencaItem);
		enumerarItem = new JMenuItem("Enumerar");
		enumerarItem.setActionCommand(MenuOption.ENUMERAR.name());
		enumerarItem.addActionListener(this);
		menu.add(enumerarItem);
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
		sentencaPanel.add(sentencaLabel);
		sentencaPanel.add(sentencaText);
		enumerarPanel.add(enumerarLabel);
		enumerarPanel.add(enumerarText);

		centerPanel.add(scrollPanel, BorderLayout.NORTH);
		centerPanel.add(sentencaPanel, BorderLayout.SOUTH);

		topoPanel.add(nomePanel, BorderLayout.NORTH);
		topoPanel.add(operacoesTabelaPanel, BorderLayout.SOUTH);

		rodapePanel.add(enumerarPanel, BorderLayout.NORTH);
		rodapePanel.add(operacoesAFPanel, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		this.add(topoPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(rodapePanel, BorderLayout.SOUTH);
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
			if (validaTabela()) {
				gerarGR();
			}
		} else if (e.getSource() == validarSentencaItem) {
			validaSentenca();
		} else if (e.getSource() == salvarItem) {
			controller.getPersistencia().salvarComo(this, geraAutomatoDaTabela());
		} else if (e.getSource() == fecharBtn) {
			mainView.removePanel(this);
		} else if (e.getSource() == enumerarItem) {
			enumerarAutomato();
		}
	}

	private void enumerarAutomato() {
		if (enumerarText.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o campo enumeração para executar essa ação!");
		} else {
			Automato automato = geraAutomatoDaTabela();
			if(automato.isAutomatoDeterministico()){
			String value = "";
			List<String> sentencas = controller.enumeraSentencas(geraAutomatoDaTabela(), Integer.parseInt(enumerarText.getText()));
			for (int i = 0; i < sentencas.size(); i++) {
				if(i == 0){
					value = sentencas.get(i);
				} else{
					value = value + " - " + sentencas.get(i);
				}
				if(i != 1 && i % 5 == 0){
					value = value + "\n";
				}
			}
			JOptionPane.showMessageDialog(null, value, "Sentenças válidas com limite " + enumerarText.getText(), JOptionPane.INFORMATION_MESSAGE);
			} else{
				JOptionPane.showMessageDialog(null, "Por favor, determinize o automato antes!", "Validação", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void validaSentenca() {
		List<Character> itensSentenca = new ArrayList<Character>();
		String sentenca = sentencaText.getText();
		for (int i = 0; i < sentenca.length(); i++) {
			itensSentenca.add(sentenca.charAt(i));
		}
		
		Automato automato = geraAutomatoDaTabela();
		
		if (automato.isAutomatoDeterministico()) {
			boolean reconhecida = controller.validaSentenca(geraAutomatoDaTabela(), itensSentenca);
			if (reconhecida) {
				JOptionPane.showMessageDialog(null, "Sentença válida", "Validação", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Sentença não válida", "Validação", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, determinize o automato antes!", "Validação", JOptionPane.ERROR_MESSAGE);
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

	private void gerarGR() {
		Automato automato = geraAutomatoDaTabela();
		mainView.gerarGR(controller.converteAFtoGR(automato));
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
					if (!transicoesSeparadas[h].trim().equals("")) {
						Transicao transicao = new Transicao(modeloTabelaAF.getColunas().get(i).trim().charAt(0), estados.get(transicoesSeparadas[h]
								.trim()));
						transicoes.add(transicao);
					}
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
			Boolean inicia = (Boolean) tabelaAF.getValueAt(row, col);
			if (inicia) {
				for (int i = 0; i < modeloTabelaAF.getItens().size(); i++) {
					if (((Boolean) modeloTabelaAF.getItens().get(i).get(0)) && row != i) {
						JOptionPane.showMessageDialog(null, "Apenas um estado pode ser inicial.");
						tabelaAF.setValueAt(false, row, col);
					}
				}
			}
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
		List<String> colunas = new ArrayList<String>();
		List<List<Object>> itens = new ArrayList<List<Object>>();

		for (Estado estado : automato.getEstados()) {
			for (Transicao transicao : estado.getTransicoes()) {
				if (!colunas.contains(transicao.getSimbolo().toString().trim())) {
					colunas.add(transicao.getSimbolo().toString().trim());
				}
			}
		}

		for (Estado estado : automato.getEstados()) {
			List<Object> linha = new ArrayList<Object>();
			linha.add(estado.isInicial());
			linha.add(estado.isEstFinal());
			linha.add(estado.getNome());
			List<Transicao> transicoes = estado.getTransicoes();
			for (int i = 0; i < colunas.size(); i++) {
				String trans = "";
				for (int j = 0; j < transicoes.size(); j++) {
					if (transicoes.get(j).getSimbolo().toString().equals(colunas.get(i))) {
						if (!trans.equals("")) {
							trans += ",";
						}
						trans += transicoes.get(j).getEstadoDestino().getNome().trim();
					}
				}
				linha.add(trans);
			}
			itens.add(linha);
		}

		modeloTabelaAF.setItens(itens);
		modeloTabelaAF.addColunas(colunas);
		ajustaTamanhoColunas();
	}

}
