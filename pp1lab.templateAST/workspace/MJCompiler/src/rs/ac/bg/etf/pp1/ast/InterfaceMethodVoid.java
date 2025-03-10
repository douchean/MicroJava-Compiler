// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class InterfaceMethodVoid extends InterfaceMethodDecl {

    private String methName;
    private FormParsArg FormParsArg;

    public InterfaceMethodVoid (String methName, FormParsArg FormParsArg) {
        this.methName=methName;
        this.FormParsArg=FormParsArg;
        if(FormParsArg!=null) FormParsArg.setParent(this);
    }

    public String getMethName() {
        return methName;
    }

    public void setMethName(String methName) {
        this.methName=methName;
    }

    public FormParsArg getFormParsArg() {
        return FormParsArg;
    }

    public void setFormParsArg(FormParsArg FormParsArg) {
        this.FormParsArg=FormParsArg;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsArg!=null) FormParsArg.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsArg!=null) FormParsArg.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsArg!=null) FormParsArg.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceMethodVoid(\n");

        buffer.append(" "+tab+methName);
        buffer.append("\n");

        if(FormParsArg!=null)
            buffer.append(FormParsArg.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceMethodVoid]");
        return buffer.toString();
    }
}
