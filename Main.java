import java.io.*;
import java.util.*;
import parser.*;
import ast.*;
import ast.builtin.*;


class Main {
	private static boolean runSubstitutionTest = true;

    public static void main(String[] args) {
        try
		{
			String filename = args[0];
			SimpleSchemeParser parser = new SimpleSchemeParser(new FileInputStream(filename));

			/*
			* Parse and build the AST
			*/
			ArrayList<SchemeExpression> expressions = parser.Parse();			
			System.out.println("Lambda code accepted:");
			/*
			for (SchemeExpression e : expressions) {
				System.out.println(e);
				System.out.println("Free names: " + e.freeNames());

				if (runSubstitutionTest) {
					e = e.substitute(new Variable("pred"), new Variable("MinusOne"));
					e = e.substitute(new Variable("fix"), new Variable("TheStrictFixPoint"));

					System.out.println(e);
					System.out.println("Free names (after substitution): " + e.freeNames());
				}
			}
			*/

			/*
			* Evaluate the AST
			*/
			Hashtable<Variable, SchemeExpression> symTable = new Hashtable<Variable, SchemeExpression>();
			symTable.put(new Variable("succ"), new IncrementAbstraction());
			symTable.put(new Variable("pred"), new DecrementAbstraction());
			symTable.put(new Variable("isZero"), new ZeroAbstraction());
			symTable.put(new Variable("notZero"), new NotZeroAbstraction());

			SchemeExpression result = null;
			for (SchemeExpression e : expressions) {
				result = e.reduce(symTable);
			}

			System.out.println(result);

			/*

			// Evaluate the AST
			LambdaMachine machine = new LambdaMachine();
			System.out.println("Running program: ");
			for (PCode pc: instructions) {
				pc.accept(machine);
			}

			machine.printStackTrace();
			machine.printMemoryTrace();

			*/
		}
		catch (ParseException e)
		{
			System.out.println("Syntax Error : \n" + e.toString());
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.toString());
		}
    }
}