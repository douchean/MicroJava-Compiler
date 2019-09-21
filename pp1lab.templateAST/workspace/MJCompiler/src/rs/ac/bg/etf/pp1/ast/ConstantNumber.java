// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class ConstantNumber extends ConstType {

    private String var;
    private Integer numvalue;

    public ConstantNumber (String var, Integer numvalue) {
        this.var=var;
        this.numvalue=numvalue;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var=var;
    }

    public Integer getNumvalue() {
        return numvalue;
    }

    public void setNumvalue(Integer numvalue) {
        this.numvalue=numvalue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstantNumber(\n");

        buffer.append(" "+tab+var);
        buffer.append("\n");

        buffer.append(" "+tab+numvalue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantNumber]");
        return buffer.toString();
    }
}
