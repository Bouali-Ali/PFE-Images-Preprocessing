import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class JPImage extends JPanel
{
	PImage img;
	int x=0,y=0;
	JPImage(PImage img1)
	{
		img = img1;
	}

	public void paintComponent(Graphics g)
	{
		int w,h,c;
		w = img.getWidth();
		h = img.getHeight();
		g.setColor(new Color(100,100,100));
		g.fillRect(0,0,w+(x*2),h+(y*2));
		for(int i = 0;i<h;i++)
		{
			for(int j = 0;j<w;j++)
			{
				c = (int)img.getPixel(i,j);
				g.setColor(new Color(c,c,c));
				g.fillRect(j+x, i+y, 1, 1);
			}
		}
		setPreferredSize(new Dimension( (w+(x*2)-10) , (h+(y*2)-10) ));
	}
	
	public void setXY(int x,int y)
	{
		this.x = x;
		this.y = y;
		repaint();
	}
}

public class DImage extends JPanel
{
	JPImage  panIm;
	PImage img;
	JScrollPane sPan = new JScrollPane();
	DImage(PImage img)
	{
		this.img = img;
		panIm = new JPImage (img);
		sPan.setViewportView(panIm);
		addComponentListener(new Resized());
		setLayout(new BorderLayout());
		add(sPan,BorderLayout.CENTER);
	}
	
	class Resized implements ComponentListener
	{
		public void componentResized(ComponentEvent e)
		{
			int x=0,y=0;
			if(img.getWidth() < getWidth())
			{
				x = (getWidth() - img.getWidth())/2;
			}
			if(img.getHeight() < getHeight())
			{
				y = (getHeight() - img.getHeight())/2;
			}
			panIm.setXY(x,y);
		}
		public void componentMoved(ComponentEvent e){}
		public void componentHidden(ComponentEvent e){}
		public void componentShown(ComponentEvent e){}	
	}
	
	
	public void setImage(PImage img)
	{
		this.img = img;
		panIm = new JPImage (img);
	}
	public PImage getImage()
	{
		return img;
	}
	
	/**public static void main(String []arg)
	{
		Image img = new Image("Image",600,399);
		img.readImage("fruits.raw",0);
		JFrame frm = new JFrame();
		frm.setPreferredSize(new Dimension(800,500));
		DImage panIm = new DImage(img);
		frm.add(panIm);
		frm.pack();
		frm.setVisible(true);
	}*/
}