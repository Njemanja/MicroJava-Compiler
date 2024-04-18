// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:34


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclNumber extends ConstDecl {

    private String typeName;
    private Integer N1;

    public ConstDeclNumber (String typeName, Integer N1) {
        this.typeName=typeName;
        this.N1=N1;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName=typeName;
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
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
        buffer.append("ConstDeclNumber(\n");

        buffer.append(" "+tab+typeName);
        buffer.append("\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclNumber]");
        return buffer.toString();
    }
}
