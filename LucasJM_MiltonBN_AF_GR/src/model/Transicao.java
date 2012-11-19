package model;

import java.io.Serializable;


public class Transicao implements Serializable {

	private static final long serialVersionUID = -1470122755255511695L;

    private Character simbolo;
    private Estado estadoDestino;
    
    public Transicao(Character simbolo, Estado estadoDestino ) {
        this.simbolo = simbolo;
        this.estadoDestino = estadoDestino;
    }

    public Estado getEstadoDestino() {
        return estadoDestino;
    }

    public Character getSimbolo() {
        return simbolo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transicao other = (Transicao) obj;
        if (this.simbolo != other.simbolo && (this.simbolo == null || !this.simbolo.equals(other.simbolo))) {
            return false;
        }
        if (this.estadoDestino != other.estadoDestino && (this.estadoDestino == null || !this.estadoDestino.equals(other.estadoDestino))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + ( this.simbolo != null ? this.simbolo.hashCode() : 0 );
        hash = 83 * hash + ( this.estadoDestino != null ? this.estadoDestino.hashCode() : 0 );
        return hash;
    }
    
    @Override
	public String toString() {
		return "Transicao [simbolo=" + simbolo + ", estadoDestino=" + estadoDestino + "]";
	}

}
