package Ex1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.google.gson.Gson;


public class Functions_GUI implements functions {
	private ArrayList<function> funList;

	public Functions_GUI() {
		this.funList = new ArrayList<function>();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.funList.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.funList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return o instanceof function&&this.funList.contains(o);
	}

	@Override
	public Iterator<function> iterator() {

		return this.funList.iterator();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.funList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return this.toArray(a);
	}

	@Override
	public boolean add(function e) {
		// TODO Auto-generated method stub
		return this.funList.add(e);
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return this.funList.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.funList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		// TODO Auto-generated method stub
		return this.funList.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.funList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.funList.retainAll(c);
	}

	@Override
	public void clear() {
		this.funList.clear();

	}

	@Override
	public void initFromFile(String file) throws IOException {
		function f =  new ComplexFunction(new Polynom());
		String line ="";

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
				funList.add(((function) f).initFromString(line));
			}
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	
	}

	@Override
	public void saveToFile(String file) throws IOException {
		try {
			PrintWriter pw = new PrintWriter(new File(file));
			StringBuilder sb=new StringBuilder();
			Iterator <function> funcIter =this.iterator();
			while (funcIter.hasNext()) {
				sb.append(funcIter.next().toString()+"\n");
			}
			pw.write(sb.toString());
			pw.close();
		}
		catch  (FileNotFoundException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		int i=0;
		for (Iterator<function> iterator = funList.iterator(); iterator.hasNext();) {

			function fun = (function) iterator.next();
			Color colorFun=draw(fun, rx, ry,resolution);
			System.out.println(i+") "+colorFun.toString() + "   f(x)=" + fun.toString());
			i++;
		}

	}
	public Color draw(function f,Range rx, Range ry, int res) {
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max()); 
		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());		
		double[] x=new double[res+1];
		double[] y=new double[res+1];
		double x_steps=(rx.get_max()-rx.get_min())/(res);
		double n=rx.get_min();

		for (int i = 0; i < res; i++) {
			x[i]=x_steps*i+n;
			y[i]=f.f(x[i]);	
		}
		for (double i=ry.get_min(); i<ry.get_max(); i++)
		{
			StdDraw.setPenColor(Color.gray);
			StdDraw.setPenRadius(0.002);
			StdDraw.line(rx.get_min(),i,rx.get_max(),i);

		}
		for (double i=rx.get_min(); i<rx.get_max(); i++)
		{
			StdDraw.setPenColor(Color.gray);
			StdDraw.setPenRadius(0.0005);
			StdDraw.line(i,ry.get_min(),i,ry.get_max());
		}
		StdDraw.setPenColor(Color.red); StdDraw.setPenRadius(0.005);
		for (int i = (int) ry.get_min(); i <= ry.get_max(); i=i+1) {
			if(i!=0)
				StdDraw.text((rx.get_min()/(rx.get_min()-rx.get_max()))-0.05, i+0.05, Integer.toString(i));
		}
		for (int i = (int) rx.get_min(); i <= rx.get_max(); i=i+1) {
			if(i!=0)
				StdDraw.text(i+0.07 ,ry.get_min()/(ry.get_min()-ry.get_max())-0.05, Integer.toString(i));
		}
		Random random = new Random();
		int r = random.nextInt(256); 
		int g = random.nextInt(256); 
		int b = random.nextInt(256);
		StdDraw.setPenColor(r,g,b); StdDraw.setPenRadius(0.005);
		for (int i = 0; i < res-1; i++) {
			StdDraw.line(x[i], y[i], x[i+1], y[i+1]);
		}

		return new Color(r,g,b);
	}

	@Override
	public void drawFunctions(String json_file) {
		Gson gson = new Gson();
		try 
		{
			FileReader fr=new FileReader (json_file);
			Graph g=gson.fromJson(fr,Graph.class);
			this.drawFunctions(g.Width, g.Height, new Range(g.rx[0],g.rx[1]) ,new Range(g.ry[0],g.ry[1]), g.resolution);
		}
		
		catch (FileNotFoundException e) {
			this.drawFunctions("error");
		}
	
		
	}


	public function get(int i) {
		return funList.get(i);
	}

}
