package rs.ac.bg.etf.pp1;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	int varDeclCount=0;
	Obj currentMethod = null;
	boolean returnFound = false;
	int nVars;
	boolean arrOrElse=false;
	boolean find=false;
	Struct tmptype= Tab.noType;
	String tmpName;
	Logger log = Logger.getLogger(getClass());
    
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
		
	}
	
	
	
/*------------------------------------Program--------------------------------------------*/


	public void visit(Program program) {		
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);
		Tab.openScope();

	}	

	
/*------------------------------------DeklaracijaVar--------------------------------------------*/

	
	public void visit(DeclarationL v) {}
	public void visit(DeclarationVar v) {}
	public void visit(VarDeclarations varDeclarations) {
		tmptype=varDeclarations.getType().struct;	
	}
	public void visit(VarOne v) {}
	public void visit(VarMul v) {  }
	public void visit(VarItemIdent varItemIdent){
		tmpName=varItemIdent.getVarName();
		Obj o=Tab.currentScope().findSymbol(varItemIdent.getVarName());
		if(o==null) {
			Tab.insert(Obj.Var, tmpName,tmptype);
			report_info("Deklarisana promenljiva "+ tmpName, null);
			varDeclCount++;
		}else {
			report_error("GRESKA: Promenljiva "+ tmpName +" je vec deklarisana!", null);
			
		}
	}
	public void visit(VarItemArr varItemArr){
		tmpName=varItemArr.getVarName();
		Obj o=Tab.currentScope().findSymbol(varItemArr.getVarName());
		if(o==null) {
			report_info("Deklarisan je niz "+ tmpName, null);
			varDeclCount++;
			Tab.insert(Obj.Var, tmpName, new Struct(Struct.Array,  tmptype));
		}else {
			report_error("GRESKA: Ime niza "+ tmpName +" je vec u upotrebi!", null);
			
		}
	}
	
	
/*------------------------------------CONST--------------------------------------------*/
	
	
	
	public void visit(ConstDeclarations constDeclarations) {
		tmptype=constDeclarations.getType().struct;
	}
	public void visit(ConstDeclNumber constDeclNumber){
		tmpName=constDeclNumber.getTypeName();
		int adr=constDeclNumber.getN1();
		Obj o=Tab.currentScope().findSymbol(tmpName);
		if(tmptype != Tab.intType) {
			report_error("GRESKA: nekompatiblni tipovi int i "+ tmptype.getKind(), null);
			
			return;}
		if(o==null) {
			Obj obj=Tab.insert(Obj.Con, tmpName, tmptype);
			obj.setAdr(adr);
			report_info("Deklarisana je konsanta int "+ tmpName+" = "+adr, null);
			
		}else {
			report_error("GRESKA (int): Ime konstante "+ tmpName +" je vec u upotrebi!", null);
			
		}
	}
	public void visit(ConstDeclChar constDeclChar){
		tmpName=constDeclChar.getTypeName();
		int adr=constDeclChar.getC1();
		Obj o=Tab.currentScope().findSymbol(tmpName);
		if(tmptype != Tab.charType) {
			report_error("GRESKA: nekompatiblni tipovi char i "+ tmptype.getKind(), null);
			
			return;}
		if(o==null) {
			Obj obj=Tab.insert(Obj.Con, tmpName, tmptype);
			obj.setAdr(adr);
			report_info("Deklarisana je konsanta char "+ tmpName+" = "+ (adr), constDeclChar);
			
		}else {
			report_error("GRESKA (char): Ime konstante "+ tmpName +" je vec u upotrebi!", null);
			
		}
	}
	public void visit(ConstDeclBool constDeclBool){
		tmpName=constDeclBool.getTypeName();
		int adr=0;
		Boolean b=constDeclBool.getB();
		if(b) {adr=1;}
		Obj o=Tab.currentScope().findSymbol(tmpName);
		if(tmptype.getKind() != 5) {
			report_error("GRESKA: nekompatiblni tipovi boolean i "+ tmptype.getKind(), null);
			
			return;}
		if(o==null) {
			Obj obj=Tab.insert(Obj.Con, tmpName, tmptype);
			obj.setAdr(adr);
			report_info("Deklarisana je konsanta boolean "+ tmpName+" = "+adr, null);

		}else {
			report_error("GRESKA (bool): Ime konstante "+ tmpName +" je vec u upotrebi!", null);
			
		}
	}


/*------------------------------------Metode--------------------------------------------*/	
	

	public void visit(MethodVoid methodTypeName) {
		currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), Tab.noType);
		methodTypeName.obj = currentMethod;
		Tab.openScope();
		
		if(!methodTypeName.getMethName().equals("main")) {
			report_error("GRESKA: Funkcija mora biti main!", methodTypeName);
			
		}else {
			report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);

		}
	}
	public void visit(MethodDecl methodDecl) {
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		if(methodDecl.getDeclarationList() == null) {
			report_error("GRESKA: Main ne treba da ima parametre!", methodDecl);

		}
		currentMethod = null;
	}	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} 
			else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
			tmptype = type.struct;

		}  
	}

	
/*------------------------------STATEMENTS---------------------------------------------*/
	
	
	public void visit(Assignment assignment) {
		
	
		
		
		if(assignment.getDesignator().obj.getKind()==Obj.Con) {
			report_error("Greska na liniji " + assignment.getLine() + " : " + " nije moguce dodeliti vrednost konstanti.", null);
			return;
		}
		
		
		
			
		
		if(assignment.getDesignator().obj.getType().getKind()==Struct.Array) {
			if(assignment.getDesignator().obj.getKind()!=Struct.Int 
					
					) {
				report_error("Greska na liniji " + assignment.getLine() + " : " + " u [ ] treba da bude INT.", null);
				return;
			}
			
			
			if(assignment.getExpr().struct.getKind()== Struct.Array 
					&& assignment.getDesignator().obj.getType().getElemType().getKind() != assignment.getExpr().struct.getElemType().getKind()) {
				report_error("Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi.", null);
				return;
			}
			
			if(assignment.getExpr().struct.getKind()!= Struct.Array && assignment.getExpr().struct.getKind() != assignment.getDesignator().obj.getType().getElemType().getKind() ) {
				report_error("Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi.", null);
				return;
			}
			
			
			
		}
		else if(assignment.getDesignator().obj.getType().getKind()==Struct.Int 
				&& assignment.getExpr().struct.getKind()== Struct.Array 
				&& assignment.getExpr().struct.getElemType().getKind() != assignment.getDesignator().obj.getKind() ) {
			report_error("Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi.", null);
			return;
		}
		else {
			if((assignment.getDesignator().obj.getType().getKind() != assignment.getExpr().struct.getKind()) && assignment.getExpr().struct.getKind() != Struct.Array ) {
				report_error("Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi.", null);
				return;
			}
		}
		
		
		
		
		
		
		
		

	}
	public void visit(StatementRead statementRead){
		Obj s=statementRead.getDesignator().obj;
		if(s==Tab.noObj) {
			report_error("Greska na liniji " + statementRead.getLine() + " : " + "ne moze da se izvrsi read.", null);
			return;
		}
		if(s.getKind() == Obj.Con) {
			report_error("Greska na liniji " + statementRead.getLine() + " : " + " los parametar za funckiju read (konstanta se ne moze menjati).", null);
			return;
		}
		if(arrOrElse) {
			report_error("Greska na liniji " + statementRead.getLine() + " : " + " los parametar za funckiju read (ime niza se ne moze koristit).", null);
			return;
		}
		
		
	}
	public void visit(StatementFindAll statementFindAll){
		if(statementFindAll.getDesignator().obj.getKind() == Obj.Con) {
			report_error("Greska na liniji " + statementFindAll.getLine() + " : " + " leva strana kod findAny mora da bude promenljiva.", null);
			return;
		}
		if(statementFindAll.getDesignator().obj.getType().getKind() == Struct.Array) {
			if(statementFindAll.getDesignator().obj.getType().getElemType().getKind() != Struct.Bool) {
				report_error("Greska na liniji " + statementFindAll.getLine() + " : " + " leva strana kod findAny mora da bude bool.", null);
				return;
			}
		}else {
			if(statementFindAll.getDesignator().obj.getType().getKind() != Struct.Bool) {
				report_error("Greska na liniji " + statementFindAll.getLine() + " : " + " leva strana kod findAny mora da bude bool.", null);
				return;
			}
			
		}
		if(statementFindAll.getDesignator1().obj.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji " + statementFindAll.getLine() + " : " + " niz mora da se koristi objekat kod findAny.", null);
			return;
		}
		if(find) {
			report_error("Greska na liniji " + statementFindAll.getLine() + " : " + " niz mora da se koristi objekat kod findAny.", null);
			return;
		}
		
		
		
		
	}
	public void visit(PrintStmt printStmt){
		if(arrOrElse) {
			report_error("Greska na liniji " + printStmt.getLine() + " : " + " nije moguce isprintovati niz.", null);
			
		}else if(printStmt.getExpr().struct.getKind()!= Struct.Int && 
				printStmt.getExpr().struct.getKind() != Struct.Bool &&
				printStmt.getExpr().struct.getKind()!= Struct.Char &&
				printStmt.getExpr().struct.getKind()!= Struct.Array) {
			report_error("Greska na liniji " + printStmt.getLine() + " : " + " nije moguce isprintovati.", null);
			
		}
		printCallCount++;    	
	}
	

	

	
	public void visit(DesignatorINC designatorINC) {
		if(arrOrElse) {
			report_error("Greska na liniji " + designatorINC.getLine() + " : " + " nemoguce inkrementiranje.", null);
			return;
		}
		if(designatorINC.getDesignator().obj.getKind()!= Obj.Elem &&
		   designatorINC.getDesignator().obj.getKind()!= Obj.Var
				
				) {
			report_error("Greska na liniji " + designatorINC.getLine() + " : " + " levi operand nije promenljiva.", null);
			return;
		}
		if(designatorINC.getDesignator().obj.getType()!= Tab.intType) {
			if(!(designatorINC.getDesignator().obj.getType().getKind()==Struct.Array &&
					designatorINC.getDesignator().obj.getType().getElemType().getKind() == Struct.Int)) {
				report_error("Greska na liniji " + designatorINC.getLine() + " : " + " inkrement mora ici uz int.", null);

			}
		}
	}
	public void visit(DesignatorDEC designatorDEC) {
		if(arrOrElse) {
			report_error("Greska na liniji " + designatorDEC.getLine() + " : " + " nemoguce inkrementiranje.", null);
			return;
		}
		if(designatorDEC.getDesignator().obj.getKind()!= Obj.Elem &&
				designatorDEC.getDesignator().obj.getKind()!= Obj.Var
				
				) {
			report_error("Greska na liniji " + designatorDEC.getLine() + " : " + " levi operand nije promenljiva.", null);
			return;
		}
		if(designatorDEC.getDesignator().obj.getType()!= Tab.intType) {
			if(!(designatorDEC.getDesignator().obj.getType().getKind()==Struct.Array &&
					designatorDEC.getDesignator().obj.getType().getElemType().getKind() == Struct.Int)) {
				report_error("Greska na liniji " + designatorDEC.getLine() + " : " + " inkrement mora ici uz int.", null);

			}
		}
	}
	
	
/*------------------------------EXPR---------------------------------------------*/
	
	
	public void visit(AddExpr addExpr) {
		Struct te = addExpr.getExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if (te.equals(t) && te == Tab.intType)
			addExpr.struct = te;
		else {
			report_error("Greska na liniji "+ addExpr.getLine()+" : nekompatibilni tipovi u izrazu za sabiranje/oduzimanje.", null);
			addExpr.struct = Tab.noType;
		} 
	}	
	public void visit(TermExprMinus termExprMinus) {
		Struct te = termExprMinus.getTerm().struct;
		if ( te == Tab.intType) {
			termExprMinus.struct = te;
			report_info("Izvrsena negacija ", termExprMinus);
		}
		else {
			report_error("Greska na liniji "+ termExprMinus.getLine()+" : nekompatibilni tipovi u izrazu za negaciju.", null);
			termExprMinus.struct = Tab.noType;
		} 
	}
	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
		//report_info(""+termExp,null);
	}
	public void visit(TermFactor term) {
		term.struct = term.getFactor().struct;    	
	}
	public void visit(TermMulop termMulop) {
		
		
		
		Struct te = termMulop.getFactor().struct;
		Struct t = termMulop.getTerm().struct;
		

		if(termMulop.getFactor().struct.getKind() == Struct. Array		
		&& termMulop.getTerm().struct.getKind() == Struct. Array
		&& termMulop.getFactor().struct.getElemType().getKind() != termMulop.getTerm().struct.getElemType().getKind() 
		) {
			termMulop.struct=Tab.noType;
			report_error("Greska na liniji "+ termMulop.getLine()+" : nekompatibilni tipovi u izrazu za mnozenje/deljenje/deljenje sa ostatkom.", null);
			return;
		}
		
		if(termMulop.getFactor().struct.getKind() != Struct. Array		
		&& termMulop.getTerm().struct.getKind() == Struct. Array
		&& termMulop.getFactor().struct.getKind() != termMulop.getTerm().struct.getElemType().getKind() 
		) {
			termMulop.struct=Tab.noType;
			report_error("Greska na liniji "+ termMulop.getLine()+" : nekompatibilni tipovi u izrazu za mnozenje/deljenje/deljenje sa ostatkom.", null);
			return;
		}
		if(termMulop.getFactor().struct.getKind() == Struct. Array		
		&& termMulop.getTerm().struct.getKind() != Struct. Array
		&& termMulop.getFactor().struct.getElemType().getKind() != termMulop.getTerm().struct.getKind() 
		) {
			termMulop.struct=Tab.noType;
			report_error("Greska na liniji "+ termMulop.getLine()+" : nekompatibilni tipovi u izrazu za mnozenje/deljenje/deljenje sa ostatkom.", null);
			return;
		}
		
		if(termMulop.getFactor().struct.getKind() != Struct. Array		
		&& termMulop.getTerm().struct.getKind() != Struct. Array
		&& termMulop.getFactor().struct.getKind() != termMulop.getTerm().struct.getKind() 
		) {
			termMulop.struct=Tab.noType;
			report_error("Greska na liniji "+ termMulop.getLine()+" : nekompatibilni tipovi u izrazu za mnozenje/deljenje/deljenje sa ostatkom.", null);
			return;
		}
		termMulop.struct=Tab.intType;
		
	}

	
/*------------------------------FACTOR---------------------------------------------*/
	
	public void visit(FactorNUMBER factorNUMBER) {
		factorNUMBER.struct=Tab.intType;
		arrOrElse=false;

	}
	public void visit(FactorChar factorChar) {
		factorChar.struct=Tab.charType;
		arrOrElse=false;

	}
	public void visit(FactorExpr factorExpr) {
		factorExpr.struct=factorExpr.getExpr().struct;
		arrOrElse=false;

	}
	public void visit(FactorBOOL factorBOOL) {
		factorBOOL.struct=new Struct(Struct.Bool);
		arrOrElse=false;

	}
	public void visit(FactorNEW factorNEW) {	
		Struct s=factorNEW.getType().struct;
		if(s.getKind()==Struct.Bool) {
			factorNEW.struct=new Struct(Struct.Array,  factorNEW.getType().struct);
			varDeclCount++;
			report_info("Deklarisana je niz (uz pomoc NEW)", factorNEW);
			Expr e=factorNEW.getExpr();
			return;
		}
		
		if(factorNEW.getType().struct == Tab.intType
				|| factorNEW.getType().struct == Tab.charType) {
			Type t=factorNEW.getType();
			factorNEW.struct=new Struct(Struct.Array,  factorNEW.getType().struct);
			varDeclCount++;
			report_info("Deklarisana je niz (uz pomoc NEW)", factorNEW);
			Expr e=factorNEW.getExpr();
			
		}else {
			report_error("Greska na liniji "+ factorNEW.getLine()+" : funkcija NEW mora da ide uz INT.", null);
			factorNEW.struct = Tab.noType;
		}
		
		
	}
	public void visit(Var var){
    	var.struct = var.getDesignator().obj.getType();
    }
	
	
	
/*------------------------------DESIGNATOR---------------------------------------------*/
	
	
	public void visit(DesignatorExpr designatorExpr) {
		Obj obj = Tab.find(designatorExpr.getName());
    	if(obj == Tab.noObj){
			report_error("Greska na liniji " + designatorExpr.getLine()+ " : ime "+designatorExpr.getName()+" nije deklarisano! ", null);
			return;
    	}
    	
    	
		
		

		if(obj.getType().getKind()== Struct.Array ) {
			if(designatorExpr.getExpr().struct.getKind()==Struct.Array) {
				if(	designatorExpr.getExpr().struct.getElemType().getKind()!= obj.getKind()) {
					report_error("Greska na liniji "+ designatorExpr.getLine()+" : indeksiranje mora da ide uz INT.", null);
					return;
				}
			}else if(designatorExpr.getExpr().struct.getKind()!=Struct.Int) {
				report_error("Greska na liniji "+ designatorExpr.getLine()+" : indeksiranje mora da ide uz INT.", null);
				return;
			}
			
			//report_error("Greska na liniji "+ designatorExpr.getLine()+" : indeksiranje mora da ide uz INT.", null);
			//return;
		}
		find=true;
		arrOrElse=false;
    	designatorExpr.obj = obj;

	}
	public void visit(DesignatorIDENT designator){
    	Obj obj = Tab.find(designator.getName());
    	if(obj == Tab.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName()+" nije deklarisano! ", null);
    	}
    	if(obj.getType().getKind()==Struct.Array) {
    		arrOrElse=true;
    		
    	}else {
    		arrOrElse=false;
    		
    	}
    	find=false;
    	designator.obj = obj;
    	
    }
	
	
/*------------------------------------Passed--------------------------------------------*/
	

	public boolean passed(){
    	return !errorDetected;
    }
	
}

