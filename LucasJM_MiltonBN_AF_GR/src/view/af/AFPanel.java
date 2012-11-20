package view.af;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import controller.Controller;

public class AFPanel extends JPanel implements ActionListener, TableModelListener {

	private static final long serialVersionUID = 4834066306800198068L;

	Controller controller;
	MainView mainView;
	JLabel nomeLabel;
	JTextField nomeText;
	JButton adicionarLinhaBtn;
	JButton adicionarColunaBtn;
	JButton removerLinhaBtn;
	JButton removerColunaBtn;;
	JButton determinizarBtn;
	JButton minimizarBtn;
	JButton gerarGRBtn;
	JButton salvarBtn;
	JPanel operacoesTabelaPanel;
	JPanel operacoesAFPanel;
	JPanel topoPanel;
	JPanel nomePanel;
	JScrollPane scrollPanel;
	JTable tabelaAF;
	AFTableModel modeloTabelaAF;

	public AFPanel(Controller controller, MainView mainView, String operacao, String nomePai) {
		this.controller = controller;
		this.mainView = mainView;
		modeloTabelaAF = new AFTableModel();

		definaComponentes();
		posicioneComponentes();

		if (operacao.equals(OperacoesConstantes.NOVO)) {
			this.setBorder(BorderFactory.createTitledBorder("Autômato Finito"));
		} else if (operacao.equals(OperacoesConstantes.DETERMINIZACAO)) {
			this.setBorder(BorderFactory.createTitledBorder("Autômato Finito determinizado do AF " + nomePai));
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
		nomeText = new JTextField(25);
		adicionarLinhaBtn = new JButton("+ Linha");
		adicionarLinhaBtn.addActionListener(this);
		adicionarColunaBtn = new JButton("+ Coluna");
		adicionarColunaBtn.addActionListener(this);
		removerLinhaBtn = new JButton("- Linha");
		removerLinhaBtn.addActionListener(this);
		removerColunaBtn = new JButton("- Coluna");
		removerColunaBtn.addActionListener(this);
		minimizarBtn = new JButton("Minimizar");
		minimizarBtn.addActionListener(this);
		determinizarBtn = new JButton("Determinizar");
		determinizarBtn.addActionListener(this);
		gerarGRBtn = new JButton("Gerar GR");
		gerarGRBtn.addActionListener(this);
		salvarBtn = new JButton("Salvar");
		salvarBtn.addActionListener(this);

		operacoesAFPanel = new JPanel();
		operacoesTabelaPanel = new JPanel();
		nomePanel = new JPanel();
		topoPanel = new JPanel(new BorderLayout());
	}

	private void posicioneComponentes() {
		operacoesTabelaPanel.add(adicionarLinhaBtn);
		operacoesTabelaPanel.add(adicionarColunaBtn);
		operacoesTabelaPanel.add(removerLinhaBtn);
		operacoesTabelaPanel.add(removerColunaBtn);
		operacoesAFPanel.add(minimizarBtn);
		operacoesAFPanel.add(determinizarBtn);
		operacoesAFPanel.add(gerarGRBtn);
		operacoesAFPanel.add(salvarBtn);
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
		} else if (e.getSource() == minimizarBtn) {

		} else if (e.getSource() == determinizarBtn) {
			determinizarAutomato();
		} else if (e.getSource() == gerarGRBtn) {

		} else if (e.getSource() == salvarBtn) {
			controller.getPersistencia().salvarComo(this, geraAutomatoDaTabela());
		}
	}

	private void determinizarAutomato() {
		Automato automato = geraAutomatoDaTabela();
		mainView.gerarAutomato(controller.determinizaAutomato(automato));
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
				List<String> transicoesSeparadas = new ArrayList<String>();
				String trans = ((String) modeloTabelaAF.getItens().get(j).get(i)).trim();
				String newTrans = "";
				for (int k = 0; k < trans.length(); k++) {
					if (trans.charAt(k) == 'q') {
						newTrans = String.valueOf(trans.charAt(k));
					} else if (Character.isDigit(trans.charAt(k)) && k < trans.length() - 1 && Character.isDigit(trans.charAt(k + 1))) {
						newTrans += String.valueOf(trans.charAt(k));
					} else if (Character.isDigit(trans.charAt(k)) && k < trans.length() - 1 && !Character.isDigit(trans.charAt(k + 1))) {
						newTrans += String.valueOf(trans.charAt(k));
						transicoesSeparadas.add(newTrans);
					} else if (Character.isDigit(trans.charAt(k)) && k == trans.length() - 1) {
						newTrans += String.valueOf(trans.charAt(k));
						transicoesSeparadas.add(newTrans);
					}
				}
				for (int h = 0; h < transicoesSeparadas.size(); h++) {
					Transicao transicao = new Transicao(modeloTabelaAF.getColunas().get(i).trim().charAt(0), estados.get(transicoesSeparadas.get(h)
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

			if (!typed.equals("") && !typed.matches("^\\[(q)[0-9]+((q)[0-9]+)*\\]|(q)[0-9]+((q)[0-9]+)*$")) {
				JOptionPane.showMessageDialog(null, "O valor deve ser 'q_' ou 'q_q_...', com ou sem [ ]");
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
