// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class VarExpressions extends VarExprList {

    private VarExprList VarExprList;
    private VarExpr VarExpr;

    public VarExpressions (VarExprList VarExprList, VarExpr VarExpr) {
        this.VarExprList=VarExprList;
        if(VarExprList!=null) VarExprList.setParent(this);
        this.VarExpr=VarExpr;
        if(VarExpr!=null) VarExpr.setParent(this);
    }

    public VarExprList getVarExprList() {
        return VarExprList;
    }

    public void setVarExprList(VarExprList VarExprList) {
        this.VarExprList=VarExprList;
    }

    public VarExpr getVarExpr() {
        return VarExpr;
    }

    public void setVarExpr(VarExpr VarExpr) {
        this.VarExpr=VarExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarExprList!=null) VarExprList.accept(visitor);
        if(VarExpr!=null) VarExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarExprList!=null) VarExprList.traverseTopDown(visitor);
        if(VarExpr!=null) VarExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarExprList!=null) VarExprList.traverseBottomUp(visitor);
        if(VarExpr!=null) VarExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarExpressions(\n");

        if(VarExprList!=null)
            buffer.append(VarExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarExpr!=null)
            buffer.append(VarExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarExpressions]");
        return buffer.toString();
    }
}
