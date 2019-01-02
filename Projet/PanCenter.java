import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.*;
import java.awt.BorderLayout;

public class PanCenter extends JPanel
{
	private PanImage panImage;
	public static JPanel JPDiv = new JPanel();/*.........*/
	private Color col = NColor.CLR10;
	public static JPInfo pInf = new JPInfo();/**.......*/
	
	Histogramme hst = new Histogramme();
	
	PanCenter()
	{	
		 panImage = new PanImage(this);
		setLayout(new BorderLayout());
		JPDiv.setBackground(col);
		JPDiv.setLayout(new BorderLayout());
		initJPDiv();
		add(panImage,BorderLayout.CENTER);
		add(pInf,BorderLayout.SOUTH);	
		add(JPDiv,BorderLayout.EAST);
	}
	
	public void appTraitement(String str)
	{
		panImage.appTraitement(str);
		actualiserJPDiv();
	}
	
	public void initHistog()
	{
		if(panImage.getImage() instanceof CImage)
		{
			hst = new Histogramme(panImage.getImage());
		}
		else if(panImage.getImage() instanceof GImage)
		{
			int []h = new int[256];
			PImage img = panImage.getImage();
			img.histogramme(h);
			hst = new Histogramme(h);
		}
		JPDiv.add(hst,BorderLayout.NORTH);
	}
	public void initOrigImg()
	{
		panImage.setOrigImg();	
	}
	public void initHistor()
	{
		panImage.setHstor();
	}
	public void actualiserJPDiv()
	{
		JPDiv.removeAll();
		initHistog();
		initOrigImg();
		initHistor();	
	}
	public void initJPDiv()
	{
		JPDiv.removeAll();
		JPDiv.add(new JPanel(),BorderLayout.NORTH);
		JPDiv.add(new JPanel(),BorderLayout.CENTER);
		JPDiv.add(new JPanel(),BorderLayout.SOUTH);
	}
	
	public PanImage getPanImage(){return panImage;}
}