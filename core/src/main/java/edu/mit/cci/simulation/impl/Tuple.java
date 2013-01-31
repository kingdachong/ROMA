package edu.mit.cci.simulation.impl;

import edu.mit.cci.simulation.api.*;
import edu.mit.cci.simulation.jaxb.JaxbReference;
import edu.mit.cci.simulation.util.SimulationValidationException;
import edu.mit.cci.simulation.util.U;
import edu.mit.cci.simulation.util.Validation;
import org.apache.log4j.Logger;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.Map;


@XmlRootElement(name = "Tuple")
@XmlAccessorType(XmlAccessType.NONE)
public class Tuple {

    private static Logger log = Logger.getLogger(Tuple.class);

    public Tuple(Variable v) {
        setVar(v);
    }


    @XmlJavaTypeAdapter(JaxbReference.Adapter.class)
    private Variable var;


    @XmlElement(name = "Value")
    private String value_;

    private transient String[] values;

    private transient final Map<Integer, TupleStatus> statuses = new HashMap<Integer, TupleStatus>();

    public Tuple() {

    }



    public String[] getValues() {

        if (values == null) {
            if (value_ == null) return null;
            else {
                this.statuses.clear();
                values = U.unescape(value_, this.statuses, var.getDataType() == DataType.NUM ? var.getPrecision_() : null);
            }
        }
        return values;
    }

    public void setValues(String[] values) throws SimulationValidationException {
        this.statuses.clear();
        _setValues(values);
    }

    private void _setValues(String[] values) throws SimulationValidationException {
        Validation.isComplete(var);
        Validation.atMostArity(var, values.length);
        for (int i = 0; i < values.length; i++) {
            Validation.checkDataType(var, values[i], true);
            if (values[i] == null) continue;
            TupleStatus status = statuses.get(i);
            if (status != null) continue;
            U.process(var, i, values, statuses);
        }
        this.values = values;
        this.value_ = (U.escape(values, statuses));
    }

    public void setValue_(String val) throws SimulationValidationException {
        this.statuses.clear();
        _setValues(U.unescape(val, this.statuses, null));
    }


    public TupleStatus getStatus(int i) {
        return statuses.get(i);
    }

    public void setStatus(int i, TupleStatus status) {
        TupleStatus current = getStatus(i);
        if (current != status) {
            statuses.put(i, status);
            value_ = U.updateEscapedArray(i, value_, status);
            values[i] = null;
        }
    }


    public static void main(String[] args) {
        String[] vals = new String[]{"hi;;", "ad%%253Bd;;", "nothing;;;"};
        String encoded = U.escape(vals, null);
        System.err.println(encoded);

        vals = U.unescape(encoded, null, null);
        for (String val : vals) {
            System.err.println(val);
        }

    }


    public String getId_() {
        return "" + getId();
    }

    public static Tuple copy(Tuple t) {
        Tuple result = new Tuple();
        result.setVar(t.getVar());
        try {
            result.setValue_(t.getValue_());
        } catch (SimulationValidationException e) {
            throw new RuntimeException("Encountered an invalid tuple on copy", e);
        }

        result.persist();
        return result;
    }


    private Long id;


    @XmlAttribute
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Variable getVar() {
           return this.var;
       }

       public void setVar(Variable var) {
           this.var = var;
       }

       public String getValue_() {
           return this.value_;
       }


}
