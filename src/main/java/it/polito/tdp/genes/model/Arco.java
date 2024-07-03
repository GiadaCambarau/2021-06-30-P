package it.polito.tdp.genes.model;

import java.util.Objects;

public class Arco {
	private String l1;
	private String l2;
	private int peso;
	public Arco(String l1, String l2, int peso) {
		super();
		this.l1 = l1;
		this.l2 = l2;
		this.peso = peso;
	}
	public String getL1() {
		return l1;
	}
	public void setL1(String l1) {
		this.l1 = l1;
	}
	public String getL2() {
		return l2;
	}
	public void setL2(String l2) {
		this.l2 = l2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(l1, l2, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return Objects.equals(l1, other.l1) && Objects.equals(l2, other.l2) && peso == other.peso;
	}
	@Override
	public String toString() {
		return l2 + "    " + peso ;
	}
	
	

}
