package edu.mit.cci.roma.server;

import com.sun.tools.javac.resources.version;
import edu.mit.cci.roma.api.DataType;
import edu.mit.cci.roma.api.Variable;
import edu.mit.cci.roma.impl.DefaultVariable;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * User: jintrone
 * Date: 2/14/13
 * Time: 12:41 PM
 */


@Entity
@Configurable
public class DefaultServerVariable extends DefaultVariable {





    public DefaultServerVariable() {
        super();
    }

     public DefaultServerVariable(String name, String description, Integer arity) {
        super(name,description,arity);
    }

    public DefaultServerVariable(String name, String description, Integer arity, Integer precision, Double min, Double max) {
        super(name,description,arity,precision,min,max);
    }

    @PersistenceContext
    transient EntityManager entityManager;



    private Integer version;


    @Id
     @GeneratedValue(strategy= GenerationType.AUTO)
     @Column(name="id")
    public Long getId() {
        return super.getId();
    }

     @Version
    @Column(name = "version")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(columnDefinition = "LONGTEXT")
    public String getDescription() {
        return super.getDescription();
    }

    @ManyToOne(targetEntity = DefaultServerVariable.class)
    public Variable getIndexingVariable() {
      return super.getIndexingVariable();
    }


    @Enumerated
    public DataType getDataType() {
        return super.getDataType();
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            DefaultVariable attached = findDefaultVariable(this.getId());
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public DefaultVariable merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DefaultVariable merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static final EntityManager entityManager() {
        EntityManager em = new DefaultServerVariable().entityManager;
        if (em == null)
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countDefaultVariables() {
        return entityManager().createQuery("select count(o) from DefaultVariable o", Long.class).getSingleResult();
    }

    public static List<DefaultServerVariable> findAllDefaultVariables() {
        return entityManager().createQuery("select o from DefaultVariable o", DefaultServerVariable.class).getResultList();
    }

    public static DefaultServerVariable findDefaultVariable(Long id) {
        if (id == null) return null;
        return entityManager().find(DefaultServerVariable.class, id);
    }

    public static List<DefaultServerVariable> findDefaultVariableEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from DefaultVariable o", DefaultServerVariable.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }


}
