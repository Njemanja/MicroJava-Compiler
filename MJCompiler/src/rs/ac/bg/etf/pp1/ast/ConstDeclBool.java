// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:34


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclBool extends ConstDecl {

    private String typeName;
    private Boolean b;

    public ConstDeclBool (String typeName, Boolean b) {
        this.typeName=typeName;
        this.b=b;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }

    public Boolean getB() {
        return b;
    }

    public void setB(Boolean b) {
        this.b=b;
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
        buffer.append("ConstDeclBool(\n");

        buffer.append(" "+tab+typeName);
        buffer.append("\n");

        buffer.append(" "+tab+b);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclBool]");
        return buffer.toString();
    }
}
