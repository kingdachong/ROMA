package edu.mit.cci.roma.model;


import edu.mit.cci.roma.impl.DefaultSimulation;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * User: jintrone
 * Date: 2/14/11
 * Time: 1:21 PM
 */
@RooJavaBean
@RooToString
@RooEntity
public class Step {

    private Integer order_ = 0;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<DefaultSimulation> simulations = new HashSet<DefaultSimulation>();

    public Step() {
    }

    public Step(Integer order, DefaultSimulation... sims) {
        setOrder_(order);
        for (DefaultSimulation sim : sims) {
            getSimulations().add(sim);
        }
    }

     public Integer getOrder_() {
        return this.order_;
    }

    public void setOrder_(Integer order_) {
        this.order_ = order_;
    }

    public Set<DefaultSimulation> getSimulations() {
        return this.simulations;
    }

    public void setSimulations(Set<DefaultSimulation> simulations) {
        this.simulations = simulations;
    }
}