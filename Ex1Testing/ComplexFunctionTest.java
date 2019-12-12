package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

class ComplexFunctionTest 
{
	static ComplexFunction actualCF;
	static ComplexFunction expectedCF;
	static function f1,f2;
	static Operation op;

	@BeforeEach
	void setUp() throws Exception
	{
		f1 = new Polynom ("2x^3");
		f2 = new Polynom ("4x^2");
		op = Operation.Plus;
		actualCF = new ComplexFunction (op,f2,f1);
	}

	@Test
	void testEquals() //the next tests are uses Equal method, so first we'll make sure it won't failed
	{
		expectedCF = new ComplexFunction (op,f2,f1);
		if (!(actualCF.equals(expectedCF)))
			fail("Equals method returns false for functions that are equals");
	}

	@Test
	void testCopy() 
	{
		expectedCF = new ComplexFunction(op,f2,f1);
		ComplexFunction check = (ComplexFunction) actualCF.copy();
		if (!(expectedCF.equals(check)))
			fail("Copy method is inncorrect");
	}

	@Test
	void testPlus()
	{
		Polynom p=new Polynom("2x^3");
		expectedCF = new ComplexFunction(op, p,f2);
		expectedCF.plus(f1);
		actualCF.plus(f1);
		if (!
				(expectedCF.equals(actualCF)))
			fail(" method plus");
	}


	@Test   
	void testDiv()
	{
		Polynom p=new Polynom("2x^3");
		expectedCF = new ComplexFunction(op, p,f2);
		expectedCF.div(f1);
		actualCF.div(f1);
		if (!
				(expectedCF.equals(actualCF)))
			fail(" method div");

	}

	@Test
	void testMul()
	{
		Polynom p=new Polynom("2x^3");
		expectedCF = new ComplexFunction(op, p,f2);
		expectedCF.mul(f1);
		actualCF.mul(f1);
		if (!(expectedCF.equals(actualCF)))
			fail(" method div");  
	}
	
	@Test
	void testMax()
	{
		actualCF = new ComplexFunction(Operation.Min, f1,f2);
		double actualAns=actualCF.f(1);
		int expectedans=4;
		if (!(expectedans==expectedans))
			fail(" method min"); 
	}
	
	@Test
	void testMin()
	{
		actualCF = new ComplexFunction(Operation.Min, f1,f2);
		double actualAns=actualCF.f(1);
		int expectedans=2;
		if (!(expectedans==expectedans))
			fail(" method min");  
	}
	
	@Test
	void testComp()
	{
		expectedCF = new ComplexFunction(Operation.Plus,f1,f2);
		expectedCF.comp(f1);
		actualCF.comp(f1);
		if (!(expectedCF.equals(actualCF)))
			fail(" method min");  
	}
	@Test
	void testInitFromString()
	{
		expectedCF = new ComplexFunction();
		expectedCF=(ComplexFunction) expectedCF.initFromString("plus(4x^2,2x^3)");
		if (!(expectedCF.equals(actualCF)))
			fail(" method initFromString");  
	}
	
	@Test
	void testF()
	{
		double actual = actualCF.f(1);
		double expected=6;
		if (!(expected==actual))
			fail(" method f");  
	}
	
	@Test
	void testToString()
	{
		String expected="plus(2.0x^3,4.0x^2)";
		if (!(expected.equals(actualCF.toString())));
			fail(" method toString");  
	}
	@Test
	void testGet()
	{
		Operation expectedOp=Operation.Plus;
		function expectedLeft=new Polynom("2.0x^3");
		function expectedRight=new Polynom("4.0x^2");
		Operation actualOp=actualCF.getOp();
		function actualLeft=actualCF.left();
		function actualRight=actualCF.right();
		if (!(actualRight.f(1)==expectedRight.f(1)&&actualLeft.f(1)==expectedLeft.f(1)&&actualOp.equals(expectedOp)))
			fail(" method get");  
	}
	
	
}