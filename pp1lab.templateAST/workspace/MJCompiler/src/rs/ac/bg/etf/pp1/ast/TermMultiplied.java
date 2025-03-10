// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:13


package rs.ac.bg.etf.pp1.ast;

public class TermMultiplied extends Term {

    private Term Term;
    private Mulop Mulop;
    private Powfact Powfact;

    public TermMultiplied (Term Term, Mulop Mulop, Powfact Powfact) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.Mulop=Mulop;
        if(Mulop!=null) Mulop.setParent(this);
        this.Powfact=Powfact;
        if(Powfact!=null) Powfact.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public Mulop getMulop() {
        return Mulop;
    }

    public void setMulop(Mulop Mulop) {
        this.Mulop=Mulop;
    }

    public Powfact getPowfact() {
        return Powfact;
    }

    public void setPowfact(Powfact Powfact) {
        this.Powfact=Powfact;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(Mulop!=null) Mulop.accept(visitor);
        if(Powfact!=null) Powfact.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(Mulop!=null) Mulop.traverseTopDown(visitor);
        if(Powfact!=null) Powfact.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(Mulop!=null) Mulop.traverseBottomUp(visitor);
        if(Powfact!=null) Powfact.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermMultiplied(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mulop!=null)
            buffer.append(Mulop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Powfact!=null)
            buffer.append(Powfact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermMultiplied]");
        return buffer.toString();
    }
}
