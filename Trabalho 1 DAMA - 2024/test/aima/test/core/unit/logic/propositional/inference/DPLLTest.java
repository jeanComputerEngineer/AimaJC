package aima.test.core.unit.logic.propositional.inference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import aima.core.logic.propositional.inference.DPLL;
import aima.core.logic.propositional.inference.DPLLSatisfiable;
import aima.core.logic.propositional.inference.OptimizedDPLL;
import aima.core.logic.propositional.kb.KnowledgeBase;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import aima.core.logic.propositional.visitors.SymbolCollector;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
@RunWith(Parameterized.class)
public class DPLLTest {

	private DPLL     dpll;
	private PLParser parser;
	
	@Parameters(name = "{index}: dpll={0}")
    public static Collection<Object[]> inferenceAlgorithmSettings() {
        return Arrays.asList(new Object[][] {
        		{new DPLLSatisfiable()}, 
        		{new OptimizedDPLL()}   
        });
    }

	public DPLLTest(DPLL dpll) {
		this.dpll   = dpll;
		this.parser = new PLParser();
	}

	@Test
	public void testDPLLReturnsTrueWhenAllClausesTrueInModel() {
		Model model = new Model();
		model = model.union(new PropositionSymbol("A"), true).union(
				new PropositionSymbol("B"), true);
		Sentence sentence = parser.parse("A & B & (A | B)");
		Set<Clause> clauses = ConvertToConjunctionOfClauses.convert(sentence)
				.getClauses();
		List<PropositionSymbol> symbols = new ArrayList<PropositionSymbol>(
				SymbolCollector.getSymbolsFrom(sentence));

		boolean satisfiable = dpll.dpll(clauses, symbols, model);
		Assert.assertEquals(true, satisfiable);
	}

	@Test
	public void testDPLLReturnsFalseWhenOneClauseFalseInModel() {
		Model model = new Model();
		model = model.union(new PropositionSymbol("A"), true).union(
				new PropositionSymbol("B"), false);
		Sentence sentence = parser.parse("(A | B) & (A => B)");
		Set<Clause> clauses = ConvertToConjunctionOfClauses.convert(sentence)
				.getClauses();
		List<PropositionSymbol> symbols = new ArrayList<PropositionSymbol>(
				SymbolCollector.getSymbolsFrom(sentence));

		boolean satisfiable = dpll.dpll(clauses, symbols, model);
		Assert.assertEquals(false, satisfiable);
	}

	@Test
	public void testDPLLSucceedsWithAandNotA() {
		Sentence sentence = parser.parse("A & ~A");
		boolean satisfiable = dpll.dpllSatisfiable(sentence);
		Assert.assertEquals(false, satisfiable);
	}

	@Test
	public void testDPLLSucceedsWithChadCarffsBugReport() {
		KnowledgeBase kb = new KnowledgeBase();

		kb.tell("B12 <=> P11 | P13 | P22 | P02");
		kb.tell("B21 <=> P20 | P22 | P31 | P11");
		kb.tell("B01 <=> P00 | P02 | P11");
		kb.tell("B10 <=> P11 | P20 | P00");
		kb.tell("~B21");
		kb.tell("~B12");
		kb.tell("B10");
		kb.tell("B01");

		Assert.assertTrue(dpll.isEntailed(kb, parser.parse("P00")));
		Assert.assertFalse(dpll.isEntailed(kb, parser.parse("~P00")));
	}

	@Test
	public void testDPLLSucceedsWithStackOverflowBugReport1() {
		Sentence sentence = (Sentence) parser.parse("(A | ~A) & (A | B)");
		Assert.assertTrue(dpll.dpllSatisfiable(sentence));
	}

	@Test
	public void testDPLLSucceedsWithChadCarffsBugReport2() {
		KnowledgeBase kb = new KnowledgeBase();
		kb.tell("B10 <=> P11 | P20 | P00");
		kb.tell("B01 <=> P00 | P02 | P11");
		kb.tell("B21 <=> P20 | P22 | P31 | P11");
		kb.tell("B12 <=> P11 | P13 | P22 | P02");
		kb.tell("~B21");
		kb.tell("~B12");
		kb.tell("B10");
		kb.tell("B01");

		Assert.assertTrue(dpll.isEntailed(kb, parser.parse("P00")));
		Assert.assertFalse(dpll.isEntailed(kb, parser.parse("~P00")));
	}

	@Test
	public void testIssue66() {
		// http://code.google.com/p/aima-java/issues/detail?id=66
		Model model = new Model();
		model = model.union(new PropositionSymbol("A"), false)
				.union(new PropositionSymbol("B"), false)
				.union(new PropositionSymbol("C"), true);
		Sentence sentence = parser.parse("((A | B) | C)");
		Set<Clause> clauses = ConvertToConjunctionOfClauses.convert(sentence)
				.getClauses();
		List<PropositionSymbol> symbols = new ArrayList<PropositionSymbol>(
				SymbolCollector.getSymbolsFrom(sentence));

		boolean satisfiable = dpll.dpll(clauses, symbols, model);
		Assert.assertEquals(true, satisfiable);
	}

	@Test
	public void testDoesNotKnow() {
		KnowledgeBase kb = new KnowledgeBase();
		kb.tell("A");

		Assert.assertFalse(dpll.isEntailed(kb, parser.parse("B")));
		Assert.assertFalse(dpll.isEntailed(kb, parser.parse("~B")));
	}
        
        
        @Test
	public void testDPLLFigure74() {
		KnowledgeBase kb = new KnowledgeBase();
                
                //com inicio em 00
                
		kb.tell("B12 <=> P11 | P13 | P22");
                kb.tell("S12 <=> W11 | W13 | W22");
                kb.tell("B11 <=> P12 | P21");
                kb.tell("S11 <=> W12 | W21");
		kb.tell("B21 <=> P11 | P22 | P31");
                kb.tell("S21 <=> W11 | W22 | W31");
    		kb.tell("B22 <=> P12 | P21 | P23 | P32");
                kb.tell("S22 <=> W12 | W21 | W23 | W32");
                kb.tell("~B11");
		kb.tell("~S11");
                kb.tell("~W11");
		kb.tell("~P11");
                kb.tell("~W12");
		kb.tell("~P12");
                kb.tell("~W21");
		kb.tell("~P21");
                kb.tell("~W22");
		kb.tell("~P22");
                kb.tell("S12");
                kb.tell("B21");

                System.out.println(dpll.isEntailed(kb, parser.parse("W13")));
		System.out.println(dpll.isEntailed(kb, parser.parse("P13")));
                System.out.println(dpll.isEntailed(kb, parser.parse("W31")));
		System.out.println(dpll.isEntailed(kb, parser.parse("P31")));
	}
        
        @Test
	public void testDPLLBookExercise7_1() {
		KnowledgeBase kb = new KnowledgeBase();
                
                //com inicio em 00

		kb.tell("B12 <=> P11 | P13 | P22 | P02");
                kb.tell("S12 <=> W11 | W13 | W22 | P02");
		kb.tell("B21 <=> P20 | P22 | P31 | P11");
		kb.tell("B01 <=> P00 | P02 | P11");
		kb.tell("B10 <=> P11 | P20 | P00");
		
                kb.tell("~P11");
		kb.tell("~W11");
                kb.tell("~W12");
                kb.tell("~P12");
                kb.tell("~W21");
                kb.tell("~P21");
		kb.tell("~B11");
		kb.tell("~S11");
                kb.tell("B21");
                kb.tell("S12");

                System.out.println(dpll.isEntailed(kb, parser.parse("P00")));
		System.out.println(dpll.isEntailed(kb, parser.parse("~P00")));
	}
        
}
