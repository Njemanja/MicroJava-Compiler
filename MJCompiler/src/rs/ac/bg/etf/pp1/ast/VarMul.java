// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:34


package rs.ac.bg.etf.pp1.ast;

public class VarMul extends VarDecl {

    private VarItem VarItem;
    private VarDecl VarDecl;

    public VarMul (VarItem VarItem, VarDecl VarDecl) {
        this.VarItem=VarItem;
        if(VarItem!=null) VarItem.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarItem getVarItem() {
        return VarItem;
    }

    public void setVarItem(VarItem VarItem) {
        this.VarItem=VarItem;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarItem!=null) VarItem.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarItem!=null) VarItem.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarItem!=null) VarItem.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarMul(\n");

        if(VarItem!=null)
            buffer.append(VarItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarMul]");
        return buffer.toString();
    }
}
