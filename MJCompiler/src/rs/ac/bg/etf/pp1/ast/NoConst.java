// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:35


package rs.ac.bg.etf.pp1.ast;

public class NoConst extends PrintConst {

    public NoConst () {
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
        buffer.append("NoConst(\n");

        buffer.append(tab);
        buffer.append(") [NoConst]");
        return buffer.toString();
    }
}
