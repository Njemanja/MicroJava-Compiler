// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:34

package rs.ac.bg.etf.pp1.ast;

public class DeclarationConst extends Declaration {

	private ConstDeclList ConstDeclList;

	public DeclarationConst(ConstDeclList ConstDeclList) {
		this.ConstDeclList = ConstDeclList;
		if (ConstDeclList != null)
			ConstDeclList.setParent(this);
	}

	public ConstDeclList getConstDeclList() {
		return ConstDeclList;
	}

	public void setConstDeclList(ConstDeclList ConstDeclList) {
		this.ConstDeclList = ConstDeclList;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		if (ConstDeclList != null)
			ConstDeclList.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (ConstDeclList != null)
			ConstDeclList.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		if (ConstDeclList != null)
			ConstDeclList.traverseBottomUp(visitor);
		accept(visitor);
	}

	public String toString(String tab) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tab);
		buffer.append("DeclarationConst(\n");

		if (ConstDeclList != null)
			buffer.append(ConstDeclList.toString("  " + tab));
		else
			buffer.append(tab + "  null");
		buffer.append("\n");

		buffer.append(tab);
		buffer.append(") [DeclarationConst]");
		return buffer.toString();
	}
}
