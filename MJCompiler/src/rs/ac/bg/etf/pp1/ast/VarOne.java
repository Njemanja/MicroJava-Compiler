// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:34


package rs.ac.bg.etf.pp1.ast;

public class VarOne extends VarDecl {

    private VarItem VarItem;

    public VarOne (VarItem VarItem) {
        this.VarItem=VarItem;
        if(VarItem!=null) VarItem.setParent(this);
    }

    public VarItem getVarItem() {
        return VarItem;
    }

    public void setVarItem(VarItem VarItem) {
        this.VarItem=VarItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarItem!=null) VarItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarItem!=null) VarItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarItem!=null) VarItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarOne(\n");

        if(VarItem!=null)
            buffer.append(VarItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarOne]");
        return buffer.toString();
    }
}
