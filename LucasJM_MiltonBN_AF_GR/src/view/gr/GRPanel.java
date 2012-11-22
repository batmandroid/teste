package view.gr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import model.GramaticaRegular;
import view.MainView;
import controller.Controller;

public class GRPanel extends JPanel implements ActionListener, TableModelListener {

	private static final long serialVersionUID = 4834066306800198068L;

	Controller controller;
	MainView mainView;
	JButton adicionarLinhaBtn;
	JButton adicionarColunaBtn;
	JButton removerLinhaBtn;
	JButton removerColunaBtn;;
	JButton gerarAFBtn;
	JButton salvarBtn;
	JPanel operacoesTabelaPanel;
	JPanel operacoesGRPanel;
	JScrollPane scrollPane;
	JTable tabelaGR;
	GRTableModel modeloTabelaGR;

	public GRPanel(Controller controller, MainView mainView, String gerarGr) {
		this.controller = controller;
		this.mainView = mainView;
		modeloTabelaGR = new GRTableModel();

		definaComponentes();
		posicioneComponentes();

		this.setBorder(BorderFactory.createTitledBorder("Gramática Regular"));
	}

	private void definaComponentes() {
		tabelaGR = new JTable(modeloTabelaGR);
		tabelaGR.setRowSelectionAllowed(false);
		tabelaGR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaGR.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane = new JScrollPane(tabelaGR);
		scrollPane.setPreferredSize(new Dimension(300, 150));

		ajustaTamanhoColunas();

		adicionarLinhaBtn = new JButton("+ Lin");
		adicionarLinhaBtn.addActionListener(this);
		adicionarColunaBtn = new JButton("+ Col");
		adicionarColunaBtn.addActionListener(this);
		removerLinhaBtn = new JButton("- Lin");
		removerLinhaBtn.addActionListener(this);
		removerColunaBtn = new JButton("- Col");
		removerColunaBtn.addActionListener(this);
		gerarAFBtn = new JButton("Gerar AF");
		gerarAFBtn.addActionListener(this);
		salvarBtn = new JButton("Salvar");
		salvarBtn.addActionListener(this);

		operacoesGRPanel = new JPanel();
		operacoesTabelaPanel = new JPanel();
	}

	private void posicioneComponentes() {
		operacoesTabelaPanel.add(adicionarLinhaBtn);
		operacoesTabelaPanel.add(adicionarColunaBtn);
		operacoesTabelaPanel.add(removerLinhaBtn);
		operacoesTabelaPanel.add(removerColunaBtn);
		operacoesGRPanel.add(gerarAFBtn);
		operacoesGRPanel.add(salvarBtn);

		this.setLayout(new BorderLayout());
		this.add("North", operacoesTabelaPanel);
		this.add("Center", scrollPane);
		this.add("South", operacoesGRPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adicionarLinhaBtn) {
			modeloTabelaGR.adicionarLinha();
		} else if (e.getSource() == adicionarColunaBtn) {
			modeloTabelaGR.adicionarColuna();
			ajustaTamanhoColunas();
		} else if (e.getSource() == removerLinhaBtn) {
			int lin = tabelaGR.getSelectedRow();
			if (lin != -1) {
				modeloTabelaGR.removerLinha(lin);
			}
		} else if (e.getSource() == removerColunaBtn) {
			int col = tabelaGR.getSelectedColumn();
			if (col == 0 || col == 1) {
				JOptionPane.showMessageDialog(null, "Essa coluna não pode ser deletada.");
			} else if (col != -1) {
				modeloTabelaGR.removerColuna(col);
				ajustaTamanhoColunas();
			}
		} else if (e.getSource() == gerarAFBtn) {
			if (validaTabela()) {
				gerarAF();
			}
		} else if (e.getSource() == salvarBtn) {
			controller.getPersistencia().salvarGramaticaRegular(this, geraGramaticaRegularDaTabela());
		}
	}

	private void gerarAF() {
		GramaticaRegular gramatica = geraGramaticaRegularDaTabela();
		// mainView.gerarAF(controller.converteAFtoGR(gramatica));
	}

	private GramaticaRegular geraGramaticaRegularDaTabela() {
		GramaticaRegular gr = new GramaticaRegular();
		List<List<String>> ordenado = new ArrayList<List<String>>();
		List<List<String>> itens = modeloTabelaGR.getItens();

		for (List<String> linha : itens) {
			List<String> novaLinha = new ArrayList<String>();
			for (String itemDaLinha : linha) {
				if (!itemDaLinha.trim().equals("->")) {
					novaLinha.add(itemDaLinha.trim());
				}
			}
			ordenado.add(novaLinha);
		}
		gr.setOrdenado(ordenado);
		return gr;
	}

	private boolean validaTabela() {
		return true;
	}

	private void ajustaTamanhoColunas() {
		TableColumn column = null;
		for (int i = 0; i < tabelaGR.getColumnCount(); i++) {
			column = tabelaGR.getColumnModel().getColumn(i);
			if (i == 1) {
				column.setMaxWidth(20);
			}
		}
	}

	public void setGramatica(GramaticaRegular gramatica) {
		int maior = 0;
		for (int i = 0; i < gramatica.getOrdenado().size(); i++) {
			gramatica.getOrdenado().get(i).add(1, "->");
			if (gramatica.getOrdenado().get(i).size() > maior) {
				maior = gramatica.getOrdenado().get(i).size();
			}
		}

		for (int i = 0; i < gramatica.getOrdenado().size(); i++) {
			int colunasFaltantas = maior - gramatica.getOrdenado().get(i).size();
			while (colunasFaltantas > 0) {
				gramatica.getOrdenado().get(i).add("");
				colunasFaltantas--;
			}
		}

		List<String> colunas = new ArrayList<String>();
		for (int i = 0; i < maior; i++) {
			if (i == 1) {
				colunas.add("->");
			} else {
				colunas.add("");
			}
		}

		modeloTabelaGR.setColunas(colunas);
		modeloTabelaGR.setItens(gramatica.getOrdenado());
		ajustaTamanhoColunas();
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.UPDATE:
			int row = tabelaGR.getSelectedRow();
			int col = tabelaGR.getSelectedColumn();

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
			// String typed = (String) tabelaGR.getValueAt(row, col);
			// typed = typed.trim();
			// if (!typed.equals("") &&
			// !typed.matches("^(q)[0-9]+(,(q)[0-9]+)*$")) {
			// JOptionPane.showMessageDialog(null,
			// "O valor deve ser 'q_', 'q_q_...' ou q_,q_...");
			// tabelaGR.setValueAt(" ", row, col);
			// }
			break;
		}
	}

}
