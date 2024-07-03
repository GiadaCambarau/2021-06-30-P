package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getLocalizations() {
		String sql = "SELECT DISTINCT c.Localization "
				+ "FROM classification c";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("Localization"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Arco> getArchi(){
		String sql = "SELECT c1.Localization as l1, c2.Localization as l2, COUNT(DISTINCT i.`Type`) AS peso "
				+ "FROM classification c1, classification c2, interactions i "
				+ "WHERE c1.Localization > c2.Localization AND  ((i.GeneID1 = c1.GeneID AND i.GeneID2 = c2.GeneID) OR (i.GeneID1 = c2.GeneID AND "
				+ " i.GeneID2 = c1.GeneID)) "
				+ "GROUP BY c1.Localization, c2.Localization";
		List<Arco> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Arco a =new Arco(res.getString("l1"), res.getString("l2"), res.getInt("peso"));
				result.add(a);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	
	
	
	
	

	
//	
//	public List<Arco> getArchi(){
//		String sql = "SELECT LEAST(c1.Localization, c2.Localization) AS l1, GREATEST(c1.Localization, c2.Localization) AS l2, COUNT(DISTINCT i.`Type`) AS peso "
//				+ "FROM interactions i, classification c1, classification c2 "
//				+ "WHERE i.GeneID1 = c1.GeneID AND  i.GeneID2 = c2.GeneID and c1.Localization != c2.Localization "
//				+ "GROUP BY LEAST(c1.Localization, c2.Localization), GREATEST(c1.Localization, c2.Localization)";
//			
//		List<Arco> result = new ArrayList<>();
//		Connection conn = DBConnect.getConnection();
//
//		try {
//			PreparedStatement st = conn.prepareStatement(sql);
//			ResultSet res = st.executeQuery();
//			while (res.next()) {
//				Arco a = new Arco(res.getString("l1"),res.getString("l2"), res.getInt("peso"));
//				result.add(a);
//			}
//			res.close();
//			st.close();
//			conn.close();
//			return result;
//			
//		} catch (SQLException e) {
//			throw new RuntimeException("Database error", e) ;
//		}
//	}
//	


	
}
