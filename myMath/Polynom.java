package myMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	/* private added methods*/
	private ArrayList<Monom> monomList = new ArrayList<>();
	private Monom_Comperator sortMonom = new Monom_Comperator();



	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		this.monomList.add(new Monom(0,0));
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)(-1.2x-7.1)", "(3-3.4x+1)((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s)  {
		s=s.replaceAll("-", "+-").replaceAll("\\^\\+\\-", "^-");
		String[] splitToMonom =s.split("[+]+");	
		for(int i=0;i<splitToMonom.length;i++) {
			try {
				Monom temp=new Monom(splitToMonom[i]);
				this.add(temp);
			
			}
			catch(NumberFormatException e) {
				continue;
			}
		}
		monomList.sort(sortMonom);
	}
			

	@Override
	public double f(double x) {
		double sum=0;
		Iterator <Monom> monoms= iteretor();
		while(monoms.hasNext()) {
			Monom temp=monoms.next();
			sum+=temp.f(x);
		}
		return sum;
	}

	@Override
	public void add(Polynom_able p1) {

		Iterator<Monom> monoms = p1.iteretor();

		while(monoms.hasNext()) {

			Monom temp = monoms.next();
			this.add(temp);		
		}

	}

	@Override
	public void add(Monom m1) {
		Iterator<Monom> monoms = this.iteretor();	
		
		while(monoms.hasNext()) 
		{
			Monom temp = monoms.next();
			System.out.println();
			if(temp.isZero()) 
			{
				temp.add(m1);
				return;
			}
			if (temp.get_power() == m1.get_power())
			{
				temp.add(m1);
				if(temp.isZero()) {
					monomList.remove(temp);
				}
				return;		
			}
			
		}

		monomList.add(m1);
		monomList.sort(sortMonom);
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator <Monom> monoms=p1.iteretor();
		Polynom ans=new Polynom();
		while(monoms.hasNext()) {
			Monom temp=monoms.next();
			ans.add(new Monom(-1*temp.get_coefficient(),temp.get_power()));
		}
		this.add(ans);
	}

	@Override
	public void multiply(Polynom_able p1) {
		Iterator <Monom> p=this.iteretor();
		Iterator <Monom> pp1=p1.iteretor();
		Polynom ans=new Polynom();
		while(p.hasNext())
		{
			Monom temp=p.next();
			pp1=p1.iteretor();
			while(pp1.hasNext())
			{
				Monom temp1=pp1.next();
				ans.add(new Monom(temp.get_coefficient()*temp1.get_coefficient(),temp.get_power()+temp1.get_power()));

			}
		}
		this.monomList=ans.monomList;
	}

	@Override
	public boolean equals(Polynom_able p1) {
		boolean flag=true;
		Iterator<Monom> iterp=this.iteretor();
		Iterator<Monom> iterp1=p1.iteretor();
		while(iterp.hasNext()&&iterp1.hasNext()&&flag) {
			Monom temp=iterp.next();
			Monom temp1=iterp1.next();
			if(temp.get_coefficient()!=temp1.get_coefficient()||temp.get_power()!=temp1.get_power()) {
				flag= false;
			}
			if((iterp.hasNext()&&!iterp1.hasNext())||(!iterp.hasNext()&&iterp1.hasNext())) {
				flag=false;
			}
		}
		return flag;
	}

	@Override
	public boolean isZero() {
		Iterator <Monom> pFlag=this.iteretor();
		while(pFlag.hasNext())
		{
			if(pFlag.next().get_coefficient()!=0)
				return false;
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if(this.f(x0)*this.f(x1)>0)
			throw new RuntimeException("if(x0)*f(x1) must be less than or equal 0");
		if(this.f(x0)==0)
			return x0;
		if(this.f(x1)==0)
			return x1;
		if(Math.abs(x0-x1)<eps)
			return ((x0+x1)/2);
		double midd=(x0+x1)/2;
		if(this.f(x0)*this.f(midd)<0)
			return root(x0,midd,eps);
		else
			return root(midd,x1,eps);
	}

	@Override
	public Polynom_able copy() {
		Iterator<Monom> p= iteretor();
		Polynom ans=new Polynom();
		while(p.hasNext()) {
			Monom temp= p.next();
			ans.add(new Monom(temp));
		}
		return ans;
	}

	@Override
	public Polynom_able derivative() {
		Iterator<Monom> monoms=iteretor();
		Polynom ans = new Polynom();
		if(this.isZero()) {
			ans.add(new Monom(0,0));
			return ans;
		}
		while(monoms.hasNext()) {
			Monom temp=monoms.next().derivative();
			ans.add(new Monom(temp));
		}

		return ans;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		if(x1<x0) {
			throw new ArithmeticException("x0 can't be be bigger than x1");
		}
		double sum=0;
		double epsDivision=(x1-x0)/eps;
		double x=x0;
		for(int i=0;i<epsDivision;++i)
		{
			if(this.f(x)>0)
				sum=sum+this.f(x)*eps;
			x=x+eps;
		}

		return sum;
	}

	@Override
	public Iterator<Monom> iteretor() {

		return monomList.iterator();
	}
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> monoms=this.iteretor();
		Polynom ans=new Polynom();
		if(!this.isZero()||m1.isZero()) {
			while(monoms.hasNext()) {
				Monom temp=monoms.next();
				temp.multipy(m1);
				ans.add(temp);

			}
			this.monomList=ans.monomList;

		}
		else {
			this.monomList=ans.monomList;
		}
	}
	public String toString() {
		Iterator <Monom> monoms = this.iteretor();
		String ans = "";
		if(monomList.isEmpty()) 
			ans = "0";
		while(monoms.hasNext())
		{
			Monom temp=monoms.next();
			ans += temp.toString();
			if (monoms.hasNext())
				ans += "+ ";
		}
		return ans;
	}
}