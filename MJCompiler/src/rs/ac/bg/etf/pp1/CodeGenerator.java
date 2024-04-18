package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	public void visit(PrintStmt printStmt) {

		if (printStmt.getExpr().struct.getKind() == Struct.Array
				&& printStmt.getExpr().struct.getElemType().getKind() == Struct.Int) {
			Code.loadConst(5);
			Code.put(Code.print);

			return;
		}
		if (printStmt.getExpr().struct.getKind() == Struct.Array
				&& printStmt.getExpr().struct.getElemType().getKind() == Struct.Char) {
			Code.loadConst(5);
			Code.put(Code.bprint);

			return;
		}
		if (printStmt.getExpr().struct == Tab.intType) {
			Code.loadConst(5);
			Code.put(Code.print);
			return;
		}

		if (printStmt.getExpr().struct.getKind() == Struct.Array
				&& printStmt.getExpr().struct.getElemType().getKind() == Struct.Bool) {
			Code.loadConst(5);
			Code.put(Code.print);

			return;
		} else if (printStmt.getExpr().struct.getKind() == Struct.Bool) {
			/*
			 * Obj tmp = new Obj(Obj.Var,"dummyObj", new Struct(0),0,1);
			 * 
			 * String greska="   true"; Code.put(Code.const_1); Code.putFalseJump(1,
			 * Code.pc+153);
			 * 
			 * for(int i=0; i<greska.length(); i++) { Character c=greska.charAt(i); Obj con
			 * = Tab.insert(Obj.Con, "$", Tab.charType); con.setLevel(0); con.setAdr(c);
			 * Code.load(con); Code.loadConst(1); Code.put(Code.bprint); }
			 * Code.putJump(Code.pc+153); greska="  false"; for(int i=0; i<greska.length();
			 * i++) { Character c=greska.charAt(i); Obj con = Tab.insert(Obj.Con, "$",
			 * Tab.charType); con.setLevel(0); con.setAdr(c); Code.load(con);
			 * Code.loadConst(1); Code.put(Code.bprint);
			 * 
			 * Code.put(Code.const_1); Code.put(Code.trap) }
			 */

			Code.loadConst(5);
			Code.put(Code.print);
			return;
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	public void visit(FactorNUMBER cnst) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
		con.setLevel(0);
		con.setAdr(cnst.getN1());

		Code.load(con);
	}

	public void visit(FactorChar cnst) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.charType);
		con.setLevel(0);
		con.setAdr(cnst.getC1());

		Code.load(con);
	}

	public void visit(FactorBOOL cnst) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
		con.setLevel(0);
		if (cnst.getB1() == true) {
			con.setAdr(1);
		} else {
			con.setAdr(0);
		}
		Code.load(con);
	}

	public void visit(MethodVoid methodTypeName) {

		mainPc = Code.pc;
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(0);
		Code.put(fpCnt.getCount() + 8);

	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(Assignment assignment) {
		Designator des = assignment.getDesignator();
		if (des instanceof DesignatorExpr && (des.obj.getType().getElemType().getKind() == Struct.Int
				|| des.obj.getType().getElemType().getKind() == Struct.Bool)) {
			Code.put(Code.astore);
			return;
		}
		if (des instanceof DesignatorExpr && des.obj.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.bastore);
			return;
		} else {
			Code.store(assignment.getDesignator().obj);

		}
	}

	public void visit(Var var) {
		Designator des = var.getDesignator();
		if (des instanceof DesignatorExpr && (des.obj.getType().getElemType().getKind() == Struct.Int
				|| des.obj.getType().getElemType().getKind() == Struct.Bool)) {
			Code.put(Code.aload);
			return;
		}
		if (des instanceof DesignatorExpr && des.obj.getType().getElemType().getKind() == Struct.Char) {
			Code.put(Code.baload);
			return;
		}
	}

	public void visit(StatementRead statementRead) {
		Obj num = new Obj(Obj.Var, "num", new Struct(1), 0, 0);

		if (statementRead.getDesignator().obj.getType().getKind() == Struct.Array) {
			Code.put(Code.read);
			Code.put(Code.astore);

		} else {
			Code.put(Code.read);
			Code.store(statementRead.getDesignator().obj);
		}

	}

	public void visit(StatementFindAll statementFindAll) {
		// Obj index = new Obj(Obj.Var,"i", new Struct(1),0,0);
		Obj val = new Obj(Obj.Var, "v", new Struct(1), 1, 1);
		Obj valch = new Obj(Obj.Var, "vch", new Struct(2), 2, 0);
		Obj index = new Obj(Obj.Var, "i", new Struct(1), 0, 1);

		if (statementFindAll.getDesignator1().obj.getType().getElemType().getKind() == Struct.Int) {

			Code.store(val);
			Code.load(statementFindAll.getDesignator1().obj);
			Code.put(Code.arraylength);
			Code.store(index);

			/*
			 * Code.load(index); Code.loadConst(5); Code.put(Code.print);
			 */
			int s = Code.pc;
			// INDEX--;
			Code.load(index);
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.store(index);
			// Arr[INDEX]
			Code.load(statementFindAll.getDesignator1().obj);
			Code.load(index);
			Code.put(Code.aload);
			// ? ARR[INDEX]==VAL
			Code.load(val);
			Code.putFalseJump(1, Code.pc + 13);
			// INDEX==0 ?
			Code.load(index);
			Code.put(Code.const_1);
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.putFalseJump(1, Code.pc + 10);
			Code.putJump(s);
			// TRUE
			Code.put(Code.const_1);
			Code.putJump(Code.pc + 6);
			// FALSE
			Code.put(Code.const_1);
			Code.put(Code.const_1);
			Code.put(Code.sub);

		} else {

			Code.store(valch);

			Code.load(statementFindAll.getDesignator1().obj);
			Code.put(Code.arraylength);
			Code.store(index);
			/*
			 * Code.load(index); Code.loadConst(5); Code.put(Code.print);
			 */
			int s1 = Code.pc;
			// INDEX--;
			Code.load(index);
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.store(index);
			// Arr[INDEX]
			Code.load(statementFindAll.getDesignator1().obj);
			Code.load(index);
			Code.put(Code.baload);
			// ? ARR[INDEX]==VAL
			Code.load(valch);
			Code.putFalseJump(1, Code.pc + 13);
			// INDEX==0 ?
			Code.load(index);
			Code.put(Code.const_1);
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.putFalseJump(1, Code.pc + 10);
			Code.putJump(s1);
			// TRUE
			Code.put(Code.const_1);
			Code.putJump(Code.pc + 6);
			// FALSE
			Code.put(Code.const_1);
			Code.put(Code.const_1);
			Code.put(Code.sub);
		}

		Code.store(statementFindAll.getDesignator().obj);
	}

	public void visit(DesignatorIDENT designator) {

		SyntaxNode parent = designator.getParent();

		if (StatementFindAll.class != parent.getClass()) {
			Code.load(designator.obj);
		}

		// Code.load(designator.obj);
	}

	/*-------------------------ADDOP|MULOP---------------------------*/

	public void visit(DesignatorINC designatorINC) {
		Designator des = designatorINC.getDesignator();
		Obj index = new Obj(Obj.Var, "i", new Struct(1), 0, 0);
		Obj val = new Obj(Obj.Var, "val", new Struct(1), 1, 0);

		Code.store(index);

		if ((des instanceof DesignatorIDENT) && (des.obj.getKind() == Struct.Array)
				&& (des.obj.getType().getElemType().getKind() == Struct.Int)) {
			Code.load(designatorINC.getDesignator().obj);
			Code.load(index);
			Code.put(Code.aload);
			Code.store(val);
			Code.load(val);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(val);

			Code.load(designatorINC.getDesignator().obj);
			Code.load(index);
			Code.load(val);

			Code.put(Code.astore);
		} else {

			Code.load(designatorINC.getDesignator().obj);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(designatorINC.getDesignator().obj);
			return;

			// Code.store(designatorINC.getDesignator().obj);

		}

	}

	public void visit(DesignatorDEC designatorDEC) {
		Designator des = designatorDEC.getDesignator();
		Obj index = new Obj(Obj.Var, "i", new Struct(1), 0, 0);
		Obj val = new Obj(Obj.Var, "val", new Struct(1), 1, 0);

		Code.store(index);

		if ((des instanceof DesignatorIDENT) && (des.obj.getKind() == Struct.Array)
				&& (des.obj.getType().getElemType().getKind() == Struct.Int)) {
			Code.load(designatorDEC.getDesignator().obj);
			Code.load(index);
			Code.put(Code.aload);
			Code.store(val);
			Code.load(val);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(val);

			Code.load(designatorDEC.getDesignator().obj);
			Code.load(index);
			Code.load(val);

			Code.put(Code.astore);
		} else {

			Code.load(designatorDEC.getDesignator().obj);
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.store(designatorDEC.getDesignator().obj);
			return;

			// Code.store(designatorINC.getDesignator().obj);

		}

	}

	public void visit(FactorNEW factorNEW) {

		Code.put(Code.newarray);
		if (factorNEW.getType().struct.getKind() == Struct.Int || factorNEW.getType().struct.getKind() == Struct.Bool) {
			Code.put(1);
		} else {
			Code.put(0);
		}

	}

	public void visit(DesignatorExpr designatorExpr) {
		Obj tmp = new Obj(Obj.Var, "dummyObj", new Struct(0));
		Code.store(tmp);
		Code.load(designatorExpr.obj);
		Code.load(tmp);
	}

	public void visit(AddExpr addExpr) {
		if (addExpr.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	public void visit(TermExprMinus termExprMinus) {
		Code.put(Code.neg);
	}

	public void visit(TermMulop termMulop) {
		if (termMulop.getMulop() instanceof MulopMUL) {
			Code.put(Code.mul);
		} else if (termMulop.getMulop() instanceof MulopDIV) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

}
