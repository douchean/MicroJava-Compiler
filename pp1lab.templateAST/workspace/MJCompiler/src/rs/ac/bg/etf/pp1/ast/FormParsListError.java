// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class FormParsListError extends FormPars {

    private TypeBrackets TypeBrackets;

    public FormParsListError (TypeBrackets TypeBrackets) {
        this.TypeBrackets=TypeBrackets;
        if(TypeBrackets!=null) TypeBrackets.setParent(this);
    }

    public TypeBrackets getTypeBrackets() {
        return TypeBrackets;
    }

    public void setTypeBrackets(TypeBrackets TypeBrackets) {
        this.TypeBrackets=TypeBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypeBrackets!=null) TypeBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeBrackets!=null) TypeBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeBrackets!=null) TypeBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsListError(\n");

        if(TypeBrackets!=null)
            buffer.append(TypeBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsListError]");
        return buffer.toString();
    }
}
