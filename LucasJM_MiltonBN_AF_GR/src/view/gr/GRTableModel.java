package view.gr;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class GRTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	List<List<Object>> itens;
	List<String> colunas;

	public GRTableModel() {
		this.itens = new ArrayList<List<Object>>();
		this.colunas = new ArrayList<String>();
		colunas.add("");
		colunas.add("->");
		colunas.add("");
	}

	public void setItens(List<List<Object>> itens) {
		this.itens = itens;
		fireTableDataChanged();
	}

	public List<List<Object>> getItens() {
		return itens;
	}

	public List<String> getColunas() {
		return colunas;
	}

	@Override
	public int getRowCount() {
		return itens.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.size();
	}

	@Override
	public String getColumnName(int i) {
		return colunas.get(i);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<Object> linha = itens.get(rowIndex);
		return linha.get(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public void setValueAt(Object value, int row, int col) {
		List<Object> lin = itens.get(row);
		lin.set(col, value);
		fireTableCellUpdated(row, col);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			return false;
		}
		return true;
	}

	public void adicionarLinha() {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < colunas.size(); i++) {
			if (i == 1) {
				list.add("->");
			} else {
				list.add(" ");
			}
		}
		itens.add(list);
		fireTableDataChanged();
	}

	public void adicionarColuna() {
		colunas.add("");
		for (List<Object> linha : itens) {
			linha.add("");
		}
		fireTableStructureChanged();
	}

	public void removerLinha(int linha) {
		itens.remove(linha);
		fireTableDataChanged();
	}

	public void removerColuna(int coluna) {
		colunas.remove(coluna);
		fireTableStructureChanged();
	}

}
