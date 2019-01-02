import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Histogramme extends JPanel
{
	/*Gris*/
	int []hstg = new int[256];
	PImage img;
	char c;
	int max = 100;
	int val= 0;
	
	/*Couleur*/
	private static final long serialVersionUID = 1L;
	private BufferedImage image, histogramme;
	private final int largeur = 261;
	private final int hauteur = 120;
	private Graphics2D dessin;
	private final int[] rouge = new int[256];
	private final int[] vert = new int[256];
	private final int[] bleu = new int[256];
   
   
	Histogramme(){}
	Histogramme(int []h)
	{
		c = 'G';
		hstg = h;
		max = getMax();
		setPreferredSize(new Dimension(261,120));
	}
	Histogramme(PImage img1)
	{
		setPreferredSize(new Dimension(261,120));
		c = 'C';
		Pixel [][]p = img1.getCPixels();
		int[] pixels= new int[img1.getWidth()*img1.getHeight()*3];
		int count = 0;
		for(int i = 0;i<img1.getHeight();i++)
		{
			for(int j = 0;j<img1.getWidth();j++)
			{
				pixels[count++] = (int)p[i][j].getR();
				pixels[count++] = (int)p[i][j].getG();
				pixels[count++] = (int)p[i][j].getB();
			}
		}		
		image = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_3BYTE_BGR); 
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, img1.getWidth(), img1.getHeight(), pixels);
		image.setData(raster);
		recupererRVB();
		tracerHistogrammes();
	}
	public void paintComponent(Graphics g)
	{
		if(c == 'G')
		{
			int i;
			float y;
			g.setColor(NColor.CLR7);
			g.fillRect(0, 0, 261, 120);
			g.setColor(NColor.CLR8);
			g.fillRect(0, 15, 2, 105);
			g.fillRect(258, 15, 2, 105);
			g.fillRect(0, 117, 261, 3);
			g.setColor(NColor.CLR9);
			for(i=0;i<256;i++)
			{
				y = (hstg[i]*100)/max;
				y = 117-y;
				g.drawLine( i+3, (int)y, i+3, 117);
			}
		}
		else if(c == 'C')
		{	
			g.setColor(NColor.CLR7);
			g.fillRect(0, 0, 261, 120);
			g.drawImage(histogramme,0, 0, null);
		}
	}
	public int getMax()
	{
		int i=0;
		max = hstg[0];
		for(i=1;i<256;i++)
		{
			if(hstg[i]>max)
			{
				max = hstg[i];
			}
		}
		return max;
	}
	public void setHstg(int []hst)
	{
		hstg = hst;
	}
   
   private void recupererRVB() {
      Raster trame = image.getRaster();
      int[] rgb = new int[3];
      int maximum = 0;
      for (int y=0; y<image.getHeight(); y++)
         for (int x=0; x<image.getWidth(); x++) {
            trame.getPixel(x, y, rgb);
            rouge[rgb[0]]++;
            vert[rgb[1]]++;
            bleu[rgb[2]]++;
         }           
   }
   
   private void tracerHistogrammes() {
      histogramme = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
      dessin = histogramme.createGraphics();
      Rectangle2D rectangle = new Rectangle2D.Double(0, 0, largeur-1, hauteur-1);
      dessin.draw(rectangle);
      dessin.setPaint(new Color(1F, 1F, 1F, 0.2F));
      dessin.fill(rectangle);      
      changerAxes();
      dessin.setPaint(new Color(1F, 0F, 0F, 0.4F));
      tracerHistogramme(rouge);
      dessin.setPaint(new Color(0F, 1F, 0F, 0.4F));
      tracerHistogramme(vert);
      dessin.setPaint(new Color(0F, 0F, 1F, 0.4F));
      tracerHistogramme(bleu);
   }

   private void changerAxes() {
      dessin.translate(0, hauteur);
      double surfaceImage = image.getWidth()*image.getHeight();
      double surfaceHistogramme = histogramme.getWidth()*histogramme.getHeight();
      dessin.scale(1, -surfaceHistogramme/surfaceImage/3.7);      
   }   
      
   private void tracerHistogramme(int[] couleur) {
      for (int i=0; i<255; i++) 
         dessin.drawLine(i, 0, i, couleur[i]);              
   } 
}
