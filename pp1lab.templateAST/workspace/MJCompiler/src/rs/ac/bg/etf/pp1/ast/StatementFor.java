// generated with ast extension for cup
// version 0.8
// 22/5/2019 0:52:13


package rs.ac.bg.etf.pp1.ast;

public class StatementFor extends Statement {

    private OptionalDesignatorStatement OptionalDesignatorStatement;
    private Sammy Sammy;
    private OptionalCondition OptionalCondition;
    private OptionalDesignatorStatement OptionalDesignatorStatement1;
    private RePare RePare;
    private Statement Statement;

    public StatementFor (OptionalDesignatorStatement OptionalDesignatorStatement, Sammy Sammy, OptionalCondition OptionalCondition, OptionalDesignatorStatement OptionalDesignatorStatement1, RePare RePare, Statement Statement) {
        this.OptionalDesignatorStatement=OptionalDesignatorStatement;
        if(OptionalDesignatorStatement!=null) OptionalDesignatorStatement.setParent(this);
        this.Sammy=Sammy;
        if(Sammy!=null) Sammy.setParent(this);
        this.OptionalCondition=OptionalCondition;
        if(OptionalCondition!=null) OptionalCondition.setParent(this);
        this.OptionalDesignatorStatement1=OptionalDesignatorStatement1;
        if(OptionalDesignatorStatement1!=null) OptionalDesignatorStatement1.setParent(this);
        this.RePare=RePare;
        if(RePare!=null) RePare.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public OptionalDesignatorStatement getOptionalDesignatorStatement() {
        return OptionalDesignatorStatement;
    }

    public void setOptionalDesignatorStatement(OptionalDesignatorStatement OptionalDesignatorStatement) {
        this.OptionalDesignatorStatement=OptionalDesignatorStatement;
    }

    public Sammy getSammy() {
        return Sammy;
    }

    public void setSammy(Sammy Sammy) {
        this.Sammy=Sammy;
    }

    public OptionalCondition getOptionalCondition() {
        return OptionalCondition;
    }

    public void setOptionalCondition(OptionalCondition OptionalCondition) {
        this.OptionalCondition=OptionalCondition;
    }

    public OptionalDesignatorStatement getOptionalDesignatorStatement1() {
        return OptionalDesignatorStatement1;
    }

    public void setOptionalDesignatorStatement1(OptionalDesignatorStatement OptionalDesignatorStatement1) {
        this.OptionalDesignatorStatement1=OptionalDesignatorStatement1;
    }

    public RePare getRePare() {
        return RePare;
    }

    public void setRePare(RePare RePare) {
        this.RePare=RePare;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalDesignatorStatement!=null) OptionalDesignatorStatement.accept(visitor);
        if(Sammy!=null) Sammy.accept(visitor);
        if(OptionalCondition!=null) OptionalCondition.accept(visitor);
        if(OptionalDesignatorStatement1!=null) OptionalDesignatorStatement1.accept(visitor);
        if(RePare!=null) RePare.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalDesignatorStatement!=null) OptionalDesignatorStatement.traverseTopDown(visitor);
        if(Sammy!=null) Sammy.traverseTopDown(visitor);
        if(OptionalCondition!=null) OptionalCondition.traverseTopDown(visitor);
        if(OptionalDesignatorStatement1!=null) OptionalDesignatorStatement1.traverseTopDown(visitor);
        if(RePare!=null) RePare.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalDesignatorStatement!=null) OptionalDesignatorStatement.traverseBottomUp(visitor);
        if(Sammy!=null) Sammy.traverseBottomUp(visitor);
        if(OptionalCondition!=null) OptionalCondition.traverseBottomUp(visitor);
        if(OptionalDesignatorStatement1!=null) OptionalDesignatorStatement1.traverseBottomUp(visitor);
        if(RePare!=null) RePare.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementFor(\n");

        if(OptionalDesignatorStatement!=null)
            buffer.append(OptionalDesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Sammy!=null)
            buffer.append(Sammy.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalCondition!=null)
            buffer.append(OptionalCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalDesignatorStatement1!=null)
            buffer.append(OptionalDesignatorStatement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RePare!=null)
            buffer.append(RePare.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementFor]");
        return buffer.toString();
    }
}
