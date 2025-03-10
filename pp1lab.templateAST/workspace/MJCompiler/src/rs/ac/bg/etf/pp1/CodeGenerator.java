package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	int op;
	Stack<Integer> stackCondIf = new Stack<>();
	Stack<Integer> stackCondElse = new Stack<>();
	Stack<Integer> stackCondFor = new Stack<>();
	Stack<Integer> stackCondForFalse = new Stack<>();
	int condForTrue;
	Stack<Integer> stackForRepete = new Stack<>();
	int arrayCounter = 0;


	Stack<List<Integer>> stackAnd = new Stack<>();
	Stack<List<Integer>> stackOr = new Stack<>();

	public int getMainPc() {
		return mainPc;
	}

	@Override
	public void visit(ProgName progName) {

		Obj meth = Tab.find("ord");
		meth.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);

		Code.load(meth.getLocalSymbols().iterator().next());

		Code.put(Code.exit);
		Code.put(Code.return_);

		meth = Tab.find("chr");
		meth.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);

		Code.load(meth.getLocalSymbols().iterator().next());

		Code.put(Code.exit);
		Code.put(Code.return_);

		meth = Tab.find("len");
		meth.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);

		Code.load(meth.getLocalSymbols().iterator().next());
		Code.put(Code.arraylength);

		Code.put(Code.exit);
		Code.put(Code.return_);

	}

	@Override
	public void visit(MethodVoid MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		MethodTypeName.obj.setAdr(Code.pc);

		// Generate the entry.
		Code.put(Code.enter);
		Code.put(MethodTypeName.obj.getLevel());
		Code.put(MethodTypeName.obj.getLocalSymbols().size());
	}

	@Override
	public void visit(MethodType MethodTypeName) {

		MethodTypeName.obj.setAdr(Code.pc);

		// Generate the entry.
		Code.put(Code.enter);
		Code.put(MethodTypeName.obj.getLevel());
		Code.put(MethodTypeName.obj.getLocalSymbols().size());
	}

	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementReturn ReturnExpr) { // permut
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementReturnExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(NewTypeArr factNewExpr) {
		Code.loadConst(factNewExpr.getNumber());
		Code.put(Code.newarray);
		if (factNewExpr.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	@Override
	public void visit(Emptiness emptiness) {
		Code.put(Code.dup);
		Code.loadConst(arrayCounter++);
	}

	@Override
	public void visit(ArraySinglePart arraySinglePart) {
		Code.put(Code.astore);
	}

	@Override
	public void visit(ArrayMultipleParts parts) {
		Code.put(Code.astore);
	}

	@Override
	public void visit(FactNewArrayInit arrayInit) {
		arrayCounter = 0;
	}

	@Override
	public void visit(FactNumber Const) {
		Code.loadConst(Const.getNumber());
	}

	@Override
	public void visit(FactChar Const) {
		Code.loadConst(Const.getCharacter());
	}

	@Override
	public void visit(FactBool Const) {
		Code.loadConst(Const.getFlag() ? 1 : 0);
	}

	@Override
	public void visit(DesignatorStatementAssign Assignment) {
		Code.store(Assignment.getDesignator().obj);
	}

	@Override
	public void visit(FactDesignator factDesignator) {
		Code.load(factDesignator.getDesignator().obj);
	}

	@Override
	public void visit(FactDesignatorParsEmpty FuncCall) {
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	@Override
	public void visit(FactDesignatorPars FuncCall) {
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	@Override
	public void visit(DesignatorStatementActParameters FuncCall) {
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);

		if (functionObj.getType() != Tab.noType)
			Code.put(Code.pop);
	}

	@Override
	public void visit(DesignatorStatementEmpty FuncCall) {
		Obj functionObj = FuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);

		if (functionObj.getType() != Tab.noType)
			Code.put(Code.pop);
	}

	@Override
	public void visit(DesignatorStatementIncrement increment) {
		Obj des = increment.getDesignator().obj;
		if (des.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(des);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(des);
	}

	@Override
	public void visit(DesignatorStatementDecrement decrement) {
		Obj des = decrement.getDesignator().obj;
		if (des.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(des);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(des);
	}

	@Override
	public void visit(StatementPrintExpr PrintStmt) {
		Code.loadConst(5);
		if (PrintStmt.getExpr().struct.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

	@Override
	public void visit(StatementPrintExprNum PrintStmt) {
		Code.loadConst(PrintStmt.getNumber());
		if (PrintStmt.getExpr().struct.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

	@Override
	public void visit(StatementRead stmtRead) {
		if (stmtRead.getDesignator().obj.getType().equals(Tab.charType))
			Code.put(Code.bread);
		else
			Code.put(Code.read);

		Code.store(stmtRead.getDesignator().obj);
	}

	@Override
	public void visit(TermListAddop addExpr) {
		if (addExpr.getAddop() instanceof AddopPlus) {
		Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	@Override
	public void visit(TermMultiplied termMultiplied) {
		if (termMultiplied.getMulop() instanceof MultipleOp)
			Code.put(Code.mul);
		else if (termMultiplied.getMulop() instanceof DivideOp)
			Code.put(Code.div);
		else
			Code.put(Code.rem);

	}

	@Override
	public void visit(Power power) {
		// recimo da je 3 * a na b
		Code.store(SemanticAnalyzer.drugi);
		Code.store(SemanticAnalyzer.prvi);
		// zaobidjemo pravljenje treceg tako sto rezultat cuvamo u expr steku
		Code.loadConst(3); // pocetna vrednost za mnozenje 3, inace jedan

		int uslov = Code.pc;
		Code.load(SemanticAnalyzer.drugi);
		Code.loadConst(0);
		Code.putFalseJump(Code.gt, 0);
		int adresaKrajaPetlje = Code.pc - 2;

		Code.load(SemanticAnalyzer.prvi);
		Code.put(Code.mul);

		Code.load(SemanticAnalyzer.drugi);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(SemanticAnalyzer.drugi);

		Code.putJump(uslov);
		Code.fixup(adresaKrajaPetlje);

	}

	@Override
	public void visit(ArrayDes arrayDes) {
		Code.load(arrayDes.getDesignator().obj);
	}

	@Override
	public void visit(ExpressionNegative expressionNegative) {
		Code.put(Code.neg);
	}
	// if for break continue

	@Override
	public void visit(ConditionFactExpressions oppy) {
		if (oppy.getRelop() instanceof RelationalEqual)
			op = Code.eq;
		else if (oppy.getRelop() instanceof RelationalNotEqual)
			op = Code.ne;
		else if (oppy.getRelop() instanceof RelationalGreater)
			op = Code.gt;
		else if (oppy.getRelop() instanceof RelationalGreaterEqual)
			op = Code.ge;
		else if (oppy.getRelop() instanceof RelationalLess)
			op = Code.lt;
		else
			op = Code.le;
	}

	@Override
	public void visit(ConditionFactExpression conditionFactExpression) {
		Code.loadConst(1);
		op = Code.eq;
	}

	@Override
	public void visit(IfCond ifCond) {
		Code.putFalseJump(op, 0);
		stackCondIf.push(Code.pc - 2); // adresa pogresne adrese gore
		for (int i : stackOr.pop()) {
			Code.fixup(i);
		}
	}

	@Override
	public void visit(StatemtntIf statemtntIf) {
		Code.fixup(stackCondIf.pop());
		for (int i : stackAnd.pop()) {
			Code.fixup(i);
		}
	}

	@Override
	public void visit(Else el) {
		Code.putJump(0);
		stackCondElse.push(Code.pc - 2);

		Code.fixup(stackCondIf.pop());
		for (int i : stackAnd.pop()) {
			Code.fixup(i);
		}

	}

	@Override
	public void visit(StatementIfElse st) {
		Code.fixup(stackCondElse.pop());
	}

	@Override
	public void visit(Sammy sammy) {
		stackCondFor.push(Code.pc);
	}

	@Override
	public void visit(Andy andy) {
		Code.putFalseJump(op, 0);
		stackAnd.peek().add(Code.pc - 2);
	}

	public static void putTrueJump(int op, int adr) {
		Code.put(Code.jcc + op);
		Code.put2(adr - Code.pc + 1);
	}

	@Override
	public void visit(Oreo oreo) {
		putTrueJump(op, 0);
		stackOr.peek().add(Code.pc - 2);

		for (int i : stackAnd.pop()) {
			Code.fixup(i);
		}
		stackAnd.push(new ArrayList<>());
	}

	@Override
	public void visit(ConditionSingle cond) {
		List<Integer> andList = new ArrayList<>();
		stackAnd.push(andList);
		List<Integer> orList = new ArrayList<>();
		stackOr.push(orList);
	}

	@Override
	public void visit(ConditionTermSingle cond) {
		List<Integer> andList = new ArrayList<>();
		stackAnd.push(andList);
	}

	@Override
	public void visit(ExistingCondition ex) {
		Code.putFalseJump(op, 0);
		stackCondForFalse.push(Code.pc - 2);
		Code.putJump(0);
		condForTrue = Code.pc - 2;
		stackForRepete.push(Code.pc);
	}

	@Override
	public void visit(NotExistingCondition nex) {
		Code.putJump(0);
		condForTrue = Code.pc - 2;
		stackForRepete.push(Code.pc);
	}

	@Override
	public void visit(RePare rp) {
		Code.putJump(stackCondFor.pop());
		Code.fixup(condForTrue);
		for (int i : stackOr.pop()) {
			Code.fixup(i);
		}
	}

	@Override
	public void visit(StatementFor statementFor) {
		Code.putJump(stackForRepete.pop());
		Code.fixup(stackCondForFalse.pop());
		for (int i : stackAnd.pop()) {
			Code.fixup(i);
		}
	}

}
