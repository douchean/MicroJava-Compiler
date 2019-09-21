// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:13


package rs.ac.bg.etf.pp1.ast;

public class FactNewExpr extends Factor {

    private NewTypeArr NewTypeArr;

    public FactNewExpr (NewTypeArr NewTypeArr) {
        this.NewTypeArr=NewTypeArr;
        if(NewTypeArr!=null) NewTypeArr.setParent(this);
    }

    public NewTypeArr getNewTypeArr() {
        return NewTypeArr;
    }

    public void setNewTypeArr(NewTypeArr NewTypeArr) {
        this.NewTypeArr=NewTypeArr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NewTypeArr!=null) NewTypeArr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NewTypeArr!=null) NewTypeArr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NewTypeArr!=null) NewTypeArr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactNewExpr(\n");

        if(NewTypeArr!=null)
            buffer.append(NewTypeArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactNewExpr]");
        return buffer.toString();
    }
}
