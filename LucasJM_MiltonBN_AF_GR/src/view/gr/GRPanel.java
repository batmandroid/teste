package view.gr;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import view.MainView;

import controller.Controller;

public class GRPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4834066306800198068L;

	Controller controller;
	MainView mainView;
	JButton adicionarLinhaBtn;
	JButton adicionarColunaBtn;
	JButton removerLinhaBtn;
	JButton removerColunaBtn;;
	JButton gerarAFBtn;
	JPanel operacoesTabelaPanel;
	JPanel operacoesGRPanel;
	JScrollPane scrollPane;
	JTable tabelaGR;
	GRTableModel modeloTabelaGR;

	public GRPanel(Controller controller, MainView mainView) {
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

		adicionarLinhaBtn = new JButton("+ Linha");
		adicionarLinhaBtn.addActionListener(this);
		adicionarColunaBtn = new JButton("+ Coluna");
		adicionarColunaBtn.addActionListener(this);
		removerLinhaBtn = new JButton("- Linha");
		removerLinhaBtn.addActionListener(this);
		removerColunaBtn = new JButton("- Coluna");
		removerColunaBtn.addActionListener(this);
		gerarAFBtn = new JButton("Gerar AF");
		gerarAFBtn.addActionListener(this);

		operacoesGRPanel = new JPanel();
		operacoesTabelaPanel = new JPanel();
	}

	private void posicioneComponentes() {
		operacoesTabelaPanel.add(adicionarLinhaBtn);
		operacoesTabelaPanel.add(adicionarColunaBtn);
		operacoesTabelaPanel.add(removerLinhaBtn);
		operacoesTabelaPanel.add(removerColunaBtn);
		operacoesGRPanel.add(gerarAFBtn);

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
			
		}
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

}
