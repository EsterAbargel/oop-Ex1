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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import jdk.nashorn.api.scripting.JSObject;



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
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for(double i= ry.get_min(); i<=ry.get_max();i++) {
			StdDraw.line(rx.get_min(), i, rx.get_max(), i);
			StdDraw.text(0.1,i+0.1,i+""); 
		}
		for(double i=rx.get_min(); i<=rx.get_max(); i++) {
			StdDraw.line(i, ry.get_min(), i, ry.get_max());
			StdDraw.text(i+0.1,0.1,i+""); 
		}
	
		Random rand = new Random();
		int r = rand.nextInt(256); int g = rand.nextInt(256); int b = rand.nextInt(256);
		Color col=new Color(r,g,b);
		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(rx.get_min(),0, rx.get_max(), 0);
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());
		StdDraw.setPenColor(col);
		double step = (Math.abs(rx.get_min())+Math.abs(rx.get_max()))/res;
			for(double i =rx.get_min(); i< rx.get_max(); i=i+step) {
				StdDraw.line(i, f.f(i), i+step, f.f(i+step));
			}

		return col;
	}

	@Override
	public void drawFunctions(String json_file) {
		
		JsonParser parser = new JsonParser(); 
		int width = 1 , height=1 , resolution=1 ;

		try {
			Object obj = parser.parse(new FileReader(json_file));
			JsonObject jsonObject = (JsonObject) obj;
	        width = jsonObject.get("Width").getAsInt();
			height = jsonObject.get("Height").getAsInt();
			resolution = jsonObject.get("Resolution").getAsInt();
			JsonArray range_X = (JsonArray)jsonObject.get("Range_X");
			JsonArray range_Y = (JsonArray)jsonObject.get("Range_Y");	
			Range rx= new Range(range_X.get(0).getAsDouble(),range_X.get(1).getAsDouble());
			Range ry= new Range(range_Y.get(0).getAsDouble(),range_Y.get(1).getAsDouble());;
			this.drawFunctions(width, height, rx, ry,  resolution);

		}
		catch (FileNotFoundException e) {
			this.drawFunctions("GUI_params.json");
		}
		catch (IllegalArgumentException e) {
			this.drawFunctions("GUI_params.json");
		}
		catch (JsonSyntaxException e) {
			this.drawFunctions("GUI_params.json");

		}
		 
	}


	public function get(int i) {
		return funList.get(i);
	}

}
