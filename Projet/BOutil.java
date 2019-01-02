import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;

class NButton extends JButton
{
	NButton(ImageIcon icon)
	{
		super(icon);
		setBackground(NColor.CLR1);
		setBorderPainted(false);
		setMargin(new Insets(0,0,0,0));
	}
}

class NJToolBar extends JToolBar
{
	NJToolBar(int nb){super(nb);}
}
public class BOutil
{
	private NJToolBar toolBar = new NJToolBar(1);
	private NButton load = new NButton(new ImageIcon("Image/load.png")),
			save = new NButton(new ImageIcon("Image/save.png")),
			arriere =new NButton(new ImageIcon("Image/arriere.png")),
			avant = new NButton(new ImageIcon("Image/avant.png")),
			rot90 = new NButton(new ImageIcon("Image/rot90.png")),
			rotM90 = new NButton(new ImageIcon("Image/rotm90.png")),
			zoomP = new NButton(new ImageIcon("Image/zoomP.png")),
			zoomM = new NButton(new ImageIcon("Image/zoomm.png")),
			pcontra = new NButton(new ImageIcon("Image/pcontrast.png")),
			mcontra = new NButton(new ImageIcon("Image/mcontrast.png")),
			flip = new NButton(new ImageIcon("Image/flip.png"));
	private Color bg = NColor.CLR1;
	
	private PanCenter panCenter;
	BOutil(PanCenter pc)
	{
		panCenter = pc;
		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		toolBar.setBackground(bg);
		
		toolBar.addSeparator();
		toolBar.add(load);
		toolBar.add(save);
		toolBar.addSeparator();
		toolBar.add(arriere);
		toolBar.add(avant);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.add(rotM90);
		toolBar.add(rot90);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.add(zoomP);
		toolBar.add(zoomM);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.add(pcontra);
		toolBar.add(mcontra);
		toolBar.addSeparator();
		toolBar.add(flip);
		
		load.addActionListener(new Action());
		save.addActionListener(new Action());
		rot90.addActionListener(new Action());
		rotM90.addActionListener(new Action());
		arriere.addActionListener(new Action());
		avant.addActionListener(new Action());
		zoomP.addActionListener(new Action());
		zoomM.addActionListener(new Action());
		pcontra.addActionListener(new Action());
		mcontra.addActionListener(new Action());
		flip.addActionListener(new Action());
	}
	
	class Action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(load))
			{
				panCenter.appTraitement("mn1_item1");				
			}
			if(e.getSource().equals(save))
			{
				panCenter.appTraitement("mn1_item2");				
			}
			if(e.getSource().equals(arriere))
			{
				panCenter.appTraitement("mn2_item2");				
			}
			else if(e.getSource().equals(avant))
			{
				panCenter.appTraitement("mn2_item3");				
			}
			else if(e.getSource().equals(rot90))
			{
				panCenter.appTraitement("mn3_item2");
			}
			else if(e.getSource().equals(rotM90))
			{
				panCenter.appTraitement("mn3_item3");				
			}
			else if(e.getSource().equals(zoomP))
			{
				panCenter.appTraitement("mn5_item1");				
			}
			else if(e.getSource().equals(zoomM))
			{
				panCenter.appTraitement("mn5_item2");				
			}
			else if(e.getSource().equals(pcontra))
			{
				panCenter.appTraitement("pcontra");				
			}
			else if(e.getSource().equals(mcontra))
			{
				panCenter.appTraitement("mcontra");				
			}
			else if(e.getSource().equals(flip))
			{
				panCenter.appTraitement("mn4_1_item5");				
			}
		}
	}
	
	public JToolBar getJToolBar(){return toolBar;}
}