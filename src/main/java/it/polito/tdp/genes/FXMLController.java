package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<String> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	if (boxLocalizzazione.getValue()!= null) {
    		String loc = boxLocalizzazione.getValue();
    		txtResult.appendText("Il percorso migliore è: "+"\n");
    		List<String> result = model.trovaPercorso(loc);
    		for (String l: result) {
    			txtResult.appendText(l+ "\n");
    		}
    	}
    }

    @FXML
    void doStatistiche(ActionEvent event) {
    	if (boxLocalizzazione.getValue()!= null) {
    		String loc = boxLocalizzazione.getValue();
    		List<Arco> adiacenti = model.getAdiacenti(loc);
    		txtResult.appendText("adiacenti a "+loc+ ": "+"\n");
    		for (Arco a : adiacenti) {
    			txtResult.appendText(a+"\n");
    		}
    	}
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		model.creaGrafo();
		txtResult.appendText("Vertici: "+model.getV()+"\n");
		txtResult.appendText("Archi: "+ model.getA()+"\n");
		boxLocalizzazione.getItems().addAll(model.getVertici());
	}
}
