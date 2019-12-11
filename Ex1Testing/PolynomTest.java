package Ex1Testing;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;

class PolynomTest 
{
	static Polynom actualPolynom;
	static Polynom expectedPolynom;

	@BeforeEach
	void setUp() throws Exception 
	{
		actualPolynom = new Polynom();
		actualPolynom.add(new Monom(2,3));
		actualPolynom.add(new Monom(4,2));
		actualPolynom.add(new Monom(-1,4));
	}

	@Test
	void testAddAndEquals()  //the next tests are uses Equal method, so firstly we'll make sure it won't failed
	{
		expectedPolynom = new Polynom();
		expectedPolynom.add(new Monom(2,3));
		expectedPolynom.add(new Monom(4,2));
		expectedPolynom.add(new Monom(-1,4));
		if (!(expectedPolynom.equals(actualPolynom)))	
			fail("The equals method is inncorect ");
	}

	@Test
	void testPolynomStr() 
	{
		expectedPolynom = new Polynom ("2x^3+4x^2-x^4");
		if (!(expectedPolynom.equals(actualPolynom)))	
			fail("The insert string polynom method is inncorect ");
	}

	@Test
	void testF()
	{
		double xVal = 3;
		double expectedValue = 2*(Math.pow(xVal, 3)) + 4*Math.pow(xVal, 2) -Math.pow(xVal, 4);
		if (actualPolynom.f(xVal) != expectedValue)
			fail("F method is inncorect");
	}

	@Test
	void testSubstruct()
	{
		Polynom p = new Polynom ("2x^3+4x^2-x^4");
		actualPolynom.substract(p);
		expectedPolynom = new Polynom ("0");
		if (!(expectedPolynom.equals(actualPolynom)))	
			fail(" Substcat method is inncorect");
		actualPolynom = new Polynom ("2x^3+4x^2");
		actualPolynom.substract(p);
		expectedPolynom = new Polynom ("x^4");
		if (!(expectedPolynom.equals(actualPolynom)))	
			fail(" Substcat method is inncorect");
	}

	@Test
	void testmuliply()
	{
		Polynom p = new Polynom ("2x^3");
		actualPolynom.multiply(p);
		expectedPolynom = new Polynom ("4x^6+8x^5-2x^7");
		if (!(expectedPolynom.equals(actualPolynom)))	
			fail(" multipy method is inncorect");
	}

	@Test
	void testIsZero() 
	{
		boolean expectedAns = true;
		if (actualPolynom.isZero() == expectedAns)
			fail("IsZero Method returns true for polynom that is not zero");
		Polynom p = new Polynom("0");
		if (p.isZero() != expectedAns)
			fail("IsZero Method returns false for polynom that is zero");
	}

	@Test
	void testRoot() 
	{
		actualPolynom = new Polynom("x^2-9"); //roots are +3 and -3.
		double posRoot = actualPolynom.root(0, 8, 0.001);
		double negRoot = actualPolynom.root(-8, 0, 0.001);
		double expectedPos = 3;
		double expectedNeg = -3;
		if (negRoot != expectedNeg)
			fail("Root method returns worng value");
		if (posRoot != expectedPos)
			fail ("Root method returns worng value");
	}

	@Test
	void testCopy()
	{
		Polynom_able p = new Polynom ();
		p = actualPolynom.copy();
		expectedPolynom = new Polynom ("2x^3+4x^2-x^4");
		if (!(expectedPolynom.equals(p)))	
			fail(" Copy method is inncorect");
	}

	@Test
	void testDerivative()
	{
		expectedPolynom = new Polynom ("6x^2+8x-4x^3");
		Polynom_able p = actualPolynom.derivative();
		if (!(expectedPolynom.equals(p)))	
			fail("Derivative method is inncorect");
		expectedPolynom = new Polynom ("6x^2+8x");
		if (expectedPolynom.equals(p))
			fail("Derivative method is inncorect");
	}

	@Test
	void testArea()
	{
		double actualVal = actualPolynom.area(0, 1, 0.0001);
		double expectedAa = 1.6330833416663786;
		if (expectedAa != actualVal)
			fail ("Area method returns worng value");
	}

}
