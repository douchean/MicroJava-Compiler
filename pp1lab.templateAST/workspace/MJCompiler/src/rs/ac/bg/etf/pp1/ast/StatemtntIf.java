// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class StatemtntIf extends Statement {

    private BasicIf BasicIf;

    public StatemtntIf (BasicIf BasicIf) {
        this.BasicIf=BasicIf;
        if(BasicIf!=null) BasicIf.setParent(this);
    }

    public BasicIf getBasicIf() {
        return BasicIf;
    }

    public void setBasicIf(BasicIf BasicIf) {
        this.BasicIf=BasicIf;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(BasicIf!=null) BasicIf.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(BasicIf!=null) BasicIf.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(BasicIf!=null) BasicIf.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatemtntIf(\n");

        if(BasicIf!=null)
            buffer.append(BasicIf.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatemtntIf]");
        return buffer.toString();
    }
}
