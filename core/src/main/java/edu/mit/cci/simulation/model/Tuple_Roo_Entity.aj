// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.mit.cci.simulation.model;

import edu.mit.cci.simulation.impl.Tuple;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import edu.mit.cci.simulation.jaxb.JaxbReference;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Tuple_Roo_Entity {
    
    declare @type: Tuple: @Entity;
    
    @PersistenceContext
    transient EntityManager Tuple.entityManager;
    

    declare @field:* *Tuple.var:@NotNull;
    declare @field:* *Tuple.var:@ManyToOne(targetEntity = DefaultVariable.class, fetch = FetchType.EAGER);

    declare @field:* *Tuple.value_:@Column(columnDefinition = "LONGTEXT");

     declare @field:* *Tuple.id:@Id;
    declare @field:* *Tuple.id:@Column(name = "id");
    declare @field:* *Tuple.id:@GeneratedValue(strategy = GenerationType.AUTO);









    
    @Version
    @Column(name = "version")
    private Integer Tuple.version;

    
    public Integer Tuple.getVersion() {
        return this.version;
    }
    
    public void Tuple.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Tuple.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Tuple.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Tuple attached = Tuple.findTuple(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Tuple.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public Tuple Tuple.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Tuple merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Tuple.entityManager() {
        EntityManager em = new Tuple().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Tuple.countTuples() {
        return entityManager().createQuery("select count(o) from Tuple o", Long.class).getSingleResult();
    }
    
    public static List<Tuple> Tuple.findAllTuples() {
        return entityManager().createQuery("select o from Tuple o", Tuple.class).getResultList();
    }
    
    public static Tuple Tuple.findTuple(Long id) {
        if (id == null) return null;
        return entityManager().find(Tuple.class, id);
    }
    
    public static List<Tuple> Tuple.findTupleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Tuple o", Tuple.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
