// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class ConstantCharacter extends ConstType {

    private String var;
    private Character charvalue;

    public ConstantCharacter (String var, Character charvalue) {
        this.var=var;
        this.charvalue=charvalue;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var=var;
    }

    public Character getCharvalue() {
        return charvalue;
    }

    public void setCharvalue(Character charvalue) {
        this.charvalue=charvalue;
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
        buffer.append("ConstantCharacter(\n");

        buffer.append(" "+tab+var);
        buffer.append("\n");

        buffer.append(" "+tab+charvalue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantCharacter]");
        return buffer.toString();
    }
}
