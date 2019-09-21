// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class FormParsList extends FormPars {

    private FormPars FormPars;
    private TypeBrackets TypeBrackets;

    public FormParsList (FormPars FormPars, TypeBrackets TypeBrackets) {
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.TypeBrackets=TypeBrackets;
        if(TypeBrackets!=null) TypeBrackets.setParent(this);
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
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
        if(FormPars!=null) FormPars.accept(visitor);
        if(TypeBrackets!=null) TypeBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(TypeBrackets!=null) TypeBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(TypeBrackets!=null) TypeBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsList(\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypeBrackets!=null)
            buffer.append(TypeBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsList]");
        return buffer.toString();
    }
}
