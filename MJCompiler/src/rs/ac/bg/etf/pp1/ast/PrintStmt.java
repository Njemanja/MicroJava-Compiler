// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:35

package rs.ac.bg.etf.pp1.ast;

public class PrintStmt extends Matched {

	private Expr Expr;
	private PrintConst PrintConst;

	public PrintStmt(Expr Expr, PrintConst PrintConst) {
		this.Expr = Expr;
		if (Expr != null)
			Expr.setParent(this);
		this.PrintConst = PrintConst;
		if (PrintConst != null)
			PrintConst.setParent(this);
	}

	public Expr getExpr() {
		return Expr;
	}

	public void setExpr(Expr Expr) {
		this.Expr = Expr;
	}

	public PrintConst getPrintConst() {
		return PrintConst;
	}

	public void setPrintConst(PrintConst PrintConst) {
		this.PrintConst = PrintConst;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		if (Expr != null)
			Expr.accept(visitor);
		if (PrintConst != null)
			PrintConst.accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		if (Expr != null)
			Expr.traverseTopDown(visitor);
		if (PrintConst != null)
			PrintConst.traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		if (Expr != null)
			Expr.traverseBottomUp(visitor);
		if (PrintConst != null)
			PrintConst.traverseBottomUp(visitor);
		accept(visitor);
	}

	public String toString(String tab) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(tab);
		buffer.append("PrintStmt(\n");

		if (Expr != null)
			buffer.append(Expr.toString("  " + tab));
		else
			buffer.append(tab + "  null");
		buffer.append("\n");

		if (PrintConst != null)
			buffer.append(PrintConst.toString("  " + tab));
		else
			buffer.append(tab + "  null");
		buffer.append("\n");

		buffer.append(tab);
		buffer.append(") [PrintStmt]");
		return buffer.toString();
	}
}
