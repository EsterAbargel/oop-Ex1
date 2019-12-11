package Ex1Testing;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Ex1.Monom;

class MonomTest {

	static Monom expectedMonom;
	static Monom actualMonom;

	@BeforeEach
	void set() throws Exception {
		actualMonom = new Monom(3, 2);
	}

	@Test
	public void testGet() {
		int expectedCo = 3, expectedPo = 2;
		if (actualMonom.get_coefficient() != expectedCo)
			fail("getCoefficient is incorrect ");
		if (actualMonom.get_power() != expectedPo)
			fail("getPower is incorrect ");
	}

	@Test
	public void testDerivative() {
		expectedMonom = new Monom(actualMonom.get_coefficient() * actualMonom.get_power(), actualMonom.get_power() - 1);
		if (actualMonom.derivative().get_coefficient() != expectedMonom.get_coefficient())
			fail("Derivative Method returns incorrect coefficient");
		if (actualMonom.derivative().get_power() != expectedMonom.get_power())
			fail("Derivative Method returns incorrect power");
	}

	@Test
	public void testF() {
		double value = 4;
		double expectedValue = actualMonom.get_coefficient() * (Math.pow(value, actualMonom.get_power()));
		if (actualMonom.f(value) != expectedValue)
			fail("f Method returns incorrect value");
	}
	
	@Test
	public void testIsZero() {
		boolean expectedAns = true;
		if (actualMonom.isZero() == expectedAns)
			fail("IsZero Method returns true for monom that is not zero");
		Monom m1 = new Monom(0, 0);
		if (m1.isZero() != expectedAns)
			fail("IsZero Method returns false for monom that is zero");
	}
	
	@Test
	public void testMonomStr()
	{
		Monom str = new Monom ("3x^2");
		if (str.get_coefficient() != actualMonom.get_coefficient())
			fail("Set coefficient throw string monom method ");
		if (str.get_power() != actualMonom.get_power())
			fail("Set power throw string monom method ");
	}
	
	@Test
	public void testAdd()
	{
		Monom m1 = new Monom (5, 2);
		expectedMonom = new Monom (actualMonom.get_coefficient()+ m1.get_coefficient(), m1.get_power());
		actualMonom.add(m1);
		if (expectedMonom.get_coefficient() != actualMonom.get_coefficient())
			fail("The coefficient after Add method is incorrect");
		if (expectedMonom.get_power() != actualMonom.get_power())
			fail("The power after Add method is incorrect");
		Monom m2 = new Monom (5, 3);
		expectedMonom = new Monom (actualMonom.get_coefficient(), actualMonom.get_power());
		actualMonom.add(m2); //Suppose not to change because the power is not equal
		if (expectedMonom.get_coefficient() != actualMonom.get_coefficient())
			fail("The coefficient after Add method is incorrect");
		if (expectedMonom.get_power() != actualMonom.get_power())
			fail("The power after Add method is incorrect");
	}

	@Test
	public void testMultipy() {
		Monom m = new Monom(4, 2);
		expectedMonom = new Monom(m.get_coefficient() * actualMonom.get_coefficient(),
				m.get_power() + actualMonom.get_power());
		actualMonom.multipy(m);
		if (expectedMonom.get_coefficient() != actualMonom.get_coefficient())
			fail("The coefficient is incorrect");
		if (expectedMonom.get_power() != actualMonom.get_power())
			fail("The power is incorrect");
	}

	@Test
	public void testEquals() {
		Monom m1 = new Monom(4, 2);
		Monom m2 = new Monom(3, 2);
		boolean expectedAns = false;
		if (actualMonom.equals(m1) != expectedAns)
			fail("Equal Method's output should be false but its true");
		if (actualMonom.equals(m2) == expectedAns)
			fail("Equal Method's output should be true but its false");
	}

}
