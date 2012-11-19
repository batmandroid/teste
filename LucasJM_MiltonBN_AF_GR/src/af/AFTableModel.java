package af;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AFTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	List<List<Object>> itens;
	List<String> colunas;

	public AFTableModel() {
		this.itens = new ArrayList<List<Object>>();
		this.colunas = new ArrayList<String>();
		colunas.add("->");
		colunas.add("*");
		colunas.add("Q");
	}

	 public void setValores(List<String> colunas, List<List<Object>> itens) {
		 this.colunas = colunas;
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
        fireTableDataChanged();
    }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void adicionarLinha() {
		List<Object> list = new ArrayList<Object>();
		list.add(new Boolean(false));
		list.add(new Boolean(false));
		for (int i = 2; i < colunas.size(); i++) {
			list.add(" ");
		}
		itens.add(list);
		fireTableDataChanged();
	}

	public void adicionarColuna(String valor) {
		colunas.add(valor);
		for (List<Object> linha : itens) {
			linha.add("");
		}
	}

	public void removerLinha(int linha) {
		itens.remove(linha);
		fireTableRowsDeleted(linha, linha);
	}

	public void removerColuna(int coluna) {
		colunas.remove(coluna);
	}

}
