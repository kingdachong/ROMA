// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.mit.cci.simulation.web;

import java.lang.Long;
import java.lang.String;
import java.util.Map;

privileged aspect RunSimulationForm_Roo_JavaBean {
    
    public Map<String, String> RunSimulationForm.getInputs() {
        return this.inputs;
    }
    
    public void RunSimulationForm.setInputs(Map<String, String> inputs) {
        this.inputs = inputs;
    }
    
    public Long RunSimulationForm.getSimid() {
        return this.simid;
    }
    
    public void RunSimulationForm.setSimid(Long simid) {
        this.simid = simid;
    }
    
}