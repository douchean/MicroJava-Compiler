// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:12


package rs.ac.bg.etf.pp1.ast;

public class ClassImplementList extends ClassImplementsList {

    private ClassImplementsList ClassImplementsList;
    private Type Type;

    public ClassImplementList (ClassImplementsList ClassImplementsList, Type Type) {
        this.ClassImplementsList=ClassImplementsList;
        if(ClassImplementsList!=null) ClassImplementsList.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
    }

    public ClassImplementsList getClassImplementsList() {
        return ClassImplementsList;
    }

    public void setClassImplementsList(ClassImplementsList ClassImplementsList) {
        this.ClassImplementsList=ClassImplementsList;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassImplementsList!=null) ClassImplementsList.accept(visitor);
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassImplementsList!=null) ClassImplementsList.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassImplementsList!=null) ClassImplementsList.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassImplementList(\n");

        if(ClassImplementsList!=null)
            buffer.append(ClassImplementsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassImplementList]");
        return buffer.toString();
    }
}
