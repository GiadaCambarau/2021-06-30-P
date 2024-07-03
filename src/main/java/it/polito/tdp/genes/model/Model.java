package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private GenesDao dao;
	private List<String> localization;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> best;
	private double nMax;
	
	public Model() {
		this.dao = new GenesDao();
		this.localization = dao.getLocalizations();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public List<String> getLocalization(){
		return this.localization;
		
	}
	
	public void creaGrafo() {
		Graphs.addAllVertices(this.grafo, localization);
		List<Arco> archi = dao.getArchi();
		for (Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getL1(), a.getL2(), a.getPeso());
		}
				
	}
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	public Set<String> getVertici(){
		return this.grafo.vertexSet();
	}
	
	public List<Arco> getAdiacenti(String l){
		List<String> vicini = Graphs.neighborListOf(this.grafo, l);
		List<Arco> adiacenti = new ArrayList<>();
		for (String s: vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(l, s);
			int peso = (int) this.grafo.getEdgeWeight(e);
			adiacenti.add(new Arco(l, s, peso));
		}
		return adiacenti;
	}
	
	
	public List<String> trovaPercorso(String l){
		this.best = new ArrayList<>();
		this.nMax = 0;
		List<String> parziale = new ArrayList<>();
		parziale.add(l);
		
		ricorsione(parziale, l);
		return this.best;
	}

	private void ricorsione(List<String> parziale, String l) {
		String corrente = l;
		List<String> vicini = Graphs.neighborListOf(this.grafo, corrente);
		
		if (calcolaPeso(parziale)>= nMax) {
			this.best = new ArrayList<>(parziale);
			this.nMax = calcolaPeso(parziale);
		}
		for (String s : vicini) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				ricorsione(parziale, s);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
	}

	private double calcolaPeso(List<String> parziale) {
		int peso = 0;
		if (parziale.size()<=1) {
			return peso;
		}
		for (int i=0; i<parziale.size()-1; i++) {
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i), parziale.get(i+1));
			peso += this.grafo.getEdgeWeight(e);
		}
		return peso;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	public void creaGrafo() {
//		Graphs.addAllVertices(this.grafo, this.localization);
//		List<Arco> archi = dao.getArchi();
//		for (Arco a : archi) {
//			Graphs.addEdgeWithVertices(this.grafo, a.getL1(), a.getL2(), a.getPeso());
//		}
//	}
//	
//	
//	public int getV() {
//		return this.grafo.vertexSet().size();
//	}
//	public int getA() {
//		return this.grafo.edgeSet().size();
//	}
//	
//	public Set<String> getVertici(){
//		return this.grafo.vertexSet();
//	}
//
//	public List<Arco> statistiche(String loc) {
//		List<String> vicini = Graphs.neighborListOf(this.grafo, loc); 
//		List<Arco> result = new ArrayList<>();
//		for (String l: vicini) {
//			int peso = (int) this.grafo.getEdgeWeight(this.grafo.getEdge(loc, l));
//			result.add(new Arco (loc, l, peso));
//		}
//		return result;
//	}
//	
////	 A partire dal grafo calcolato al punto precedente, alla pressione del
////	tasto “Ricerca Cammino”, si avvii una procedura di ricerca ricorsiva 
////	per determinare il più lungo cammino semplice di localizzazioni che
////	parta dalla localizzazione selezionata dall’utente. La lunghezza del
////	cammino sarà valutata dalla somma dei pesi degli archi incontrati.
//	
//	public List<String> trovaPercorso(String partenza){
//		this.best = new ArrayList<>();
//		this.nMax = 0;
//		List<String> parziale = new ArrayList<>();
//		parziale.add(partenza);
//		ricorsione(partenza, parziale);
//		return this.best;
//	}
//
//	private void ricorsione(String partenza, List<String> parziale) {
//		 //condizione uscita
//		if (calcolaPeso(parziale)>= this.nMax) {
//			this.best = new ArrayList<>(parziale);
//			this.nMax = calcolaPeso(parziale);
//		}
//		String corrente = partenza;
//		List<String> vicini = Graphs.neighborListOf(this.grafo,partenza );
//		for (String v: vicini) {
//			if (!parziale.contains(v)) {
//				parziale.add(v);
//				ricorsione(v,parziale);
//				parziale.remove(parziale.size()-1);
//			}
//		}
//		
//	}
//
//	private double calcolaPeso(List<String> parziale) {
//		double peso =0;
//		if (parziale.size()<= 1) {
//			return peso;
//		}
//		for (int i =1; i<parziale.size(); i++) {
//			DefaultWeightedEdge d = this.grafo.getEdge(parziale.get(i-1), parziale.get(i));
//			peso += this.grafo.getEdgeWeight(d);
//		}
//		return peso;
//	}

}