// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:35


package rs.ac.bg.etf.pp1.ast;

public class Izmena1 extends DesignatorStatement {

    private Designator Designator;
    private Integer N2;

    public Izmena1 (Designator Designator, Integer N2) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.N2=N2;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Integer getN2() {
        return N2;
    }

    public void setN2(Integer N2) {
        this.N2=N2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Izmena1(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+N2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Izmena1]");
        return buffer.toString();
    }
}
