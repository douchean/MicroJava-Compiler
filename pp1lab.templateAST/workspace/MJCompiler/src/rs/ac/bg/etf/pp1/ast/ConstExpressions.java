// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class ConstExpressions extends ConstExprList {

    private ConstExprList ConstExprList;
    private ConstType ConstType;

    public ConstExpressions (ConstExprList ConstExprList, ConstType ConstType) {
        this.ConstExprList=ConstExprList;
        if(ConstExprList!=null) ConstExprList.setParent(this);
        this.ConstType=ConstType;
        if(ConstType!=null) ConstType.setParent(this);
    }

    public ConstExprList getConstExprList() {
        return ConstExprList;
    }

    public void setConstExprList(ConstExprList ConstExprList) {
        this.ConstExprList=ConstExprList;
    }

    public ConstType getConstType() {
        return ConstType;
    }

    public void setConstType(ConstType ConstType) {
        this.ConstType=ConstType;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstExprList!=null) ConstExprList.accept(visitor);
        if(ConstType!=null) ConstType.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstExprList!=null) ConstExprList.traverseTopDown(visitor);
        if(ConstType!=null) ConstType.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstExprList!=null) ConstExprList.traverseBottomUp(visitor);
        if(ConstType!=null) ConstType.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstExpressions(\n");

        if(ConstExprList!=null)
            buffer.append(ConstExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstType!=null)
            buffer.append(ConstType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstExpressions]");
        return buffer.toString();
    }
}
