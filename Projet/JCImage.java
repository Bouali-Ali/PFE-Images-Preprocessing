import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.Dimension;

public class JCImage extends JComponent{
	BufferedImage image;
	int taille;
	int width,height;
	
	public JCImage(int t)
	{
		taille=t ; setPreferredSize(new Dimension(t,t));
	}
	
	public JCImage(PImage img,int t)
	{
	    super();
		width=img.getWidth();	height=img.getHeight();
		if(img instanceof GImage)
		{
			image = getImageFromArray(img.getPixels());
		}
		else
		{
			image = getCImageFromArray(img.getCPixels());
		}
		taille=t ;
		setPreferredSize(new Dimension(t,t));
	}

	public JCImage(BufferedImage img,int t)
	{
	    	super();
		width=img.getWidth();	height=img.getHeight();
	    	image=img;
		taille=t ;
		setPreferredSize(new Dimension(t,t));
	}

	public void setImage(PImage img)
	{
		width=img.getWidth();	height=img.getHeight();
	    	image=getImageFromArray(img.getPixels());
	}
	
	public void setImage(BufferedImage img)
	{
		width=img.getWidth();	height=img.getHeight();
	    	image=img;
	}

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
	
	int nw,nh;
	  if(Math.max(width,height)==width){
		nw=taille;
		nh=height*taille/width;
	  }else{
		nh=taille;
		nw=width*taille/height;
	  }
	  
	  int x,y;
	  x=(taille-nw)/2;	y=(taille-nh)/2;
	  
	  g.drawImage(image,x,y,nw,nh,null);	
    }

	protected static BufferedImage getImageFromArray(char[][] p)
	{
		int width=p[0].length,height=p.length; 
		int[] pixels=chartowDimToOneDimIntArray(p);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); 
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		image.setData(raster);
		return image;
	}
	
	protected static BufferedImage getCImageFromArray(Pixel[][] p)
	{
		int width=p[0].length,height=p.length; 
		int[] pixels= new int[width*height*3];
		int count = 0;
		for(int i = 0;i<height;i++)
		{
			for(int j = 0;j<width;j++)
			{
				pixels[count++] = (int)p[i][j].getR();
				pixels[count++] = (int)p[i][j].getG();
				pixels[count++] = (int)p[i][j].getB();
			}
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); 
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		image.setData(raster);
		return image;
	}
	
	protected static int [] chartowDimToOneDimIntArray(char[][] towDim){
		int []one=new int[towDim.length*towDim[0].length];
		for(int i=0;i<towDim.length;++i){
			for(int j=0;j<towDim[i].length;++j){
				one[i*(towDim[i].length)+j]=(int)towDim[i][j];
			}
		}
		return one;
	}
}
