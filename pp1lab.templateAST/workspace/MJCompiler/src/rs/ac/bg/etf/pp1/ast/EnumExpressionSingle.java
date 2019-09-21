// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class EnumExpressionSingle extends EnumExprList {

    private EnumExpr EnumExpr;

    public EnumExpressionSingle (EnumExpr EnumExpr) {
        this.EnumExpr=EnumExpr;
        if(EnumExpr!=null) EnumExpr.setParent(this);
    }

    public EnumExpr getEnumExpr() {
        return EnumExpr;
    }

    public void setEnumExpr(EnumExpr EnumExpr) {
        this.EnumExpr=EnumExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumExpr!=null) EnumExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumExpr!=null) EnumExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumExpr!=null) EnumExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumExpressionSingle(\n");

        if(EnumExpr!=null)
            buffer.append(EnumExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumExpressionSingle]");
        return buffer.toString();
    }
}
