// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class Implement extends Implementation {

    private ClassImplementsList ClassImplementsList;

    public Implement (ClassImplementsList ClassImplementsList) {
        this.ClassImplementsList=ClassImplementsList;
        if(ClassImplementsList!=null) ClassImplementsList.setParent(this);
    }

    public ClassImplementsList getClassImplementsList() {
        return ClassImplementsList;
    }

    public void setClassImplementsList(ClassImplementsList ClassImplementsList) {
        this.ClassImplementsList=ClassImplementsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassImplementsList!=null) ClassImplementsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassImplementsList!=null) ClassImplementsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassImplementsList!=null) ClassImplementsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Implement(\n");

        if(ClassImplementsList!=null)
            buffer.append(ClassImplementsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Implement]");
        return buffer.toString();
    }
}
