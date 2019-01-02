import java.io.*;
import java.util.*;

class Histog
{
	int r,g,b;
	Histog(){r=0;g=0;b=0;}
}

class CImage extends PImage
{
	private Pixel pixels[][];
	private char []tabPixels;
	
	private void allocate()
	{
		pixels = null;
		pixels = new Pixel [height][width];
	}
	
	private void copier(Pixel[][] cpix)
	{
		if(pixels != null) pixels=null;
			allocate();
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				pixels[i][j] = new Pixel(cpix[i][j].getR(),cpix[i][j].getG(),cpix[i][j].getB());
	}
	
	private void copier()
	{
		tabPixels = new char[height * width * 3];
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
			{
				tabPixels[i*width + j] = pixels[i][j].getR();	
				tabPixels[i*width + j + 1] = pixels[i][j].getG();	
				tabPixels[i*width + j + 2] = pixels[i][j].getB();
			}
	}
	
	//Constructors
	CImage()
	{
		name=new String();
		width=height=0;
	}
	CImage(String name1,int width1,int height1)
	{
		name=new String(name1);
		width=width1;height=height1;
		allocate();
	}
	CImage(PImage img)
	{
		name=new String(img.getName());
		pathName = new String(img.pathName);
		width=img.width;
		height=img.height;
		allocate();
		copier(img.getCPixels());
	}
	
	//Getters and Setters
	public String getPathName(){return pathName;}
	public void setPathName(String pathName1){pathName = pathName1;}
	public String getName(){return name;}
	public void setName(String name1){name = name1;}
	public int getWidth(){return width;}
	public void setWidth(int width1){ width = width1;}
	public int getHeight() {return height;}
	public void setHeight(int height){this.height = height;}
	/// return Pixels
	public Pixel[][] getCPixels() {return pixels;}
	public void setCPixels(Pixel pixels[][],int width,int height) 
	{
		setWidth(width); setHeight(height);
		copier(pixels);
	}
	public char[] getTabPixels()
	{
		copier();
		return tabPixels;
	}
	// return un Pixel
	public Pixel getCPixel(int i,int j){return pixels[i][j];}
	public void setCPixel(char r,char g,char b,int i,int j)
	{
		pixels[i][j] = new Pixel(r,g,b);
	}
	public void setCPixel(Pixel p,int i,int j)
	{
		pixels[i][j] = new Pixel(p);
	}
	public void setCPixel(char r,char g,char b,int index)
	{
		int j=index%width,i=index/width;
		pixels[i][j] = new Pixel(r,g,b);
	}
	public Pixel getCPixel(int index)
	{
		int j=index%width,i=index/width;
		return pixels[i][j];
	}
	
	//Special Methods 
    
	public void histogramme(Histog []histog)
    {
        for(int i=0;i<=255;++i)
            histog[i]=new Histog();
        Pixel p;
        for(int i=0;i<height;++i)
           for(int j=0;j<width;++j)
            {
                p = getCPixel(i,j);
                histog[(int)p.getR()].r++;
                histog[(int)p.getG()].g++;
                histog[(int)p.getB()].b++;
            }
    }
    public PImage binarise(int seuil) 
	{
        Pixel p;
		int moy;
        CImage imgBin=new CImage(this);
        imgBin.name ="Binarise";
        for(int i=0;i<height;++i)
            for(int j=0;j<width;++j)
			{
                    p = imgBin.getCPixel(i,j);
					moy = (p.getR() + p.getG() + p.getB() )/3;
                    if(moy>seuil)
                        imgBin.setCPixel((char)255,(char)255,(char)255,i,j);
                    else
                        imgBin.setCPixel((char)0,(char)0,(char)0,i,j);
            }
		return imgBin;
    }
	
	//Charger&&Sauvgarder
	public PImage readImage(String pathname , int lhead)
	{
		pathName =new String(pathname);
		FileIn in = new FileIn(pathname);
		opRead(in,lhead);   
		in.closeF();
		return this;
    }
    public void saveImage(String name)
	{
		FileOut out = new FileOut(name);
		opWrite(out);
		out.closeF();
    }
	// les operateurs
    public PImage opNeg()
    {
        Pixel p;
		int r,g,b;
        CImage imgNeg=new CImage(this);
        imgNeg.name ="Negation";
        for(int i=0;i<height;++i)
            for(int j=0;j<width;++j)
            {
                p = imgNeg.getCPixel(i,j);
                r=255-p.getR();
                g=255-p.getG();
                b=255-p.getB();
                imgNeg.setCPixel((char)r,(char)g,(char)b,i,j);
            }
        return imgNeg;
    }
	
    public  void opRead(FileIn in,int lHead)
    {
		char r,g,b;
		boolean f = true;
		
		char u;
		char []head = new char[lHead*3];
		for(int i = 0; i<lHead*3 ;i++)
			head[i] = in.getC();
        for(int i=0; i<height && f  ;++i)
            for(int j=0; j<width  && f ;++j)
				if((r=in.getC())!= (char)-1 && (g=in.getC())!= (char)-1 && (b=in.getC())!= (char)-1)
					setCPixel((char)r,(char)g,(char)b,i,j);
				else f = false;
    }
	
    public void opWrite(FileOut fout)
    {
		Pixel p ;
		for(int i=0; i<height;++i)
            for(int j=0; j<width;++j)
			{
				p = new Pixel(getCPixel(i,j));
				fout.putC(p.getR()); 
				fout.putC(p.getG()); 
				fout.putC(p.getB()); 
			}
    }
	
	public PImage toGImage()
	{
		PImage img = new GImage("Covertir en gris",width,height);
		img.setPathName(pathName);
		for(int i=0;i<height;++i)
		{
            for(int j=0;j<width;++j)
			{
				Pixel p = getCPixel(i,j);
				int moy = ((int)p.getR() + (int)p.getG() + (int)p.getB() )/3;
				img.setPixel((char)moy,i,j);
            }
		}
		return img;
	}
	
	
	public PImage filtrageMoyenneur()
	{
		CImage temp = new CImage(this);
		temp.name = "filtrage Moyenneur";
		int r;
		int g;
		int b;
		int var;
		int moy=0;
		for(int i=1; i<getHeight()-1; i++)
			for(int j=1; j<getWidth()-1; j++)
			{
				r=((int)temp.getCPixel(i,j).getR()+(int)temp.getCPixel(i+1,j).getR()+(int)temp.getCPixel(i-1,j).getR()
					 +(int)temp.getCPixel(i,j-1).getR()+(int)temp.getCPixel(i+1,j-1).getR()+(int)temp.getCPixel(i-1,j-1).getR()+
					 (int)temp.getCPixel(i,j+1).getR()+(int)temp.getCPixel(i-1,j+1).getR()+(int)temp.getCPixel(i+1,j+1).getR());
				g=((int)temp.getCPixel(i,j).getG()+(int)temp.getCPixel(i+1,j).getG()+(int)temp.getCPixel(i-1,j).getG()
					 +(int)temp.getCPixel(i,j-1).getG()+(int)temp.getCPixel(i+1,j-1).getG()+(int)temp.getCPixel(i-1,j-1).getG()+
					 (int)temp.getCPixel(i,j+1).getG()+(int)temp.getCPixel(i-1,j+1).getG()+(int)temp.getCPixel(i+1,j+1).getG());
				b=((int)temp.getCPixel(i,j).getB()+(int)temp.getCPixel(i+1,j).getB()+(int)temp.getCPixel(i-1,j).getB()
					 +(int)temp.getCPixel(i,j-1).getB()+(int)temp.getCPixel(i+1,j-1).getB()+(int)temp.getCPixel(i-1,j-1).getB()+
					 (int)temp.getCPixel(i,j+1).getB()+(int)temp.getCPixel(i-1,j+1).getB()+(int)temp.getCPixel(i+1,j+1).getB());
				r=r/9;
				g=g/9;
				b=b/9;
				temp.setCPixel((char)r,(char)g,(char)b,i,j);
			}
		return temp;
	}
	
	public PImage flip()
	{
		CImage tmp = new CImage(this);
		tmp.name ="flip";
		int k;
		Pixel temp;
		for(int i=0; i<getHeight(); i++)
		{
			k= getWidth()-1;
			for(int j=0; j<getWidth()/2; j++)
			{
				temp = getCPixel(i,k);
				tmp.setCPixel(getCPixel(i,j),i,k);
				tmp.setCPixel(temp,i,j);
				k--;
			}
		}
		return tmp;
	}
	public PImage rotationm90()/**--*/
	{
		int k;
		Pixel temp;
		CImage tmp = new CImage("rotation -90",getHeight(),getWidth());
		tmp.pathName = pathName;
		k = getHeight()-1;
		for(int i=0; i<getHeight();i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				tmp.setCPixel(getCPixel(i,j),j,k);
			}
			k--;
		}
		return tmp;
	}
	public PImage rotation90()/**--*/
	{
		int k;
		Pixel temp;
		CImage tmp = new CImage("rotation 90",getHeight(),getWidth());
		tmp.pathName = pathName;
		for(int i=0; i<getHeight();i++)
		{
			k = getWidth()-1;
			for(int j=0; j<getWidth(); j++)
			{
				tmp.setCPixel(getCPixel(i,j),k--,i);
			}
		}
		return tmp;
	}
	public PImage contrastCorrection(float cor)/**--*/
	{	
		CImage tmp=new CImage(this);
		tmp.name ="Contrast Correction de "+cor;
		float j=0;
		float somR=0,somG=0,somB=0,i,avgR,avgG,avgB;
		float r,g,b;
		for(int x=0;x<getHeight();x++)
			for(int y=0;y<getWidth();y++)
			{
				somR+=(float)getCPixel(x,y).getR();
				somG+=(float)getCPixel(x,y).getG();
				somB+=(float)getCPixel(x,y).getB();
				j++;
			}
		avgR=somR/j;
		avgG=somG/j;
		avgB=somB/j;
		for(int x=0;x<getHeight();x++)
			for(int y=0;y<getWidth();y++)
			{
				r=(cor*((float)getCPixel(x,y).getR()-avgR)+avgR);
				g=(cor*((float)getCPixel(x,y).getG()-avgG)+(int)avgG);
				b=(cor*((float)getCPixel(x,y).getB()-avgB)+(int)avgB);
				if(r>255)
					r=255;
				if(r<0)
					r=0;
				if(g>255)
					g=255;
				if(g<0)
					g=0;
				if(b>255)
					b=255;
				if(b<0)
					b=0;
					
				tmp.setCPixel((char)r,(char)g,(char)b,x,y);
			}
		return tmp;
	}	
	public PImage convolution(float [][]t ,int d)
	{
		CImage temp = new CImage(this);
		temp.name ="convolution";
		float somR=0;
		float somG=0;
		float somB=0;
		int de= d*d;
		int s=d/2;
		float pixR;
		float pixG;
		float pixB;
		for(int i=s; i<getHeight()-s; i++)
			for(int j=s; j<getWidth()-s; j++)
			{

				for(int k=-s; k<=s; k++)
					for(int l=-s; l<=s; l++)
					{
						pixR=(float)getCPixel(i+k,j+l).getR();
						pixG=(float)getCPixel(i+k,j+l).getG();
						pixB=(float)getCPixel(i+k,j+l).getB();
						somR+=(t[s+k][s+l]*pixR);
						somG+=(t[s+k][s+l]*pixG);
						somB+=(t[s+k][s+l]*pixB);
					}
				somR/=de;
				if(somR>255)
					somR=255;
				if(somR<0)
					somR=0;
					
				somG/=de;
				if(somG>255)
					somG=255;
				if(somG<0)
					somG=0;
					
				somB/=de;
				if(somB>255)
					somB=255;
				if(somB<0)
					somB=0;
				temp.setCPixel((char)somR,(char)somG,(char)somB,i,j);
			}
		return temp;
	}
	public PImage contrastBrightness(float cor)/**--*/
	{
		 CImage tmp=new CImage(this);
		 tmp.name ="Contrast Brightness de " + cor;
		 float r=0,g=0,b=0;
		 for(int x=0;x<getHeight();x++)
			for(int y=0;y<getWidth();y++)
			{
				r = (float)getCPixel(x,y).getR()+cor;
				if(r>255)
					r=255;
				if(r<0)
					r=0;
				g = (float)getCPixel(x,y).getG()+cor;
				if(g>255)
					g=255;
				if(g<0)
					g=0;
				b = (float)getCPixel(x,y).getB()+cor;
				if(b>255)
					b=255;
				if(b<0)
					b=0;
				tmp.setCPixel((char)r,(char)g,(char)b,x,y);
			}
		return tmp;
	}
	public PImage repoussage()
	{
		CImage temp = new CImage(this);
		float [][]t = {{-2,-1,0},{-1,1,1},{0,1,2}};
		int d = 3;
		temp.name ="Repoussage";
		float somR=0;
		float somG=0;
		float somB=0;
		int de= d*d;
		int s=d/2;
		float pixR;
		float pixG;
		float pixB;
		for(int i=s; i<getHeight()-s; i++)
			for(int j=s; j<getWidth()-s; j++)
			{
				for(int k=-s; k<=s; k++)
					for(int l=-s; l<=s; l++)
					{
						pixR=(float)getCPixel(i+k,j+l).getR();
						pixG=(float)getCPixel(i+k,j+l).getG();
						pixB=(float)getCPixel(i+k,j+l).getB();
						somR+=(t[s+k][s+l]*pixR);
						somG+=(t[s+k][s+l]*pixG);
						somB+=(t[s+k][s+l]*pixB);
					}
				//somR/=de;
				if(somR>255)
					somR=255;
				if(somR<0)
					somR=0;
					
				//somG/=de;
				if(somG>255)
					somG=255;
				if(somG<0)
					somG=0;
					
				//somB/=de;
				if(somB>255)
					somB=255;
				if(somB<0)
					somB=0;
				temp.setCPixel((char)somR,(char)somG,(char)somB,i,j);
			}
		return temp;
	}
	public PImage laplacien()
	{
		CImage temp = new CImage(this);
		float [][]t = {{1,1,1},{1,-8,1},{1,1,1}};
		int d = 3;
		temp.name ="Repoussage";
		float somR=0;
		float somG=0;
		float somB=0;
		int de= d*d;
		int s=d/2;
		float pixR;
		float pixG;
		float pixB;
		for(int i=s; i<getHeight()-s; i++)
			for(int j=s; j<getWidth()-s; j++)
			{
				for(int k=-s; k<=s; k++)
					for(int l=-s; l<=s; l++)
					{
						pixR=(float)getCPixel(i+k,j+l).getR();
						pixG=(float)getCPixel(i+k,j+l).getG();
						pixB=(float)getCPixel(i+k,j+l).getB();
						somR+=(t[s+k][s+l]*pixR);
						somG+=(t[s+k][s+l]*pixG);
						somB+=(t[s+k][s+l]*pixB);
					}
				somR/=de;
				if(somR>255)
					somR=255;
				if(somR<0)
					somR=0;
					
				somG/=de;
				if(somG>255)
					somG=255;
				if(somG<0)
					somG=0;
					
				somB/=de;
				if(somB>255)
					somB=255;
				if(somB<0)
					somB=0;
				temp.setCPixel((char)somR,(char)somG,(char)somB,i,j);
			}
		return temp.opNeg();
	}
	public PImage supR()
	{
		CImage tmp = new CImage(this);
		tmp.name ="Supprimer le rouge";
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				tmp.getCPixel(i,j).setR((char)0);
			}
		}
		return tmp;
	}
	public PImage supG()
	{
		CImage tmp = new CImage(this);
		tmp.name ="Supprimer le vert";
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				tmp.getCPixel(i,j).setG((char)0);
			}
		}
		return tmp;
	}
	public PImage supB()
	{
		CImage tmp = new CImage(this);
		tmp.name ="Supprimer le blue";
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				tmp.getCPixel(i,j).setB((char)0);
			}
		}
		return tmp;
	}
	public PImage filtrageAdaptatif()/**--*/
	{
		CImage tmp=new CImage(this);
		tmp.name ="filtrage Adaptatif";
		float [][]tabR = new float[3][3];
		float [][]tabG = new float[3][3];
		float [][]tabB = new float[3][3];
		float totalB,sumB,totalR,sumR,totalG,sumG;
		float myR,myG,myB;
		for(int x=1; x<getHeight()-1; x++)
			for(int y=1; y<getWidth()-1; y++)
			{
				myB=(float)getCPixel(x,y).getB();
				myG=(float)getCPixel(x,y).getG();
				myR=(float)getCPixel(x,y).getR();
				sumB=0;sumR=0;sumG=0;
				for(int a=-1; a<=1; a++)
					for(int b=-1; b<=1; b++)
					{
						tabR[1+a][1+b]=(float)getCPixel(x+a,y+b).getR();
						tabR[1+a][1+b]=(tabR[1+a][1+b]-myR);
						if (tabR[1+a][1+b]<0)
							tabR[1+a][1+b]=(0-tabR[1+a][1+b]);
						sumR+=tabR[1+a][1+b];
						
						tabG[1+a][1+b]=(float)getCPixel(x+a,y+b).getG();
						tabG[1+a][1+b]=(tabG[1+a][1+b]-myG);
						if (tabG[1+a][1+b]<0)
							tabG[1+a][1+b]=(0-tabG[1+a][1+b]);
						sumG+=tabG[1+a][1+b];
						
						tabB[1+a][1+b]=(float)getCPixel(x+a,y+b).getB();
						tabB[1+a][1+b]=(tabB[1+a][1+b]-myB);
						if (tabB[1+a][1+b]<0)
							tabB[1+a][1+b]=(0-tabB[1+a][1+b]);
						sumB+=tabB[1+a][1+b];
					}
				totalR =(1/sumR);
				totalR = 1/totalR;
				totalG =(1/sumG);
				totalG = 1/totalG;
				totalB =(1/sumB);
				totalB = 1/totalB;
				tmp.setCPixel((char)totalR,(char)totalG,(char)totalB,x,y);
			}
		return tmp;
	}
	
	public PImage sobel()
	{
		PImage temp1 = new CImage(this);
		PImage temp2 = new CImage(this);
		PImage temp  = new CImage(this);
		temp.name ="Sobel";
		int d = 3;
		float [][]t ={{-1,-2,-1},{0,0,0},{1,2,1}};
		temp1 = temp1.convolution(t,d);
		float [][]t2 ={{-1,0,1},{-2,0,2},{-1,0,1}};
		temp2 = temp2.convolution(t,d);
		double varR,varB,varG; 
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				varR = Math.sqrt(Math.pow((int)temp1.getCPixel(i,j).getR(),2)+Math.pow((int)temp2.getCPixel(i,j).getR(),2));
				if(varR > 255)
					varR = 255;
				varG = Math.sqrt(Math.pow((int)temp1.getCPixel(i,j).getG(),2)+Math.pow((int)temp2.getCPixel(i,j).getG(),2));
				if(varG > 255)
					varG = 255;
				varB = Math.sqrt(Math.pow((int)temp1.getCPixel(i,j).getB(),2)+Math.pow((int)temp2.getCPixel(i,j).getB(),2));
				if(varB > 255)
					varB = 255;
				temp.setCPixel((char)varR,(char)varG,(char)varB,i,j);
			}
		}
		return temp;
	}
}









