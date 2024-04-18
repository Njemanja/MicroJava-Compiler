// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:34


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclChar extends ConstDecl {

    private String typeName;
    private Character C1;

    public ConstDeclChar (String typeName, Character C1) {
        this.typeName=typeName;
        this.C1=C1;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }

    public Character getC1() {
        return C1;
    }

    public void setC1(Character C1) {
        this.C1=C1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclChar(\n");

        buffer.append(" "+tab+typeName);
        buffer.append("\n");

        buffer.append(" "+tab+C1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclChar]");
        return buffer.toString();
    }
}
