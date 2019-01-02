import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Color;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;

public class JPInfo extends JPanel
{
	JLabel lbDim,lbLoc ,lbPos;
	Dimension dim;
	Point pos;
	String loc;
	JPInfo()
	{
		setLayout(new GridLayout(1,3));
		dim = new Dimension(0,0);
		pos = new Point(0,0);
		loc = new String("<html><font style=\"text-decoration : underline; font-size : 11px;\">Path name</font></html> : ");
		lbDim = new JLabel("");
		lbLoc = new JLabel("");
		lbPos = new JLabel("");
		setBackground(NColor.CLR12);
		afficher();	
	}
	JPInfo(Point pos1,Dimension dim1,String loc1)
	{
		setLayout(new GridLayout(1,3));
		dim = new Dimension(dim1);
		pos = new Point(pos1);
		loc = new String(loc1);
		afficher();
	}
	public void afficher()
	{
		Double var = pos.getX();
		int x = var.intValue();
		var = pos.getY();
		int y = var.intValue();
		var = dim.getWidth();
		int w= var.intValue();
		var = dim.getHeight();
		int h = var.intValue();
		
		String sPos = "<html><font style=\"font-family : verdana;text-decoration : underline; font-size : 11px;\">Position</font> : x = " + x + " , y = " + y +"</html>";
		String sDim = "<html><font style=\"text-decoration : underline; font-size : 11px;\">Dimension</font> : " + w + " x " + h + "</html>";
		lbPos.setText(sPos);
		lbDim.setText(sDim);
		lbLoc.setText(loc);
		removeAll();
		add(lbLoc);add(lbDim);add(lbPos);
	}
	/**public void paintComponent(Graphics g)
	{
		BufferedImage bg;
		try{
			bg=ImageIO.read(new File("bgImage/bas.jpg"));
			g.drawImage(bg,0,0,getWidth(),bg.getHeight(),null);	
		}catch(IOException ioe){}
	}*/
	public void setLoc(String loc1){loc = new String(loc1); afficher();}
	public void setPos(Point pos1){pos = new Point(pos1); afficher();}
	public void setDim(Dimension dim1){dim = new Dimension(dim1); afficher();}
}