//package tabbedpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;


import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class JTPClosable extends JTabbedPane{
	BufferedImage cross;
	private TabCloseUI closeUI = new TabCloseUI(this);
	private PanImage jpimg;
	private PanCenter jpCenter;
	
	public JTPClosable (PanImage jpm,PanCenter pnc)
	{
		super(JTabbedPane.TOP);
		jpimg = jpm;
		jpCenter = pnc;
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		try{
			cross=ImageIO.read(new File("image/redCross.png"));
		}catch(IOException ioe){}
		addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(getSelectedIndex() != -1)
				{
					jpimg.setInfo();
					jpCenter.actualiserJPDiv();
				}
				else 
				{
					jpCenter.initJPDiv();
					jpimg.initInfo();
				}
			}
		});
	
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		closeUI.paint(g);
	}
	
	public void addTab(String title, Component component) {
		super.addTab(title + "  ", component);
	}
	
	public String getTabTitleAt(int index) 
	{
		return super.getTitleAt(index).trim();
	}
	
	private class TabCloseUI implements MouseListener, MouseMotionListener 
	{
		private JTPClosable  tabbedPane;
		private int closeX = 0 ,closeY = 0, meX = 0, meY = 0;
		private int selectedTab;
		private final int  width = 8, height = 8;
		private Rectangle rectangle = new Rectangle(0,0,width, height);
		private TabCloseUI(){}
		public TabCloseUI(JTPClosable pane) 
		{	
			tabbedPane = pane;
			tabbedPane.addMouseMotionListener(this);
			tabbedPane.addMouseListener(this);
		}
		public void mouseEntered(MouseEvent me){}
		public void mouseExited(MouseEvent me){}
		public void mousePressed(MouseEvent me){}
		public void mouseClicked(MouseEvent me){}
		public void mouseDragged(MouseEvent me){}
		
		public void mouseReleased(MouseEvent me) {
			if(closeUnderMouse(me.getX(), me.getY())){
				boolean isToCloseTab = tabAboutToClose(selectedTab);
				if (isToCloseTab && selectedTab > -1){			
					tabbedPane.removeTabAt(selectedTab);
					jpimg.getTabDImg().remove(selectedTab);
					if(getSelectedIndex() != -1)
					{
						jpimg.setInfo();
						jpCenter.actualiserJPDiv();
					}
					else 
					{
						jpCenter.initJPDiv();
						jpimg.initInfo();
					}
				}
				selectedTab = tabbedPane.getSelectedIndex();
			}
		}

		public void mouseMoved(MouseEvent me) {	
			meX = me.getX();
			meY = me.getY();			
			if(mouseOverTab(meX, meY)){
				controlCursor();
				tabbedPane.repaint();
			}
		}

		private void controlCursor() {
			if(tabbedPane.getTabCount()>0)
				if(closeUnderMouse(meX, meY)){
					tabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));	
					if(selectedTab > -1)
						tabbedPane.setToolTipTextAt(selectedTab, "Close " +tabbedPane.getTitleAt(selectedTab));
				}
				else{
					tabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					if(selectedTab > -1)
						tabbedPane.setToolTipTextAt(selectedTab,"");
				}	
		}

		private boolean closeUnderMouse(int x, int y) {		
			rectangle.x = closeX;
			rectangle.y = closeY;
			return rectangle.contains(x,y);
		}

		public void paint(Graphics g) {
			int selected = tabbedPane.getSelectedIndex();
			if(selected != -1 ){
				int x = tabbedPane.getBoundsAt(selected).x + tabbedPane.getBoundsAt(selected).width -width-5;
				int y = tabbedPane.getBoundsAt(selected).y +5;	
				drawClose(g,x,y);

			}
			if(mouseOverTab(meX, meY)){
				drawClose(g,closeX,closeY);
			}
		}

		private void drawClose(Graphics g, int x, int y) {
			if(tabbedPane != null && tabbedPane.getTabCount() > 0){
				Graphics2D g2 = (Graphics2D)g;				
				drawColored(g2, isUnderMouse(x,y)? Color.RED : Color.WHITE, x, y);
			}
		}

		private void drawColored(Graphics2D g2, Color color, int x, int y) {
			g2.drawImage(cross,x,y,width,height,null);
		}

		private boolean isUnderMouse(int x, int y) {
			if(Math.abs(x-meX)<width && Math.abs(y-meY)<height )
				return  true;		
			return  false;
		}

		private boolean mouseOverTab(int x, int y) {
			int tabCount = tabbedPane.getTabCount();
			for(int j = 0; j < tabCount; j++)
				if(tabbedPane.getBoundsAt(j).contains(meX, meY)){
					selectedTab = j;
					closeX = tabbedPane.getBoundsAt(j).x + tabbedPane.getBoundsAt(j).width -width-5;
					closeY = tabbedPane.getBoundsAt(j).y +5;					
					return true;
				}
			return false;
		}

	}

	public boolean tabAboutToClose(int tabIndex) 
	{
		String tab = this.getTabTitleAt(tabIndex);
				int choice = JOptionPane.showConfirmDialog(null,
						"Voulez-vous fermer l'onglet : '" + tab
								+ "' ?",
						"Quitter", JOptionPane.INFORMATION_MESSAGE);
				return choice == 0; // if returned false tab closing will be
									// canceled
		//return true;
	}
}
