import javax.swing.*;      
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class Fenetre extends JFrame
{
	Menu menu;
	BOutil toolBar;
	PanCenter panCenter = new PanCenter();
	

	
	Fenetre()
	{
		setPreferredSize(new Dimension(1000,600));
		setTitle("Traitement d'image");
		try{
		setIconImage(ImageIO.read(new File("Image/logo.png")));
		}catch(IOException e){}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		toolBar =new BOutil(panCenter);
		menu = new Menu(panCenter);
		setJMenuBar(menu.getJMenuBar());
		this.getContentPane().add(toolBar.getJToolBar(), BorderLayout.WEST);
		this.getContentPane().add(panCenter, BorderLayout.CENTER);	
		
		pack();
		setVisible(true);
	}
}

public class test
{
	public static void main(String []arg)
	{
		new Fenetre();
	}
}