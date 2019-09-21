package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import jdk.nashorn.internal.ir.Assignment;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	// alocirati niz

	boolean errorDetected = false;
	Obj currentMethod = null; // metoda koju trenutno obradjujemo
	Obj currentEnum = null; // nabrajanje koje trenutno obradjujemo
	boolean returnFound = false;
	int nVars;
	static final Struct boolType = new Struct(Struct.Bool);
	int currEnumValue = -1; // trenutna vrednost nabrajanja
	Struct lastVisited; // trenutni tip
	boolean equalCheck = false; //
	Obj mainFunc = null; // sacuvan objekat glavne funkcije za kasniju proveru
	List<Integer> enumValues = null; // lista vrednosti koje imamo u trenutnom enumu

	int nArgs; // broj argumenata funkcije
	int nActParams = 0; // broj stvarnih argumenata funkcije
	List<Struct> actualParameters = new ArrayList<>();

	int nArr;
	int nActArr = 0;

	Logger log = Logger.getLogger(getClass());

	public static Obj prvi, drugi;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		System.err.println((msg.toString()));
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		System.out.println(msg.toString());
	}

	public void visit(Program program) {
		///////////// m///////////
		// prvi = Tab.insert(Obj.Var, "*+*", Tab.intType);
		// drugi = Tab.insert(Obj.Var, "*-*", Tab.intType);

		///////////////////////////////////////
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if (mainFunc == null || mainFunc.getLevel() != 0 || mainFunc.getAdr() != 0)
			report_error("U programu mora postojati main funkcija koja je tipa void i nema argumente", null);

	}

	public void visit(ProgName progName) {
		Obj meth = Tab.find("ord");

		meth.getLocalSymbols().iterator().next().setFpPos(1);

		meth = Tab.find("chr");
		meth.getLocalSymbols().iterator().next().setFpPos(1);

		meth = Tab.find("len");
		meth.getLocalSymbols().iterator().next().setFpPos(1);

		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		if (Tab.currentScope.findSymbol(progName.getPName()) == null) {
			progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);
		} else {
			progName.obj = new Obj(Obj.Prog, progName.getPName(), Tab.noType);
			report_error("Konflikt imena : " + progName.getPName(), progName);
		}
		Tab.openScope();
	}

	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funcija " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}

		currentMethod.setLevel(nArgs);
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		returnFound = false;
		currentMethod = null; // globalne funkcije nemaju implicitan parametar this
	}

	public void visit(MethodType methodType) { // analogno za methodVoid kao methodType, pre toga treba provera
												// jedinstvenosti imena
		nArgs = 0;
		if (Tab.currentScope.findSymbol(methodType.getMethName()) == null) {
			currentMethod = Tab.insert(Obj.Meth, methodType.getMethName(), methodType.getType().struct);
		} else {
			currentMethod = new Obj(Obj.Meth, methodType.getMethName(), methodType.getType().struct);
			report_error("Konflikt imena : " + methodType.getMethName(), methodType);
		}
		methodType.obj = currentMethod;
		Tab.openScope(); // i enum kao novi element, ista fora, jedan visit za otvaranje i jedno za
							// zatvaranje, licice na program
	}

	public void visit(MethodVoid methodVoid) { // analogno za methodVoid kao methodType, pre toga treba provera
												// jedinstvenosti imena
		nArgs = 0;
		if (Tab.currentScope.findSymbol(methodVoid.getMethName()) == null) {
			currentMethod = Tab.insert(Obj.Meth, methodVoid.getMethName(), Tab.noType);
		} else {
			currentMethod = new Obj(Obj.Meth, methodVoid.getMethName(), Tab.noType);
			report_error("Konflikt imena : " + methodVoid.getMethName(), methodVoid);
		}

		if (currentMethod.getName().equals("main")) {
			mainFunc = currentMethod;
		}
		methodVoid.obj = currentMethod;
		Tab.openScope(); // i enum kao novi element, ista fora, jedan visit za otvaranje i jedno za
							// zatvaranje, licice na program
	}

	public void visit(EnumDecl enumDecl) {
		Tab.chainLocalSymbols(currentEnum.getType());
		Tab.closeScope();
		currentEnum = null;
		currEnumValue = -1; // resetovanje pocetne vrednosti za autoinkrementalne vrednosti nabrajanja
		enumValues = null; // brisanje liste postojecih vrednosti trenutnog nabrajanja
	}

	public void visit(EnumName enumName) { // proveriti da li se enum dodaje kao tip Type
		if (Tab.currentScope.findSymbol(enumName.getName()) == null) {
			currentEnum = Tab.insert(Obj.Type, enumName.getName(), new Struct(Struct.Enum, Tab.intType));
		} else {
			currentEnum = new Obj(Obj.Type, enumName.getName(), new Struct(Struct.Enum, Tab.intType));
			report_error("Konflikt imena niza: " + enumName.getName(), enumName);
		}
		enumName.obj = currentEnum;
		Tab.openScope();
		enumValues = new ArrayList<>(); // pravljenje nove liste postojecih vrednosti
	}

	public void visit(EnumExpressionIdent enumExpressionIdent) {
		currEnumValue++;
		if (Tab.currentScope.findSymbol(enumExpressionIdent.getName()) == null && !enumValues.contains(currEnumValue)) {
			Obj newMemb = Tab.insert(Obj.Con, enumExpressionIdent.getName(), Tab.intType);
			newMemb.setAdr(currEnumValue);
			enumValues.add(currEnumValue);
		} else {
			currEnumValue = Tab.currentScope.findSymbol(enumExpressionIdent.getName()).getAdr();
			report_error(
					"Imena i vrednosti clanova nabrajanja moraju biti jedinstveni!: " + enumExpressionIdent.getName(),
					enumExpressionIdent);
		}
	}

	public void visit(EnumExpressionIdentNumConst enumExpressionIdentNumConst) {
		currEnumValue = enumExpressionIdentNumConst.getNumber();
		if (Tab.currentScope.findSymbol(enumExpressionIdentNumConst.getName()) == null
				&& !enumValues.contains(currEnumValue)) {
			Obj newMemb = Tab.insert(Obj.Con, enumExpressionIdentNumConst.getName(), Tab.intType);
			newMemb.setAdr(currEnumValue);
			enumValues.add(currEnumValue);
		} else {
			currEnumValue = Tab.currentScope.findSymbol(enumExpressionIdentNumConst.getName()).getAdr();
			report_error("Imena i vrednosti clanova nabrajanja moraju biti jedinstveni!: "
					+ enumExpressionIdentNumConst.getName(), enumExpressionIdentNumConst);
		}

	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}
		if (type.struct.getKind() == 6)
			type.struct = Tab.intType;
		lastVisited = type.struct;
	}

	public void visit(DesignatorIdent designator) {
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) { // treba name da bude Broj
			report_error("Greska na liniji " + designator.getLine() + " : ime " + designator.getName()
					+ " nije deklarisano! ", null);
		} else {
			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(obj);
			if (obj.getFpPos() != 0)
				report_info("Koristi se formalni parametar funkcije " + dump.getOutput(), designator);
			else if (obj.getKind() == Obj.Con)
				report_info("Koristi se konstanta " + dump.getOutput(), designator);
			else if (obj.getKind() == Obj.Var) // dodati ovde jos jedan if sa fpposom za formalne arg
				if (obj.getLevel() == 0)
					report_info("Koristi se globalna promenljiva " + dump.getOutput(), designator);
				else
					report_info("Koristi se lokalna promenljiva " + dump.getOutput(), designator);

		}
		designator.obj = obj;
		// detektovanje koriscenja, za nizove cemo ono [] sto ima array u nazivu

	}

	public void visit(DesignatorListIdent designatorListIdent) {
		designatorListIdent.obj = designatorListIdent.getDesignator().obj.getType().getMembersTable()
				.searchKey(designatorListIdent.getName()); // treba da bude int, a name NULA
		if (designatorListIdent.obj == null) {
			report_error(
					"Greska na liniji " + designatorListIdent.getLine() + " : identifikator "
							+ designatorListIdent.getName() + " nije deklarisan u okviru dezignatora! ",
					designatorListIdent);
			designatorListIdent.obj = Tab.noObj;
		}
	}

	public void visit(DesignatorListExpr designatorListExpr) {
		if (designatorListExpr.getArrayDes().getDesignator().obj.getType().getKind() == Struct.Array) {

			designatorListExpr.obj = new Obj(Obj.Elem, "",
					designatorListExpr.getArrayDes().getDesignator().obj.getType().getElemType());

			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(designatorListExpr.obj);
			report_info("Koristi se element niza " + dump.getOutput(), designatorListExpr);

			if (!designatorListExpr.getExpr().struct.equals(Tab.intType)
					&& designatorListExpr.getExpr().struct.getKind() != Struct.Enum) {
				report_error("Greska: tip izraza " + designatorListExpr.getArrayDes().getDesignator().obj.getName()
						+ " mora biti Int! ", designatorListExpr);
			}
		} else {
			designatorListExpr.obj = Tab.noObj;
			report_error("Greska na liniji: tip " + designatorListExpr.getArrayDes().getDesignator().obj.getName()
					+ " mora biti Array! ", designatorListExpr);
		}

	}

	public void visit(DesignatorStatementAssign designatorStatementAssign) { // kompatibilnost proveriti
		Struct exprType = designatorStatementAssign.getExpr().struct;
		Struct desType = designatorStatementAssign.getDesignator().obj.getType();
		if (designatorStatementAssign.getDesignator().obj.getKind() != Obj.Var
				&& designatorStatementAssign.getDesignator().obj.getKind() != Obj.Elem
				&& designatorStatementAssign.getDesignator().obj.getKind() != Obj.Fld) {
			report_error("Greska na liniji " + designatorStatementAssign.getLine() + " : dezignator "
					+ designatorStatementAssign.getDesignator().obj.getName()
					+ " mora biti promenljiva, element niza ili polje! ", designatorStatementAssign);
		}

		if (!exprType.assignableTo(desType) && desType.getKind() != Struct.Enum) {
			report_error("Greska na liniji " + designatorStatementAssign.getLine() + " : dezignator "
					+ designatorStatementAssign.getDesignator().obj.getName() + " mora biti kompatibilan sa izrazom! ",
					null);
		}
	}

	public void visit(DesignatorStatementAssignError des) {
		report_info("Na liniji: " + des.getLine() + " desio se oporavak od greske pri dodeli vrednosti do ';' ", null);
	}

	public void visit(VarDeclError err) {
		report_info("Na liniji: " + err.getLine() + " desio se oporavak od greske pri deklaraciji promenljive do ';' ",
				null);
	}

	public void visit(VarExpressionsError err) {
		report_info("Na liniji: " + err.getLine() + " desio se oporavak od greske pri deklaraciji promenljive do ',' ",
				null);
	}

	public void visit(ParametersError err) {
		report_info("Na liniji: " + err.getParent().getLine()
				+ " desio se oporavak od greske pri deklaraciji formalnog parametra do ')' ", null);
	}

	public void visit(FormParsListError err) {
		report_info("Na liniji: " + err.getLine()
				+ " desio se oporavak od greske pri deklaraciji formalnog parametra do ',' ", null);
	}

	public void visit(BasicIfError err) {
		report_info("Na liniji: " + err.getLine()
				+ " desio se oporavak od greske kod logickog izraza unutar if konstrukcije do ')' ", null);
	}

	public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
		if (designatorStatementIncrement.getDesignator().obj.getKind() != Obj.Var
				&& designatorStatementIncrement.getDesignator().obj.getKind() != Obj.Elem
				&& designatorStatementIncrement.getDesignator().obj.getKind() != Obj.Fld
				&& designatorStatementIncrement.getDesignator().obj.getType() != Tab.intType) {
			report_error(
					"Greska na liniji " + designatorStatementIncrement.getLine() + " : dezignator "
							+ designatorStatementIncrement.getDesignator().obj.getName()
							+ " mora biti promenljiva, element niza ili polje tipa int! ",
					designatorStatementIncrement);
		}
	}

	public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
		if (designatorStatementDecrement.getDesignator().obj.getKind() != Obj.Var
				&& designatorStatementDecrement.getDesignator().obj.getKind() != Obj.Elem
				&& designatorStatementDecrement.getDesignator().obj.getKind() != Obj.Fld
				&& designatorStatementDecrement.getDesignator().obj.getType() != Tab.intType) {
			report_error(
					"Greska na liniji " + designatorStatementDecrement.getLine() + " : dezignator "
							+ designatorStatementDecrement.getDesignator().obj.getName()
							+ " mora biti promenljiva, element niza ili polje tipa int! ",
					designatorStatementDecrement);
		}
	}

	public void visit(DesignatorStatementEmpty designatorStatementEmpty) {
		if (designatorStatementEmpty.getDesignator().obj.getKind() != Obj.Meth
				|| designatorStatementEmpty.getDesignator().obj.getLevel() != 0
		/* || designatorStatementEmpty.getDesignator().obj.getLevel() > 0 */) {
			report_error("Greska na liniji " + designatorStatementEmpty.getLine() + " : dezignator "
					+ designatorStatementEmpty.getDesignator().obj.getName()
					+ " mora biti globalna metoda bez argumenata! ", designatorStatementEmpty);
		} else {
			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(designatorStatementEmpty.getDesignator().obj);
			report_info("Poziva se funkcija " + dump.getOutput(), designatorStatementEmpty);
		}
		nActParams = 0;
	}

	public void visit(StatementRead statementRead) {
		if (statementRead.getDesignator().obj.getKind() != Obj.Elem
				&& statementRead.getDesignator().obj.getKind() != Obj.Var
				&& statementRead.getDesignator().obj.getKind() != Obj.Fld) {
			report_error("Greska na liniji " + statementRead.getLine() + " : dezignator "
					+ statementRead.getDesignator().obj.getName() + " mora biti element niza, promenljiva ili polje! ",
					null);
		}
		if (!(statementRead.getDesignator().obj.getType().equals(Tab.intType)
				|| statementRead.getDesignator().obj.getType().equals(Tab.charType)
				|| statementRead.getDesignator().obj.getType().equals(boolType))) {
			report_error(
					"Greska na liniji " + statementRead.getLine() + " : dezignator "
							+ statementRead.getDesignator().obj.getName() + " mora biti tipa int, char ili bool! ",
					null);
		}
	}

	public void visit(StatementPrintExprNum statementPrintExprNum) {
		if (!(statementPrintExprNum.getExpr().struct.getKind() == Struct.Int
				|| statementPrintExprNum.getExpr().struct.getKind() == Struct.Char
				|| statementPrintExprNum.getExpr().struct.getKind() == Struct.Bool)) {
			report_error("Greska na liniji " + statementPrintExprNum.getLine() + " : izraz "
					+ " mora biti tipa int, char ili bool! ", statementPrintExprNum);
		}
	}

	public void visit(StatementPrintExpr statementPrintExpr) {
		if (!(statementPrintExpr.getExpr().struct.getKind() == Struct.Int
				|| statementPrintExpr.getExpr().struct.getKind() == Struct.Char
				|| statementPrintExpr.getExpr().struct.getKind() == Struct.Bool)) {
			report_error("Greska na liniji " + statementPrintExpr.getLine() + " : izraz "
					+ " mora biti tipa int, char ili bool! ", statementPrintExpr);
		}
	}

	public void visit(StatementReturn statementReturn) {
		returnFound = false;
		if (currentMethod == null || currentMethod.getLevel() > 0) {
			report_error("Greska na liniji " + statementReturn.getLine() + " : return moze postojati "
					+ "samo u okviru tela globalne funkcije! ", statementReturn);
		}
		if (currentMethod != null && currentMethod.getType().getKind() != Struct.None) {
			report_error(
					"Greska na liniji " + statementReturn.getLine() + " : funkcija zahteva " + "povratnu vrednost! ",
					null);
		}
	}

	public void visit(StatementReturnExpr returnExpr) {
		returnFound = true;
		Struct currMethType = currentMethod.getType();
		if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
			report_error("Greska na liniji " + returnExpr.getLine() + " : "
					+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
					+ currentMethod.getName(), null);
		}
	}

	public void visit(TermListAddop addExpr) {
		Struct te = addExpr.getTermList().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.equals(Tab.intType) || te.getKind() == Struct.Enum)
				&& (t.equals(Tab.intType) || t.getKind() == Struct.Enum))
			addExpr.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje.",
					null);
			addExpr.struct = Tab.noType;
		}
	}

	public void visit(ConstantNumber constantNumber) {
		if (lastVisited.equals(Tab.intType)) {
			if (Tab.currentScope.findSymbol(constantNumber.getVar()) == null) {
				Obj curr = Tab.insert(Obj.Con, constantNumber.getVar(), Tab.intType);
				curr.setAdr(constantNumber.getNumvalue());
			} else {
				report_error("Konflikt imena konstante: " + constantNumber.getVar(), constantNumber);
			}
		} else {
			report_error("Nekompatibilnost konstante: " + constantNumber.getVar() + " i tipa!", constantNumber);
		}
	}

	public void visit(ConstantCharacter constantCharacter) {
		if (lastVisited.equals(Tab.charType)) {
			if (Tab.currentScope.findSymbol(constantCharacter.getVar()) == null) {
				Obj curr = Tab.insert(Obj.Con, constantCharacter.getVar(), Tab.intType);
				curr.setAdr(constantCharacter.getCharvalue());
			} else {
				report_error("Konflikt imena konstante: " + constantCharacter.getVar(), constantCharacter);
			}
		} else {
			report_error("Nekompatibilnost konstante: " + constantCharacter.getVar() + " i tipa!", constantCharacter);

		}
	}

	public void visit(ConstantBoolean constantBoolean) {
		if (lastVisited.equals(boolType)) {
			if (Tab.currentScope.findSymbol(constantBoolean.getVar()) == null) {
				Obj curr = Tab.insert(Obj.Con, constantBoolean.getVar(), Tab.intType);
				curr.setAdr(constantBoolean.getBoolvalue() ? 1 : 0);
			} else {
				report_error("Konflikt imena konstante: " + constantBoolean.getVar(), constantBoolean);
			}
		} else {
			report_error("Nekompatibilnost konstante: " + constantBoolean.getVar() + " i tipa!", constantBoolean);

		}
	}

	public void visit(ConditionFactExpressions conditionFactExpressions) {
		Struct te = conditionFactExpressions.getExpr().struct;
		Struct t = conditionFactExpressions.getExpr1().struct;
		if (!(te.compatibleWith(t) || ((te.equals(Tab.intType) || te.getKind() == Struct.Enum)
				&& (t.equals(Tab.intType) || t.getKind() == Struct.Enum)))) {
			report_error("Greska na liniji " + conditionFactExpressions.getLine() + " : "
					+ "tipovi izraza  sa obe strane relacije moraju biti kompatiiblni!", null);
		}
		if (conditionFactExpressions.getExpr().struct.isRefType()
				|| conditionFactExpressions.getExpr1().struct.isRefType()) {
			if (!equalCheck) {
				report_error("Greska na liniji " + conditionFactExpressions.getLine() + " : "
						+ "za klase i nizove smeju od relacionih operacija smeju se koristiti samo provere jednakosti!",
						null);
			}
		}
	}

	public void visit(ConditionFactExpression conditionFactExpression) {
		if (!conditionFactExpression.getExpr().struct.equals(boolType)) {

			report_error("Greska na liniji " + conditionFactExpression.getLine() + " : "
					+ "tip izraza u uslovu mora biti bool!", null);

		}
	}

	public void visit(ExpressionNegative expressionNegative) {
		Struct t = expressionNegative.getTermList().struct;
		if (t.equals(Tab.intType))
			expressionNegative.struct = Tab.intType;
		else {
			report_error("Greska na liniji " + expressionNegative.getLine() + " : izraz mora biti tipa int!", null);
			expressionNegative.struct = Tab.noType;
		}
	}

	public void visit(ActPar actPar) {
		nActParams++;
		actualParameters.add(actPar.getExpr().struct);
	}

	public void visit(ExpressionPositive expressionPositive) {
		expressionPositive.struct = expressionPositive.getTermList().struct; // ostaje int
	}

	public void visit(TermMultiplied termMultiplied) {
		Struct te = termMultiplied.getTerm().struct;
		Struct t = termMultiplied.getPowfact().struct;
		if ((te.equals(Tab.intType) || te.getKind() == Struct.Enum)
				&& (t.equals(Tab.intType) || t.getKind() == Struct.Enum))
			termMultiplied.struct = Tab.intType;
		else {
			report_error(
					"Greska na liniji " + termMultiplied.getLine() + " : nekompatibilni tipovi u izrazu za mnozenje.",
					null);
			termMultiplied.struct = Tab.noType;
		}
	}

	public void visit(TermSingle termSingle) {
		termSingle.struct = termSingle.getPowfact().struct; // trebali bi da ostane int
	}

	public void visit(Power termMultiplied) {
		Struct te = termMultiplied.getPowfact().struct;
		Struct t = termMultiplied.getFactor().struct;
		if ((te.equals(Tab.intType) || te.getKind() == Struct.Enum)
				&& (t.equals(Tab.intType) || t.getKind() == Struct.Enum))
			termMultiplied.struct = Tab.intType;
		else {
			report_error(
					"Greska na liniji " + termMultiplied.getLine() + " : nekompatibilni tipovi u izrazu za mnozenje.",
					null);
			termMultiplied.struct = Tab.noType;
		}
	}

	public void visit(PowerFact termSingle) {
		termSingle.struct = termSingle.getFactor().struct; // trebali bi da ostane int
	}

	public void visit(TermListSingle termListSingle) {
		termListSingle.struct = termListSingle.getTerm().struct; // isto int, zapravo ne
	}

	public void visit(FactDesignatorPars factDesignatorPars) {// treba da bude globalna, u test 302 problem ako je 1
		Obj des = factDesignatorPars.getDesignator().obj;
		if (des.getKind() == Obj.Meth) {
			factDesignatorPars.struct = des.getType();

			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(des);
			report_info("Poziva se funkcija " + dump.getOutput(), factDesignatorPars);

			if (des.getLevel() == nActParams) {
				for (int currentPos = 1; currentPos <= nActParams; currentPos++) {
					for (Iterator<Obj> i = des.getLocalSymbols().iterator(); i.hasNext();) {
						Obj localVar = i.next();
						if (localVar.getFpPos() == currentPos) {
							if (!localVar.getType().equals(actualParameters.get(currentPos - 1))) {
								report_error("Greska na liniji " + factDesignatorPars.getLine()
										+ " : nekompatibilni tipovi stvarnih i formalnih argumenata "
										+ localVar.getName() + ", metode "
										+ factDesignatorPars.getDesignator().obj.getName() + "! ", null);
							}
							break;
						}
					}
				}

			} else {
				report_error("Greska na liniji " + factDesignatorPars.getLine()
						+ " : broj stvarnih i formalnih argumenata metode "
						+ factDesignatorPars.getDesignator().obj.getName() + " mora biti isti! ", null);
			}
		} else {
			report_error("Greska na liniji " + factDesignatorPars.getLine() + " : dezignator mora biti "
					+ "globalna funkcija.", null);
			factDesignatorPars.struct = Tab.noType;
		}

		actualParameters = new ArrayList<>();
		nActParams = 0;
	}

	public void visit(DesignatorStatementActParameters designatorStatementActParameters) {// kako da znamo da je
// globalna?
		Obj desMethod = designatorStatementActParameters.getDesignator().obj;
		if (desMethod.getKind() == Obj.Meth) {

			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(desMethod);
			report_info("Poziva se funkcija " + dump.getOutput(), designatorStatementActParameters);

			if (desMethod.getLevel() == nActParams) {
				for (int currentPos = 1; currentPos <= nActParams; currentPos++) {
					for (Iterator<Obj> i = desMethod.getLocalSymbols().iterator(); i.hasNext();) {
						Obj localVar = i.next();
						if (localVar.getFpPos() == currentPos) {
							if (!localVar.getType().equals(actualParameters.get(currentPos - 1))) {
								report_error(
										"Greska na liniji " + designatorStatementActParameters.getLine()
												+ " : nekompatibilni tipovi stvarnih i formalnih argumenata "
												+ localVar.getName() + ", metode "
												+ designatorStatementActParameters.getDesignator().obj.getName() + "! ",
										null);
							}

							currentPos++;
							break;
						}
					}
				}

			} else {
				report_error(
						"Greska na liniji " + designatorStatementActParameters.getLine()
								+ " : broj stvarnih i formalnih argumenata metode "
								+ designatorStatementActParameters.getDesignator().obj.getName() + " mora biti isti! ",
						null);
			}
		}

		else {
			report_error("Greska na liniji " + designatorStatementActParameters.getLine() + " : dezignator "
					+ designatorStatementActParameters.getDesignator().obj.getName() + " mora biti globalna metoda! ",
					null);
		}

		actualParameters = new ArrayList<>();
		nActParams = 0;
	}

	public void visit(FactDesignatorParsEmpty factDesignatorParsEmpty) {
		Obj des = factDesignatorParsEmpty.getDesignator().obj;
		if (des.getKind() == Obj.Meth) {

			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(des);
			report_info("Poziva se funkcija " + dump.getOutput(), factDesignatorParsEmpty);

			factDesignatorParsEmpty.struct = des.getType();
			if (des.getLevel() != 0) {
				report_error("Greska na liniji " + factDesignatorParsEmpty.getLine() + " : broj argumenata u pozivu "
						+ "i definiciji funkcije mora biti isti.", null);
			}
			nActParams = 0;
		} else {
			report_error("Greska na liniji " + factDesignatorParsEmpty.getLine() + " : dezignator mora biti "
					+ "globalna funkcija.", null);
			factDesignatorParsEmpty.struct = Tab.noType;
		}

	}

	public void visit(FactDesignator factDesignator) {
		factDesignator.struct = factDesignator.getDesignator().obj.getType(); // type je tip i treba da bude int
	}

	public void visit(FactNumber factNumber) {
		factNumber.struct = Tab.intType;
	}

	public void visit(FactChar factChar) {
		factChar.struct = Tab.charType;
	}

	public void visit(FactBool factBool) {
		factBool.struct = boolType;
	}

	public void visit(FactExpr factExpr) {
		factExpr.struct = factExpr.getExpr().struct;
	}

	public void visit(FactNew factNew) {
		factNew.struct = lastVisited;
	}

	public void visit(FactNewExpr factNewExpr) {
//		Struct tip = factNewExpr.getNewTypeArr().getExpr().struct;
//
//		if (tip.equals(Tab.intType)) {
		factNewExpr.struct = new Struct(Struct.Array, lastVisited);
//		} else {
//			report_error("Greska na liniji " + factNewExpr.getLine() + " : izraz mora biti " + "celobrojnog tipa!",
//					null);
//			factNewExpr.struct = Tab.noType;
//		}
	}

	public void visit(FactNewArrayInit factNewExpr) { // MENJATI
		// Struct tip = factNewExpr.getNewTypeArr().getExpr().struct;

		// if (tip.equals(Tab.intType)) {
		factNewExpr.struct = new Struct(Struct.Array, lastVisited);
		if (nActArr != nArr) {
			report_error("Greska na liniji " + factNewExpr.getLine() + " : broj elemenata niza mora"
					+ " odgovarati njegovoj velicini", null);
		}
		nArr = 0;
		nActArr = 0;
		// } else {
		// report_error("Greska na liniji " + factNewExpr.getLine() + " : izraz mora
		// biti " + "celobrojnog tipa!",
		// null);
		// factNewExpr.struct = Tab.noType;
		// }
	}

	public void visit(ArrPart arrPart) {
		nActArr++;
		if (!arrPart.getExpr().struct.equals(lastVisited)) {
			report_error("Greska na liniji " + arrPart.getLine() + " : tipovi niza i njegovih elemenata "
					+ "moraju biti isti.", null);
		}
	}

	public void visit(NewTypeArr arr) {
		nArr = arr.getNumber();
	}

	public void visit(RelationalEqual relationalEqual) {
		equalCheck = true;
	}

	public void visit(RelationalNotEqual relationalNotEqual) {
		equalCheck = true;
	}

	public void visit(RelationalGreater relationalGreater) {
		equalCheck = false;
	}

	public void visit(RelationalGreaterEqual relationalGreaterEqual) {
		equalCheck = false;
	}

	public void visit(RelationalLess relationalLess) {
		equalCheck = false;
	}

	public void visit(RelationalLessEqual relationalLessEqual) {
		equalCheck = false;
	}

	public void visit(TypeBrackets typeBrackets) {
		Obj newSym;
		if (typeBrackets.getOptionalBrackets() instanceof ExistingBrackets) {
			newSym = Tab.insert(Obj.Var, typeBrackets.getName(), new Struct(Struct.Array, lastVisited));

		} else {
			newSym = Tab.insert(Obj.Var, typeBrackets.getName(), lastVisited);
		}
		newSym.setFpPos(++nArgs);
	}

	public void visit(VarExpr varExpr) { // da li con ili var?
		// report_info("Deklarisana promenljiva " + varExpr.getName(), varExpr);
		if (varExpr.getOptionalBrackets() instanceof ExistingBrackets) {
			if (Tab.find(varExpr.getName()) != null) {
				Tab.insert(Obj.Var, varExpr.getName(), new Struct(Struct.Array, lastVisited));
			} else {
				report_error("Greska na liniji " + varExpr.getLine() + " : visestruka deklaracija simbola "
						+ varExpr.getName(), null);
			}
		} else {
			if (Tab.find(varExpr.getName()) != null) {
				Tab.insert(Obj.Var, varExpr.getName(), lastVisited);
			} else {
				report_error("Greska na liniji " + varExpr.getLine() + " : visestruka deklaracija simbola "
						+ varExpr.getName(), null);
			}
		}
	}

	/*
	 * public void visit(Assignment assignment) { if
	 * (!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.
	 * getType())) report_error( "Greska na liniji " + assignment.getLine() + " : "
	 * + " nekompatibilni tipovi u dodeli vrednosti ", null); }
	 * 
	 * public void visit(ProcCall procCall) { Obj func =
	 * procCall.getDesignator().obj; if (Obj.Meth == func.getKind()) {
	 * report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " +
	 * procCall.getLine(), null); // RESULT = func.getType(); } else {
	 * report_error("Greska na liniji " + procCall.getLine() + " : ime " +
	 * func.getName() + " nije funkcija!", null); // RESULT = Tab.noType; } }
	 * 
	 * public void visit(TermExpr termExpr) { termExpr.struct =
	 * termExpr.getTerm().struct; }
	 * 
	 * public void visit(Term term) { term.struct = term.getFactor().struct; }
	 * 
	 * public void visit(Const cnst) { cnst.struct = Tab.intType; }
	 * 
	 * public void visit(Var var) { var.struct = var.getDesignator().obj.getType();
	 * }
	 * 
	 * public void visit(FuncCall funcCall) { Obj func =
	 * funcCall.getDesignator().obj; if (Obj.Meth == func.getKind()) {
	 * report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " +
	 * funcCall.getLine(), null); funcCall.struct = func.getType(); } else {
	 * report_error("Greska na liniji " + funcCall.getLine() + " : ime " +
	 * func.getName() + " nije funkcija!", null); funcCall.struct = Tab.noType; }
	 * 
	 * }
	 */
	public boolean passed() {
		return !errorDetected;
	}

}
