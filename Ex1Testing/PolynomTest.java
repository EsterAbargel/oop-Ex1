package Ex1Testing;


import Ex1.Monom;
import Ex1.Polynom;

public class PolynomTest {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
	}
	/*the first test tests creating a polynom of a string,in case of valid and invalid strings.
	 * it also tests the area, the f function. isZero  function and substruct function.
	 */
	public static void test1() {
		System.out.println("******test one*******");
		Polynom p1 = new Polynom();
		String[] monoms = {"1","x","x^2", "0.5x^2"};
		for(int i=0; i < monoms.length; i++) 
		{
			Monom m1 = new Monom(monoms[i]);
			p1.add(m1); //using function add 
		}
		System.out.println(p1.toString());
		System.out.println("when the x is 2, the polynom outcome is " + p1.f(2));//using function f
		double aa = p1.area(0, 1, 0.0001); //using function area
		System.out.println("area of p1 is "+aa);
		System.out.println("substruct: "+p1+ " - "+'(' + p1+ ')' );
		p1.substract(p1);
		System.out.println("= "+p1);
		System.out.println(p1 +" \tisZero: "+p1.isZero());
		String valid="2x^2+4x+3x^2+1+x";
		System.out.println("String valid is: "+valid);
		Polynom p2 = new Polynom(valid); //using function polynom(string)
		System.out.println("the polynom after inserting the valid string: "+p2.toString());
		String invalid="2x^2-4x+3x^2&+1+x";
		System.out.println("String invalid is: "+invalid);
		Polynom p3 = new Polynom(invalid); //Ignore the forbidden monom
		System.out.println("the polynom after ignoring the invalid monom in the string is: "+p3.toString());
	} 
	/* the second test tests the add function of the polynom, copy function 
	add function and derivatives*/
	public static void test2() {
		System.out.println("**********test two**********");
		Polynom p1 = new Polynom();
		String[] monoms1 = { "2x^4","-4x^2","3","3x^3"};
		for(int i=0;i<monoms1.length;i++) {
			Monom m = new Monom(monoms1[i]);
			p1.add(m);
		}
		System.out.println("p1 is: "+p1.toString());
		p1=(Polynom) p1.derivative().derivative();
		System.out.println("p1 after two derivatives is: "+p1);
		Polynom p2=(Polynom) p1.copy();
		System.out.println("p2 after copy p1 is: "+p2.toString());
		
		

	}
	/*the third test tests the equal and multiply functions
	 * 
	 */
	
	public static void test3(){
		System.out.println("******test three********");
		Polynom p1 = new Polynom ("2x^2+4");
		Polynom p2 = new Polynom ("24x^3+10x^5+8x");
		System.out.println("p1 is: "+p1.toString()+" p2 is: "+p2.toString());
		Polynom p3= new Polynom("5x^3+2x");
		p1.multiply(p3);
		System.out.println("p1 after muliplying (5x^3+2x) is:  "+ p1.toString());
		System.out.println("p1= "+p1+ "p2= "+p2.toString()+" are they equals? " + p1.equals(p2));
	}
	/*
	 * the fourth test tests the function root
	 */
	public static void test4(){
		System.out.println("*******test four*****");
		Polynom p1=new Polynom("x^2-9");//roots are +3 and -3;
		System.out.println("polynom p1 is: ");
		double posRoot=p1.root(0, 8, 0.001);
		System.out.println("positive root is: "+posRoot);
		double negRoot=p1.root(-8, 0, 0.001);
		System.out.println("negative root is: "+negRoot);
		Polynom p2=new Polynom("x^2+9");//has no root-always above the x axis.
		System.out.println("polynom p2 is: "+p2);
		double root1=p2.root(0, 8, 0.001);
		System.out.println(root1);
	}
	
}