package model;

import java.io.Serializable;
import java.util.List;

public class GramaticaRegular implements Serializable {

	private static final long serialVersionUID = -179124786341550970L;

	private List<List<String>> ordenado;

	public List<List<String>> getOrdenado() {
		return ordenado;
	}

	public void setOrdenado(List<List<String>> ordenado) {
		this.ordenado = ordenado;
	}
	
}
