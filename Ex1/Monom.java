package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ****** add your code below *********
	public Monom(String s)  {
		if(s.matches(".*[abcdefghijklmnopqrstuvwyz*()].*")){
			throw new IllegalArgumentException("complex regex or letters can not accepted in this program");
		}
		if(s.contains("^")) {
			String[] CoeAndPow= s.split("x");
			this._power=Integer.parseInt(CoeAndPow[1].substring(1));
			if(this._power < 0)
				throw new IllegalArgumentException("power can not be negative in this program");
			if(CoeAndPow[0].equals("-")){
				this._coefficient=-1;
			}
			else if(s.charAt(0)=='x') {
				this._coefficient=1;
			}
			else {
				this._coefficient=Double.parseDouble(CoeAndPow[0]);
			}

		}
		else if(s.contains("x")) {
			if(s.charAt(0)=='-' && s.charAt(1)=='x') {
				this._coefficient=-1;
			}
			else if(s.charAt(0)=='x') {
				this._coefficient=1;
			}
			else {
				this._coefficient=Double.parseDouble(s.substring(0,s.length()-1));
			}
			this._power=1;
		}
		else {
			this._coefficient=Double.parseDouble(s);
			this._power=0;

		}

	}

	public void add(Monom m) {
		if (this.isZero())
		{
			this._coefficient += m._coefficient;
			this._power += m._power;
			return;
		}
		if (this._power != m._power)
			System.out.println("Impossible adding. The power has to be equal");
		else
			this._coefficient += m._coefficient;	
	}

	public void multipy(Monom d) {
		if (this.isZero()|| d.isZero()) 
		{
			this._coefficient = 0;
			this._power = 0;
		}
		else 
		{
			this._coefficient = (this._coefficient* d._coefficient);
			this._power = (this._power + d._power);
		}
	}

	public String toString() {
		String ans= "";
		if (this._coefficient == 0)
			ans = "0";
		else if (this._power == 0)
			ans += this._coefficient;
		else
			ans += this._coefficient + "x^" + this._power ;
		return ans;
	}
	public boolean equals(Monom m1) {
		return(this._coefficient==m1._coefficient&&this._power==m1._power||(this.isZero()&&m1.isZero()));	
	}
	// you may (always) add other methods.

	//****** Private Methods and Data *******


	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	@Override
	public function initFromString(String s) {
		function m=new Monom(s);
		return m;
	}
	@Override
	public function copy() {
		return new Monom(this.toString());
	}


}