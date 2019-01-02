import java.io.*;     
import java.util.*;


class GImage extends PImage
{
	private char pixels[][];

//---------------------------- methodes 
	private void allocate()
	{
		pixels = null;
		pixels = new char [height][width];
		if(pixels == null)
			System.exit(0);
	}
	private void copier(char[][] cpix)
	{
		if(pixels !=null) pixels=null;
			allocate();
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				pixels[i][j]=cpix[i][j];
	}	
	
//----------------------------- Constructors
	GImage()
	{
		name=new String();
		width=height=0;
		allocate();
	}
	GImage(String name1,int width1,int height1)
	{
		name=new String(name1);
		width=width1;height=height1;
		allocate();
	}
	GImage(PImage img)
	{
		pathName=new String(img.pathName);
		width=img.width;
		height=img.height;
		allocate();
		copier(img.getPixels());
	}
	
	
//----------------------------- Getters and Setters
	public String getName(){ return name;}
	public void setName(String name1){name = name1;}
	public int getWidth(){return width;}
	public void setWidth(int width1){ width = width1;}
	public int getHeight() {return height;}
	public void setHeight(int height){ this.height = height;}
	public String getPathName(){return pathName;}
	public void setPathName(String ptn){pathName = ptn;}
//------------------------------- return Pixels
	public char[][] getPixels() {return pixels;}
	public void setPixels(char pixels[][],int width,int height) 
	{
		setWidth(width); setHeight(height);
		copier(pixels);
	}

	
//------------------------------- return un Pixel
	public char getPixel(int i ,int j){return pixels[i][j];}
	public void setPixel(char c,int i,int j){pixels[i][j]=c;}
	public void setPixel(char c,int index)
	{
		int j=index%width,i=index/width;
		pixels[i][j]=c;
	}
	
	public char getPixel(int index)
	{
		int j=index%width,i=index/width;
		return pixels[i][j];
	}
//---------------------------------- Special Methods 
    public void afficher() 
    {
        System.out.println("--- GImage : "+ name +" ---"); 
        for(int i=0;i<height;++i)
        {
            System.out.print("| ");
            for(int j=0;j<width;++j)
                System.out.print((int)pixels[i][j]+" ");
            System.out.println("|");
        }
        System.out.println("------  ------");
    }
    public void histogramme(int []histog)
    {
        int gris;
        for(int i=0;i<=255;++i)
            histog[i]=0;
        for(int i=0;i<height;++i)
           for(int j=0;j<width;++j)
           {
                gris=(int)getPixel(i,j);
				if(gris > 0 && gris < 256)
					histog[gris]++;
           }
    }
    public PImage binarise(int seuil)
	{
        int gris;
        GImage imgBin=new GImage(this);
        imgBin.name ="Binarise";
        for(int i=0;i<height;++i)
            for(int j=0;j<width;++j)
			{
                    gris=(int)imgBin.getPixel(i,j);
                    if(gris>seuil)
                        imgBin.setPixel((char)255,i,j);
                    else
                        imgBin.setPixel((char)0,i,j);
            }
        return imgBin;
    }

//----------------------------------- Charger&&sauvegarder   
	public PImage readImage(String pathname,int lHead)
	{
		pathName = pathname;
		FileIn in = new FileIn(pathname);
		opRead(in,lHead);   
		in.closeF();
		return this;
    }
    public void saveImage(String pathname)
	{
		FileOut out = new FileOut(pathname);
		opWrite(out);
		out.closeF();
    }
	//---------------------------------- les operateurs	
    public PImage opEgal(PImage img)
    {
		img = new GImage(this);
		return this;
    }
    public PImage opNeg()
    {
        int gris;
        GImage imgNeg=new GImage(this);
        imgNeg.name ="Negation";
        for(int i=0;i<height;++i)
            for(int j=0;j<width;++j)
            {
                gris=(int)imgNeg.getPixel(i,j);
                gris=255-gris;
                imgNeg.setPixel((char)gris,i,j);
            }
        return imgNeg;
    }
    public  void opRead(FileIn in,int lHead)
    {
		char u;
		char []head = new char[lHead];
		int f =  1;
		for(int i = 0; i<lHead ;i++)
			head[i] = in.getC();
        for(int i=0; i<height && f == 1 ;++i)
            for(int j=0; j<width  && f == 1;++j)
				if( (u=in.getC())!= (char)-1){
					setPixel((char)u,i,j);
				}
				else f = 0;
    }
    public void opWrite(FileOut fout)
    {
        for(int i=0; i<height;++i)
            for(int j=0; j<width;++j)
				fout.putC(getPixel(i,j));    
    }

	//----------------------------------
	public int getMinHisto(int []t)
	{

		for(int i=0; i<256; i++)
			if(t[i]!=0)
				return i;
		return -1;
	}
	
	public int getMaxHisto(int []t)
	{

		for(int i=255; i>-1; i--)
			if(t[i]!=0)
				return i;
		return -1;
	}
	/***/
	public PImage recadrage_Dynamique()/**--*/
	{
		GImage img = new GImage("Recadrage Dynamique",width,height);
		img.pathName = pathName;
		int []t = new int[256];
		histogramme(t);
		int a = getMinHisto(t);
		int b = getMaxHisto(t);
		char n;
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				if(getPixel(i,j)<a)
					img.setPixel((char)0,i,j);
				else if(getPixel(i,j)>a && getPixel(i,j)<b)
				{
					n =(char)((int)(getPixel(i,j))-a);
					img.setPixel((char)(255*n/(b-a)),i,j);
				}
				else
					img.setPixel((char)255,i,j);
			}
		}
		return img;
	}
	/***/
	public PImage filtrageMoyenneur()/**--*/
	{
		GImage temp = new GImage(this);
		temp.name = "filtrage Moyenneur";
		int som;
		int moy=0;
		for(int i=1; i<getHeight()-1; i++)
			for(int j=1; j<getWidth()-1; j++)
			{
				som=((int)temp.getPixel(i,j)+(int)temp.getPixel(i+1,j)+(int)temp.getPixel(i-1,j)
					 +(int)temp.getPixel(i,j-1)+(int)temp.getPixel(i+1,j-1)+(int)temp.getPixel(i-1,j-1)+
					 (int)temp.getPixel(i,j+1)+(int)temp.getPixel(i-1,j+1)+(int)temp.getPixel(i+1,j+1));
				moy=som/9;
				temp.setPixel((char)moy,i,j);
			}
		return temp;
	}
	/***/
	public PImage filtrageMoyenneur(int de)/**--*/
	{
		GImage temp= new GImage(this);
		temp.name = "filtrage Moyenneur de " + de;
		int som;
		int moy;
		int dee;
		int s=de/2;
		// if(de == 0) de = 1;
		for(int i=de/2; i<getHeight()-de/2; i++)
			for(int j=de/2; j<getWidth()-de/2; j++)
			{
				som=0;
				for(int k=-s; k<=s; k++)
				{
					for(int l=-s; l<=s; l++)
					{
						som+=getPixel(i+k,j+l);
					}
				}
				dee=de*de;
				moy=(som/dee);
				temp.setPixel((char)moy,i,j);
			}
		return temp;
	}
	/***/
	public PImage filtrageMoyenneur_Pondere(int de)/**--*/
	{
		GImage temp = new GImage(this);
		temp.name = "filtrage Moyenneur Pondere de "+ de;
		double som;
		float dee;
		double moy;
		int s=de/2;
		dee=(s*2*4+s*4+4);
		dee=(1/dee);
		System.out.println(dee);
		for(int i=s; i<getHeight()-s; i++)
			for(int j=s; j<getWidth()-s; j++)
			{
				som=0;
				for(int k=-s; k<=s; k++)
				{
					for(int l=-s; l<=s; l++)
					{
						if((k==0)||(l==0))
						{
							if((k==0)&&(l==0))
								som +=((int)getPixel(i+k,j+l)*4);
							else
								som +=((int)getPixel(i+k,j+l)*2);
						}
						else
							som =som + (int)(getPixel(i+k,j+l));
					}
				}
				moy=(som*dee);
				temp.setPixel((char)moy,i,j);
			}
		return temp;
	}
	public PImage equalization()
	{
		GImage temp = new GImage(this);
		temp.name = "Equalization";
		char k;
		int []his_equa = new int[256];
		double constant;
		int []his = new int[256];
		histogramme(his);
		for(int i=1; i<256; i++)
		{
			his_equa[i] =0;
			his_equa[i] = his_equa[i-1]+his[i];
		}
		constant=(getHeight() * getWidth());
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				k =getPixel(i,j);
				temp.setPixel((char)(his_equa[k]*255/constant),i,j);
			}
		}
		return temp;
	}
	public double masqueGaussien(int i,int j,float o)
	{
		double r = Math.pow(i,2)+Math.pow(j,2);
		double e=2*Math.pow(o,2);
		double ex =-r/e;
		double exx=Math.exp(ex);
		double a=2*(3.14)*Math.pow(o,2);
		double res=exx/a;
		return res;
	}
	public PImage filtrageGaussienDe3()/**--*/
	{
		GImage temp=(this);
		temp.name ="filtrage Gaussien de 3";
		Double som=0.0;
		double pix;
		double [][]t=new double[3][3];
		
		t[0][0] = 0.011;t[0][1] = 0.084;t[0][2] = 0.011;
		t[1][0] = 0.084;t[1][1] = 0.619;t[1][2] = 0.084;
		t[2][0] = 0.011;t[2][1] = 0.084;t[2][2] = 0.011;

		for(int i=1; i<getHeight()-1; i++)
		{
			for(int j=1; j<getWidth()-1; j++)
			{
				som=0.0;
				for(int k=-1; k<=1; k++)
					for(int l=-1; l<=1; l++)
					{
						pix=(double)getPixel(i+k,j+l);
						som+=t[1+k][1+l]*pix;
					}
	            if(som>255)
	                som=255.0;
	            if(som<0)
	                som=0.0;
				temp.setPixel((char)som.intValue(),i,j);
			}
		}
		return temp;
	}
	public PImage filtrageGaussien()/**--*/
	{
		GImage temp = new GImage(this);
		temp.name ="filtrage Gaussien";
		Double som=0.0;
		for(int i=4; i<getHeight()-4; i++)
		{
			for(int j=4; j<getWidth()-4; j++)
			{
				som=1*getPixel(i-3,j-3)+1*getPixel(i-3,j-2)+2*getPixel(i-3,j-1)+2*getPixel(i-3,j)+2*getPixel(i-3,j+1)+1*getPixel(i-3,j+2)+2*getPixel(i-3,j+3)+
					1*getPixel(i-2,j-3)+2*getPixel(i-2,j-2)+2*getPixel(i-2,j-1)+4*getPixel(i-2,j)+2*getPixel(i-2,j+1)+2*getPixel(i-2,j+2)+1*getPixel(i-2,j+3)+
					2*getPixel(i-1,j-3)+2*getPixel(i-1,j-2)+4*getPixel(i-1,j-1)+8*getPixel(i-1,j)+4*getPixel(i-1,j+1)+2*getPixel(i-1,j+2)+2*getPixel(i-1,j+3)+
					2*getPixel(i,j-3)+4*getPixel(i,j-2)+8*getPixel(i,j-1)+16*getPixel(i,j)+8*getPixel(i,j+1)+4*getPixel(i,j+2)+2*getPixel(i,j+3)+
					2*getPixel(i+1,j-3)+2*getPixel(i+1,j-2)+4*getPixel(i+1,j-1)+8*getPixel(i+1,j)+4*getPixel(i+1,j+1)+2*getPixel(i+1,j+2)+2*getPixel(i+1,j+3)+
					1*getPixel(i+2,j-1)+2*getPixel(i+2,j-2)+2*getPixel(i+2,j-1)+4*getPixel(i+2,j)+2*getPixel(i+2,j+1)+2*getPixel(i+2,j+2)+1*getPixel(i+2,j+3)+
					1*getPixel(i+3,j-3)+1*getPixel(i+3,j-2)+2*getPixel(i+3,j-1)+2*getPixel(i+3,j)+2*getPixel(i+3,j+1)+1*getPixel(i+3,j+2)+2*getPixel(i+3,j+3)+0.0;
				som=som/140;
				if(som>255)
					som=255.0;
				if(som<0)
					som=0.0;
				temp.setPixel((char)som.intValue(),i,j);
			}
		}
		return temp;
	}
	public PImage filtrageGaussienDe15()/**--*/
	{
    int [][]t = new int[15][15];
    t[0][0] = 2;t[0][1] = 2;t[0][2] = 3;t[0][3] = 4;t[0][4] = 5;t[0][5] = 5;t[0][6] = 6;t[0][7] = 6;t[0][8] = 6;t[0][9] = 5;t[0][10] = 5;t[0][11] = 4;t[0][12] = 3;						t[0][13] = 2;t[0][14] = 2;
    t[1][0] = 2;t[1][1] = 3;t[1][2] = 4;t[1][3] = 5;t[1][4] = 7;t[1][5] = 7;t[1][6] = 8;t[1][7] = 8;t[1][8] = 8;t[1][9] = 7;t[1][10] = 7;t[1][11] = 5;t[1][12] = 4;						t[1][13] = 3;t[1][14] = 2;
    t[2][0] = 3;t[2][1] = 4;t[2][2] = 6;t[2][3] = 7;t[2][4] = 9;t[2][5] = 10;t[2][6] = 10;t[2][7] = 11;t[2][8] = 10;t[2][9] = 10;t[2][10] = 9;t[2][11] = 7;t[2][12] = 6;				t[2][13] = 4;t[2][14] = 3;
    t[3][0] = 4;t[3][1] = 5;t[3][2] = 7;t[3][3] = 9;t[3][4] = 10;t[3][5] = 12;t[3][6] = 13;t[3][7] = 13;t[3][8] = 13;t[3][9] = 12;t[3][10] = 10;t[3][11] = 9;t[3][12] = 7;				t[3][13] = 5;t[3][14] = 4;
    t[4][0] = 5;t[4][1] = 7;t[4][2] = 9;t[4][3] = 11;t[4][4] = 13;t[4][5] = 14;t[4][6] = 15;t[4][7] = 16;t[4][8] = 15;t[4][9] = 14;t[4][10] = 13;t[4][11] = 11;t[4][12] = 9;			t[4][13] = 7;t[4][14] = 5;
    t[5][0] = 5;t[5][1] = 7;t[5][2] = 10;t[5][3] = 12;t[5][4] = 14;t[5][5] = 16;t[5][6] = 17;t[5][7] = 18;t[5][8] = 17;t[5][9] = 16;t[5][10] = 14;t[5][11] = 12;t[5][12] = 10;			t[5][13] = 7;t[5][14] = 5;
    t[6][0] = 6;t[6][1] = 8;t[6][2] = 10;t[6][3] = 13;t[6][4] = 15;t[6][5] = 17;t[6][6] = 19;t[6][7] = 19;t[6][8] = 19;t[6][9] = 17;t[6][10] = 15;t[6][11] = 13;t[6][12] = 10;			t[6][13] = 8;t[6][14] = 6;
    t[7][0] = 6;t[7][1] = 8;t[7][2] = 11;t[7][3] = 13;t[7][4] = 16;t[7][5] = 18;t[7][6] = 19;t[7][7] = 20;t[7][8] = 19;t[7][9] = 18;t[7][10] = 16;t[7][11] = 13;t[7][12] = 11;			t[7][13] = 8;t[7][14] = 6;
    t[8][0] = 6;t[8][1] = 8;t[8][2] = 10;t[8][3] = 13;t[8][4] = 15;t[8][5] = 17;t[8][6] = 19;t[8][7] = 19;t[8][8] = 19;t[8][9] = 17;t[8][10] = 15;t[8][11] = 13;t[8][12] = 10;			t[8][13] = 8;t[8][14] = 6;
    t[9][0] = 5;t[9][1] = 7;t[9][2] = 10;t[9][3] = 12;t[9][4] = 14;t[9][5] = 16;t[9][6] = 17;t[9][7] = 18;t[9][8] = 17;t[9][9] = 16;t[9][10] = 14;t[9][11] = 12;t[9][12] = 10;			t[9][13] = 7;t[9][14] = 5;
    t[10][0] = 5;t[10][1] = 7;t[10][2] = 9;t[10][3] = 11;t[10][4] = 13;t[10][5] = 14;t[10][6] = 15;t[10][7] = 16;t[10][8] = 15;t[10][9] = 14;t[10][10] = 13;t[10][11] = 11;t[10][12] = 9;t[10][13] = 7;t[10][14] = 5;
    t[11][0] = 4;t[11][1] = 5;t[11][2] = 7;t[11][3] = 9;t[11][4] = 10;t[11][5] = 12;t[11][6] = 13;t[11][7] = 13;t[11][8] = 13;t[11][9] = 12;t[11][10] = 10;t[11][11] = 9;t[11][12] = 7;	t[11][13] = 5;t[11][14] = 4;
    t[12][0] = 3;t[12][1] = 4;t[12][2] = 6;t[12][3] = 7;t[12][4] = 9;t[12][5] = 10;t[12][6] = 10;t[12][7] = 11;t[12][8] = 10;t[12][9] = 10;t[12][10] = 9;t[12][11] = 7;t[12][12] = 6;	t[12][13] = 4;t[12][14] = 3;
    t[13][0] = 2;t[13][1] = 3;t[13][2] = 4;t[13][3] = 5;t[13][4] = 7;t[13][5] = 7;t[13][6] = 8;t[13][7] = 8;t[13][8] = 8;t[13][9] = 7;t[13][10] = 7;t[13][11] = 5;t[13][12] = 4;		t[13][13] = 3;t[13][14] = 2;
    t[14][0] = 2;t[14][1] = 2;t[14][2] = 3;t[14][3] = 4;t[14][4] = 5;t[14][5] = 5;t[14][6] = 6;t[14][7] = 6;t[14][8] = 6;t[14][9] = 5;t[14][10] = 5;t[14][11] = 4;t[14][12] = 2;		t[14][13] = 2;t[14][14] = 2;
	

    Double som=0.0;
    GImage temp = new GImage(this);
	temp.name ="filtrage Gaussien de 15";
    for(int i=7; i<getHeight()-7; i++)
        for(int j=7; j<getWidth()-7; j++)
        {

            for(int k=-7; k<7; k++)
                for(int l=-7; l<7; l++)
                    som+=t[7+k][7+l]*getPixel(i+k,j+l);
            som=som/2044;
            if(som>255)
                som=255.0;
            if(som<0)
                som=0.0;
            temp.setPixel((char)som.intValue(),i,j);
        }
	return temp;
}
	public PImage convolution(float [][]t ,int d)
	{
		GImage temp = new GImage(this);
		temp.name ="convolution";
		float som=0;
		int de= d*d;
		int s=d/2;
		float pix;
		for(int i=s; i<getHeight()-s; i++)
			for(int j=s; j<getWidth()-s; j++)
			{

				for(int k=-s; k<=s; k++)
					for(int l=-s; l<=s; l++)
					{
						pix=(float)getPixel(i+k,j+l);
						som+=(t[s+k][s+l]*pix);
					}
				som /=de;
				if(som>255)
					som=255;
				if(som<0)
					som=0;
				temp.setPixel((char)som,i,j);
			}
		return temp;
	}
	public PImage filtrageSmooth(double delta)/**--*/
	{
		GImage temp =new GImage(this);
		temp.name ="filtrage Smooth";
		double r=(Math.pow(delta,2)*2*(3.15));
		double e,g;
		Double sum;
		double t=(2*Math.pow(delta,2));
		for(int i=2; i<getHeight()-2; i++)
		{
			for(int j=2; j<getWidth()-2; j++)
			{
				sum=0.0;
				for(int k=-2; k<=2; k++)
				{
					for(int l=-2; l<=2; l++)
					{
						e=(Math.exp(-(Math.pow(k,2)+Math.pow(l,2))/t));
						g=(e/r);
						sum+=(e*getPixel(i+k,j+l));
					}
				}
				temp.setPixel((char)sum.intValue(),i,j);
			}
		}
		return temp;
	}
	public PImage filtreMin(int n)/**--*/
	{
		GImage temp=new GImage(this);
		temp.name ="filtre Min de " + n;
		char [][]a = new char[n+1][n+1];

		int m=(n/2);
		int smin;
		for(int y=m; y < getHeight()-m; y++)
		{
			for(int x=m; x < getWidth()-m; x++)
			{
				smin=255;
				for(int j=-m; j<=m; j++)
					for(int i=-m; i<=m; i++)
					{
						a[i+m][j+m]=getPixel(x+i+(y+j)*getHeight());
						//  a[i+m][j+m]=this->getPixel(x+i,y+j);
					}
				for(int j=0; j<=n-1; j++)
				{
					for(int i=0; i<=n-1; i++)
					{
						if(a[i][j]<=smin)
							smin=a[i][j];
					}
				}
				temp.setPixel((char)smin,y,x);
			}
		}
			return temp;
	}
	public PImage filtreMax(int n)/**--*/
	{
		GImage temp=new GImage(this);
		temp.name ="filtre Max de "+n;
		char [][]a = new char[n+1][n+1];

		int m=(n/2);
		int s;
		for(int y=m; y<getHeight()-m; y++)
		{
			for(int x=m; x<getWidth()-m; x++)
			{
				s=0;
				for(int j=-m; j<=m; j++)
					for(int i=-m; i<=m; i++)
					{
						a[i+m][j+m]=getPixel(x+i+(y+j)*getHeight());
						//  a[i+m][j+m]=this->getPixel(x+i,y+j);
					}
				for(int j=0; j<=n-1; j++)
				{
					for(int i=0; i<=n-1; i++)
					{
						if(a[i][j]>s)
							s=a[i][j];
					}
				}
				temp.setPixel((char)s,y,x);
			}
		}
		return temp;
	}
	public PImage filtrageMediane(int d)/**--*/
	{
		GImage temp=new GImage(this);
		temp.name ="filtrage Mediane de " + d;
		int de=d*d;
		int retur=(de/2+1);
		int s=d/2;
		int []Tab = new int[de];
		int k,a,p;

		for(int x=s; x<getHeight()-s; x++)
		{
			for(int y=s; y<getWidth()-s; y++)
			{
				k=0;
				for(int i=-s; i<s; i++)
					for(int j=-s; j<s; j++)
					{
						Tab[k]=getPixel(x+i,y+j);
						k++;
					}

				for (int i=1; i<=de-1; i++)
				{
					a=Tab[i];
					p=i-1;
					while(p>=0 && Tab[p]>a)
					{
						Tab[p+1]=Tab[p];
						p=p-1;
					}
					Tab[p+1]=a;
				}
				temp.setPixel((char)Tab[retur],x,y);
			}
		}
		return temp;
	}
	public char valeurMediane(int x, int y,int d)
	{
		int tmp;
		int de=d*d;
		int s=(d/2);
		int retur=(de/2+1);
		int []Tab = new int[de];
		for(int i=-s; i<s; i++)
			for(int j=-s; j<s; j++)
				Tab[(s+i-1)*getHeight()+s+j]= (int)getPixel(x+i,y+j) ;

		for (int i=0; i<de; i++)
		{
			if (Tab[i] > Tab[i+1])
			{
				tmp = Tab[i];
				Tab[i] = Tab[i + 1];
				Tab[i + 1] = tmp;
			}
		}
		return (char) Tab[retur];
	}
	public PImage filtrageDwMtm(float n,float k)/**--*/
	{
		GImage tmp=new GImage(this);
		tmp.name ="filtrage DwMtm de "+n+" et "+k;
		int []tab = new int[9];
		int m,p;
		int g,temp;
		int total,sum;
		for(int x=2; x<getHeight()-2; x++)
		{
			for(int y=2; y<getWidth()-2; y++)
			{
				total=0;
				for(int xx=-1; xx<=1; xx++)
					for(int yy=-1; yy<=1; yy++)
					{
						tab[total]=getPixel(x+xx,y+yy);
						total++;
					}
				for(int j=1; j<9; j++)
				{
					temp=tab[j];
					p=j-1;
					while(p>=0 && tab[p]>temp)
					{
						tab[p+1]=tab[p];
						p=p-1;
					}
					tab[p+1]=temp;
				}				
				m=tab[4];
				sum=0;
				total=0;
				for(int yy=-2; yy<=2; yy++)
					for(int xx=-2; xx<=2; xx++)
					{
						g=getPixel(x+xx,y+yy);
						if(g>=(m-k*n))
							if(g<=(m+k*n))
							{
								sum+=g;
								total++;
							}
					}
				float val = sum/total;
				tmp.setPixel((char)val,x,y);
			}
		}
		return tmp;
	}
	public PImage flip()
	{
		GImage tmp = new GImage(this);
		tmp.name ="flip";
		int k;
		char temp;
		for(int i=0; i<getHeight(); i++)
		{
			k= getWidth()-1;
			for(int j=0; j<getWidth()/2; j++)
			{
				temp = getPixel(i,k);
				tmp.setPixel(getPixel(i,j),i,k);
				tmp.setPixel(temp,i,j);
				k--;
			}
		}
		return tmp;
	}
	public PImage filtrageGradient()
	{
		GImage temp=new GImage(this);
		temp.name ="Filtrage Gradient";
		int t[][]={{-2,-1,0},{-1,0,1},{0,1,2}};
	/**{-1,0,1,-2,0,2,1,2,1}; mal
	{-1,-2,-1,0,0,0,1,2,1};
	{0,-1,-2,1,0,-1,2,1,0};
	2,1,0,1,0,-1,0,-1,-2};un peu
	{1,0,-1,2,0,-2,1,0,-1};bien**/
		Double som=0.0;
		for(int i=1; i<getHeight()-1; i++)
			for(int j=1; j<getWidth()-1; j++)
			{

				for(int k=-1; k<=1; k++)
					for(int l=-1; l<=1; l++)
						som+=t[1+k][1+l]*getPixel(i+k,j+l);
				if(som>255)
					som=255.0;
				if(som<0)
					som=0.0;
				temp.setPixel((char)som.intValue(),i,j);
			}
		return temp;
	}
	public PImage rotationm90()/**--*/
	{
		int k;
		char temp;
		GImage tmp = new GImage("rotation -90",getHeight(),getWidth());
		tmp.pathName = pathName;
		k = getHeight()-1;
		for(int i=0; i<getHeight();i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				tmp.setPixel((char)getPixel(i,j),j,k);
			}
			k--;
		}
		return tmp;
	}
	public PImage rotation90()/**--*/
	{
		int k;
		char temp;
		GImage tmp = new GImage("rotation 90",getHeight(),getWidth());
		tmp.pathName = pathName;
		for(int i=0; i<getHeight();i++)
		{
			k = getWidth()-1;
			for(int j=0; j<getWidth(); j++)
			{
				tmp.setPixel((char)getPixel(i,j),k--,i);
			}
		}
		return tmp;
	}
	public PImage filtreLissageMinmax()
	{
		int [][]t =new int[3][3];
		int mi,ma,moy;
		GImage temp=new GImage(this);
		temp.name ="Filtre Lissage Min Max";
		for(int i=1; i<getHeight()-1; i++)
			for(int j=1; j<getWidth()-1; j++)
			{
				mi=255;
				ma=0;
				for(int k=-1; k<=1; k++)
					for(int l=-1; l<=1; l++)
					{
						t[1+k][1+l]=getPixel(i+k,j+l);
						
						if(t[1+k][1+l]>ma)
						{
							ma=t[1+k][1+l];
						}
						if(t[1+k][1+l]<mi)
						{
							mi=t[1+k][1+l];
						}
					}

				moy=((ma-mi)/2);
				if(getPixel(i,j)<moy)
					temp.setPixel((char)mi,i,j);
				else
					temp.setPixel((char)ma,i,j);
			}
		return temp;
	}	
	public PImage filtrageAdaptatif()/**--*/
	{
		GImage tmp=new GImage(this);
		tmp.name ="filtrage Adaptatif";
		float [][]tab = new float[3][3];
		float total,sum;
		float my;
		for(int x=1; x<getHeight()-1; x++)
			for(int y=1; y<getWidth()-1; y++)
			{
				my=(float)getPixel(x,y);
				sum=0;
				for(int a=-1; a<=1; a++)
					for(int b=-1; b<=1; b++)
					{
						tab[1+a][1+b]=(float)getPixel(x+a,y+b);
						tab[1+a][1+b]=(tab[1+a][1+b]-my);
						if (tab[1+a][1+b]<0)
							tab[1+a][1+b]=(0-tab[1+a][1+b]);
						sum+=tab[1+a][1+b];
					}
				total=(1/sum);
				total = 1/total;
				tmp.setPixel((char)total,x,y);
			}
		return tmp;
	}
	public PImage contrastCorrection(float cor)/**--*/
	{
		GImage tmp=new GImage(this);
		tmp.name ="Contrast Correction de "+cor;
		float j=0;
		float som=0,i,r = getHeight(),avg;
		for(int x=0;x<getHeight();x++)
			for(int y=0;y<getWidth();y++)
			{
				som+=getPixel(x,y);
				j++;
			}
		avg=(float)(som/j);
		for(int x=0;x<getHeight();x++)
			for(int y=0;y<getWidth();y++)
			{
				i=(cor*((float)getPixel(x,y)-avg)+avg);
				if(i>255)
					i=255;
				if(i<0)
					i=0;
				tmp.setPixel((char)i,x,y);
			}
		return tmp;
	}	
	public PImage contrastBrightness(float cor)/**--*/
	{
		 GImage tmp=new GImage(this);
		 tmp.name ="Contrast Brightness de " + cor;
		 float i=0;
		 for(int x=0;x<getHeight();x++)
			for(int y=0;y<getWidth();y++)
			{
				i = getPixel(x,y)+cor;
				if(i>255)
					i=255;
				if(i<0)
					i=0;
				tmp.setPixel((char)i,x,y);
			}
		return tmp;
	}
	public float moyenDeVoisin(int d,int i,int j)
	{  
		float som=0;
		int de=d*d;
		int s=d/2;
		for(int k=-s; k<=s; k++)
			for(int l=-s; l<=s; l++)
			{
				som+=(float)getPixel(i+k,j+l);
			}
		return (som/de);
	}
	public PImage filtrageNagao() /**--*/
	{
		GImage temp = new GImage(this);
		temp.name ="filtrage Nagao";
		float dee;
		for(int i=2; i<getHeight()-2; i++)
		{
			for(int j=2; j<getWidth()-2; j++)
			{
				dee=255;
				for(int k=-1; k<=1; k++)
				{
					for(int l=-1; l<=1; l++)
					{
						if(dee>moyenDeVoisin(3,i+k,j+l))
							dee=moyenDeVoisin(3,i+k,j+l);
					}
				}
				temp.setPixel((char)dee,i,j);
			}
		}
		return temp;
	}

	public PImage sobel()
	{
		PImage temp1 = new GImage(this);
		PImage temp2 = new GImage(this);
		PImage temp = new GImage(this);
		temp.name ="Sobel";
		int d = 3;
		float [][]t ={{-1,-2,-1},{0,0,0},{1,2,1}};
		temp1 = temp1.convolution(t,d);
		float [][]t2 ={{-1,0,1},{-2,0,2},{-1,0,1}};
		temp2 = temp2.convolution(t,d);
		double var; 
		for(int i=0; i<getHeight(); i++)
		{
			for(int j=0; j<getWidth(); j++)
			{
				var = Math.sqrt(Math.pow((int)temp1.getPixel(i,j),2)+Math.pow((int)temp2.getPixel(i,j),2));
				if(var > 255)
					var = 255;
				temp.setPixel((char)var,i,j);
			}
		}
		return temp;
	}
}