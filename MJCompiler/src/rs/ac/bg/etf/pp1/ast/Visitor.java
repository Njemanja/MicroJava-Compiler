// generated with ast extension for cup
// version 0.8
// 20/7/2023 23:4:35


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Designator Designator);
    public void visit(Factor Factor);
    public void visit(Mulop Mulop);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ConstDecl ConstDecl);
    public void visit(Declaration Declaration);
    public void visit(ActualParamList ActualParamList);
    public void visit(VarItem VarItem);
    public void visit(Expr Expr);
    public void visit(FormalParamList FormalParamList);
    public void visit(FormPars FormPars);
    public void visit(VarDeclList VarDeclList);
    public void visit(DeclarationList DeclarationList);
    public void visit(VarDecl VarDecl);
    public void visit(PrintConst PrintConst);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(Unmatched Unmatched);
    public void visit(Addop Addop);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Statement Statement);
    public void visit(Term Term);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(StatementList StatementList);
    public void visit(Matched Matched);
    public void visit(ActualPars ActualPars);
    public void visit(MulopMOD MulopMOD);
    public void visit(MulopDIV MulopDIV);
    public void visit(MulopMUL MulopMUL);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(DesignatorMod DesignatorMod);
    public void visit(DesignatorExpr DesignatorExpr);
    public void visit(DesignatorIDENT DesignatorIDENT);
    public void visit(Var Var);
    public void visit(FactorNEW FactorNEW);
    public void visit(FactorBOOL FactorBOOL);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNUMBER FactorNUMBER);
    public void visit(TermMulop TermMulop);
    public void visit(TermFactor TermFactor);
    public void visit(TermExpr TermExpr);
    public void visit(TermExprMinus TermExprMinus);
    public void visit(AddExpr AddExpr);
    public void visit(Izmena3 Izmena3);
    public void visit(Izmena2 Izmena2);
    public void visit(Izmena1 Izmena1);
    public void visit(Izmena Izmena);
    public void visit(Zamena Zamena);
    public void visit(DesignatorDEC DesignatorDEC);
    public void visit(DesignatorINC DesignatorINC);
    public void visit(Assignment Assignment);
    public void visit(NoConst NoConst);
    public void visit(PrintCnst PrintCnst);
    public void visit(PrintStmt PrintStmt);
    public void visit(StatementFindAll StatementFindAll);
    public void visit(StatementRead StatementRead);
    public void visit(MatchedStmt MatchedStmt);
    public void visit(DesignatorStmt DesignatorStmt);
    public void visit(NoStmt NoStmt);
    public void visit(Statements Statements);
    public void visit(UnmatchedIfElse UnmatchedIfElse);
    public void visit(UnmatchedIf UnmatchedIf);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(SingleFormalParamDecl SingleFormalParamDecl);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(MethodVoid MethodVoid);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(Type Type);
    public void visit(ConstDeclBool ConstDeclBool);
    public void visit(ConstDeclChar ConstDeclChar);
    public void visit(ConstDeclNumber ConstDeclNumber);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(VarItemArr VarItemArr);
    public void visit(VarItemIdent VarItemIdent);
    public void visit(VarOne VarOne);
    public void visit(VarMul VarMul);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(DeclarationConst DeclarationConst);
    public void visit(DeclarationVar DeclarationVar);
    public void visit(NoDecl NoDecl);
    public void visit(DeclarationL DeclarationL);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
