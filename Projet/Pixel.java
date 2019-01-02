public class Pixel
{
	private char r;
	private char g;
	private char b;

	Pixel(){};
	Pixel(char r1,char g1,char b1){r = r1; g = g1; b = b1;}
	Pixel(Pixel p){r = p.r; g = p.g; b = p.b;}

	public void setR(char r1){r = r1;}
	public void setG(char g1){g = g1;}
	public void setB(char b1){b = b1;}

	public char getR(){return r;}
	public char getG(){return g;}
	public char getB(){return b;}
}