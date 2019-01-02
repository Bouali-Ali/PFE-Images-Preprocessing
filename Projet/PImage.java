class PImage
{
	int height;
	int width;
	String pathName;
	String name;
	
	PImage(){}
	PImage(String nm,int w,int h)
	{
		name = nm;
		width = w;
		height = h;
	}
	
	public void Afficher(){}
	private void allocate(){}
	private void copier(char[][] cpix){}
	private void copier(Pixel[][] cpix){}
	private void copier(){}
//---------------------------- methodes 
	public String getName(){return null;}
	public void setName(String name1){}
	public int getWidth(){return 0;}
	public void setWidth(int width1){}
	public int getHeight(){return 0;}
	public void setHeight(int height){}
	public String getPathName(){return null;}
	public void setPathName(String pathName1){}
	
//------------------------------- return Pixels
	public char[][] getPixels(){return null;}
	public void setPixels(char pixels[][],int width,int height){}
	public Pixel[][] getCPixels(){return null;}
	public void setPixels(Pixel pixels[][],int width,int height){} 
	
//------------------------------- return un Pixel
	public char getPixel(int i ,int j){return (char)-1;}
	public void setPixel(char c,int i,int j){}
	public void setPixel(char c,int index){}
	public char getPixel(int index){return (char)-1;}
//---------------------------------- Special Methods 
    public void afficher(){}
    public void histogramme(int []histog){}
    public PImage binarise(int seuil){return null;}

//----------------------------------- Charger&&sauvegarder   
	public PImage readImage(String pathname,int lHead){return null;}
	public void saveImage(String pathname){}
	//---------------------------------- les operateurs	
    public PImage opEgal(PImage img){return null;}
    public PImage opNeg(){return null;}
	public  void opRead(FileIn in,int lHead){}
	public void opWrite(FileOut fout){}
	
	//----------------------------------
	public int getMinHisto(int []t){return 0;}
	public int getMaxHisto(int []t){return 0;}
	public PImage recadrage_Dynamique(){return null;}
	public PImage filtrageMoyenneur(){return null;}
	public PImage filtrageMoyenneur(int de){return null;}
	public PImage filtrageMoyenneur_Pondere(int de){return null;}
	public PImage equalization(){return null;}
	public double masqueGaussien(int i,int j,float o){return 0.0;}
	public PImage filtrageGaussienDe3(){return null;}
	public PImage filtrageGaussien(){return null;}
	public PImage filtrageGaussienDe15(){return null;}
	public PImage convolution(float [][]t,int d){return null;}
	public PImage filtrageSmooth(double delta){return null;}
	public PImage filtreMin(int n){return null;}
	public PImage filtreMax(int n){return null;}
	public PImage filtrageMediane(int d){return null;}
	public char valeurMediane(int x, int y,int d){return (char)-1;}
	public PImage filtrageDwMtm(float n,float k){return null;}
	public PImage flip(){return null;}
	public PImage filtrageGradient(){return null;}
	public PImage rotationm90(){return null;}
	public PImage rotation90(){return null;}
	public PImage filtreLissageMinmax(){return null;}
	public PImage filtrageAdaptatif(){return null;}
	public PImage contrastCorrection(float cor){return null;}
	public PImage contrastBrightness(float cor){return null;}
	public float moyenDeVoisin(int d,int i,int j){return 0;}
	public PImage filtrageNagao(){return null;}
	public PImage repoussage(){return null;}
	public PImage laplacien(){return null;}
	public PImage supR(){return null;}
	public PImage supG(){return null;}
	public PImage supB(){return null;}
	public PImage sobel(){return null;}
	/**-------------------------*/
	public char[] getTabPixels(){return null;}
	// return un Pixel
	public Pixel getCPixel(int i,int j){return null;}
	public void setCPixel(char r,char g,char b,int i,int j){}
	public void setCPixel(char r,char g,char b,int index){}
	public void setCPixel(Pixel p,int i,int j){}
	public Pixel getCPixel(int index){return null;}
	public void histogramme(Histog []histog){}
	// les operateurs
	public PImage toGImage(){return null;}
}
