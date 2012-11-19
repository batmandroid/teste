package af;

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

public class AFPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4834066306800198068L;

	JButton adicionarLinhaBtn;
	JButton adicionarColunaBtn;
	JButton removerLinhaBtn;
	JButton removerColunaBtn;;
	JButton determinizarBtn;
	JButton minimizarBtn;
	JButton gerarGRBtn;
	JPanel operacoesTabelaPanel;
	JPanel operacoesAFPanel;
	JScrollPane scrollPane;
	JTable tabelaAF;
	AFTableModel modeloTabelaAF;

	public AFPanel() {
		modeloTabelaAF = new AFTableModel();

		definaComponentes();
		posicioneComponentes();

		this.setBorder(BorderFactory.createTitledBorder("Autômato Finito"));
	}

	private void definaComponentes() {
		tabelaAF = new JTable(modeloTabelaAF);
		tabelaAF.setRowSelectionAllowed(false);
		tabelaAF.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAF.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane = new JScrollPane(tabelaAF);
		scrollPane.setPreferredSize(new Dimension(300, 150));
		
		TableColumn column = null;
		for (int i = 0; i < tabelaAF.getColumnCount(); i++) {
		    column = tabelaAF.getColumnModel().getColumn(i);
		    if (i == 0 || i == 1) {
		        column.setMaxWidth(20);
		    }
		}

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

		operacoesAFPanel = new JPanel();
		operacoesTabelaPanel = new JPanel();
	}

	private void posicioneComponentes() {
		operacoesTabelaPanel.add(adicionarLinhaBtn);
		operacoesTabelaPanel.add(adicionarColunaBtn);
		operacoesTabelaPanel.add(removerLinhaBtn);
		operacoesTabelaPanel.add(removerColunaBtn);
		operacoesAFPanel.add(minimizarBtn);
		operacoesAFPanel.add(determinizarBtn);
		operacoesAFPanel.add(gerarGRBtn);

		this.setLayout(new BorderLayout());
		this.add("North", operacoesTabelaPanel);
		this.add("Center", scrollPane);
		this.add("South", operacoesAFPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == adicionarLinhaBtn) {
			modeloTabelaAF.adicionarLinha();
		} else if (e.getSource() == adicionarColunaBtn) {
			String input = JOptionPane.showInputDialog(null, "Digite o valor da nova coluna.");
			if (!input.trim().equals("") && input.trim().matches("^([a-z]|[0-9])$")) {
				modeloTabelaAF.adicionarColuna(input);
				TableColumn tabCol = new TableColumn();
				tabCol.setHeaderValue(input);
				tabelaAF.addColumn(tabCol);
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
			}
		} else if (e.getSource() == minimizarBtn) {

		} else if (e.getSource() == determinizarBtn) {

		} else if (e.getSource() == gerarGRBtn) {

		}
	}

}
