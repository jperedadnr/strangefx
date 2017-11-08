package com.gluonhq.strange.ui;

import com.gluonhq.strange.Model;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.stream.Collectors;

public class Circuit extends Control {

    private static final double HEIGHT = 70;

    private Measurement output = new Measurement();
    private int idx; // the number of the qubit
    private ObservableList<GateSymbol> gates = FXCollections.observableArrayList();
    
    private final Model model = Model.getInstance();

    public int getIndex() {
        return this.idx;
    }
    
    public Circuit(int idx) {
        this.idx = idx;
        getStyleClass().add("circuit");
        model.getEndStates().addListener((Observable o)-> {
            double mv = model.getEndStates().get(idx);
            output.setMeasuredChance(mv);
        });
        gates.addListener( (Observable o) -> {
            model.setGatesForCircuit(
                    idx, gates.stream().map(GateSymbol::getGate).collect(Collectors.toList()));
            
//            output.setMeasuredChance(Math.random());
        });

    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CircuitSkin(this);
    }

    public ObservableList<GateSymbol> getGateSymbols() {
        return this.gates;
    }
    
    public Measurement getOutput() {
        return output;
    }
    
    
}
