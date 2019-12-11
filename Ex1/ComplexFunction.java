package Ex1;

public class ComplexFunction implements complex_function {
	private function left,right;
	private Operation op;

	public ComplexFunction(function f1,function f2,Operation oper) {
		this.left=f1;
		this.right=f2;
		this.op=oper;
		if(f1==null) {
			this.left=null;
		}
		if(f2==null) {
			this.right=null;
		}
	}
	public ComplexFunction(function f1,function f2,String s){
		Operation op=this.findOperation(s);
		ComplexFunction cf=new ComplexFunction(f1,f2,op);
		this.left=cf.left;
		this.right=cf.right;
		this.op=cf.op;
	}
	public ComplexFunction(function f) {
		this.left=f;
		this.op=Operation.None;
		this.right=null;

	}

	public Operation findOperation(String operation){
		operation=operation.toLowerCase();
		switch(operation) {
		case "plus":
			return Operation.Plus;
		case "div":
			return Operation.Divid;
		case "times":
			return Operation.Times;
		case "mul":
			return Operation.Times;
		case "max":
			return Operation.Max;
		case "min":
			return Operation.Min;
		case "comp":
			return Operation.Comp;
		case "none":
			return Operation.None;
		case "error":
			return Operation.Error;
		}
		throw new IllegalArgumentException("couldn't find the operation");
	}
	@Override
	public double f(double x) {
		double ans=0;
		switch(this.op) {
		case Plus:
			return ans+this.left.f(x)+this.right.f(x);
		case Times:
			return ans+this.left.f(x)*this.right.f(x);
		case Divid:
			return ans+this.left.f(x)/this.right.f(x);
		case Max:
			return ans+Math.max(this.left.f(x),this.right.f(x));
		case Min:
			return ans + Math.min(this.left.f(x), this.right.f(x));
		case Comp:
			return ans + this.left.f(this.right.f(x));
		case Error:
			throw new IllegalArgumentException("Error operation");
		case None:
			return this.left.f(x);
		}
		return ans;

	}
	@Override
	public String toString() {	
		switch (this.op){
		case Comp:
			return "comp("+this.left.toString()+","+this.right.toString()+")";
		case Divid:
			return "div("+this.left.toString()+","+this.right.toString()+")";
		case Error:
			throw new IllegalArgumentException("Error operation");
		case Max:
			return "max("+this.left.toString()+","+this.right.toString()+")";
		case Min:
			return "min("+this.left.toString()+","+this.right.toString()+")";
		case None:
			return this.left.toString();
		case Plus:
			return "plus("+this.left.toString()+","+this.right.toString()+")";
		case Times:
			return "mul("+this.left.toString()+","+this.right.toString()+")";
		}
		return "";
	}

	@Override
	public function initFromString(String s) {
		s=s.replaceAll(" ", "");
		int index=0;
		if(!s.contains(")") && !s.contains("(")) {
			Polynom p= new Polynom(s);
			function f=new ComplexFunction(p);
			return f;
		}
		else {

			while (s.charAt(index) != '(') {
				index++;
			}

			int comma=findComma(s , index+1);
			String leftFun=s.substring(index+1, comma);
			String rightFun=s.substring(comma+1,s.length()-1);
			Operation oper = this.findOperation(s.substring(0, index));
			function left = initFromString(leftFun);
			function right = initFromString(rightFun);
			function function= new ComplexFunction(left,right,oper);
			return function;
		}
	}
	public int findComma(String str ,int index) {

		int comma=0; 
		int startBracket=1;
		for (; index < str.length(); index++) {

			if(str.charAt(index)==',') {
				comma++;
			}
			if(str.charAt(index)=='(') {
				startBracket++;
			}
			if( str.charAt(index) == ',' && (comma == startBracket)) {

				return index;
			}

		}		
		return 0;

	}

	@Override
	public function copy() {
		return new ComplexFunction(this.left,this.right,this.op);
	}

	@Override
	public void plus(function f1) {
		function temp=this.copy();
		this.op=Operation.Plus;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void mul(function f1) {
		function temp=this.copy();
		this.op=Operation.Times;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void div(function f1) {
		function temp=this.copy();
		this.op=Operation.Divid;
		this.right=f1;
		this.left=temp;
	}

	@Override
	public void max(function f1) {
		function temp=this.copy();
		this.op=Operation.Max;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void min(function f1) {
		function temp=this.copy();
		this.op=Operation.Min;
		this.right=f1;
		this.left=temp;

	}

	@Override
	public void comp(function f1) {
		function temp=this.copy();
		this.op=Operation.Comp;
		this.right=f1;
		this.left=temp;
	}

	@Override
	public function left() {

		return this.left;
	}

	@Override
	public function right() {

		return this.right;
	}

	@Override
	public Operation getOp() {

		return this.op;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof function)) {
			return false;
		}
		else{
			function cf = this.initFromString(obj.toString());
			for(double x = -1; x <= 1; x += 0.01) {
				if(Math.abs(this.f(x) - cf.f(x)) > Monom.EPSILON) {
					return false;
				}
			}
			return true;
		}

	}
}
