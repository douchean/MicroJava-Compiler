// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:13


package rs.ac.bg.etf.pp1.ast;

public class ActParameterSingle extends ActPars {

    private ActPar ActPar;

    public ActParameterSingle (ActPar ActPar) {
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
    }

    public ActPar getActPar() {
        return ActPar;
    }

    public void setActPar(ActPar ActPar) {
        this.ActPar=ActPar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPar!=null) ActPar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParameterSingle(\n");

        if(ActPar!=null)
            buffer.append(ActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParameterSingle]");
        return buffer.toString();
    }
}
