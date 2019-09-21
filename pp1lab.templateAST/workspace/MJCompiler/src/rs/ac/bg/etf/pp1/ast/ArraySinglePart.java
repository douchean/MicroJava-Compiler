// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:13


package rs.ac.bg.etf.pp1.ast;

public class ArraySinglePart extends ArrayParts {

    private Emptiness Emptiness;
    private ArrPart ArrPart;

    public ArraySinglePart (Emptiness Emptiness, ArrPart ArrPart) {
        this.Emptiness=Emptiness;
        if(Emptiness!=null) Emptiness.setParent(this);
        this.ArrPart=ArrPart;
        if(ArrPart!=null) ArrPart.setParent(this);
    }

    public Emptiness getEmptiness() {
        return Emptiness;
    }

    public void setEmptiness(Emptiness Emptiness) {
        this.Emptiness=Emptiness;
    }

    public ArrPart getArrPart() {
        return ArrPart;
    }

    public void setArrPart(ArrPart ArrPart) {
        this.ArrPart=ArrPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Emptiness!=null) Emptiness.accept(visitor);
        if(ArrPart!=null) ArrPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Emptiness!=null) Emptiness.traverseTopDown(visitor);
        if(ArrPart!=null) ArrPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Emptiness!=null) Emptiness.traverseBottomUp(visitor);
        if(ArrPart!=null) ArrPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArraySinglePart(\n");

        if(Emptiness!=null)
            buffer.append(Emptiness.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ArrPart!=null)
            buffer.append(ArrPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArraySinglePart]");
        return buffer.toString();
    }
}
