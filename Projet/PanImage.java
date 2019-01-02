import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.lang.Object;
import javax.swing.filechooser.FileNameExtensionFilter;         
import javax.swing.filechooser.FileFilter;    
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.JOptionPane; 
import javax.swing.JTextField; 
import javax.swing.JLabel; 
import javax.swing.JRadioButton; 
import javax.swing.ButtonGroup; 
import javax.swing.JButton;
import javax.swing.Icon; 
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

public class PanImage extends JPanel
{
	private JTPClosable panOngle;
	private LinkedList<GImageLL> tabDImg = new LinkedList<GImageLL>(); 
	private PanCenter jpCenter; 
	private boolean apTrt = true;
	JTextField [][] jtfconv = new JTextField[1][1];
	int etat = -1;
	PanImage(PanCenter pnc)
	{
		jpCenter = pnc;
		panOngle = new JTPClosable(this,jpCenter);
		setLayout(new BorderLayout());
		add(panOngle,BorderLayout.CENTER);
	}
	public void chargerFichier()
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e)
		{
			
		}
			JFileChooser fileOuvrirImage = new JFileChooser();
		fileOuvrirImage.setAcceptAllFileFilterUsed(false);
		String exetension[]={"raw"};
		FileFilter imagesFilter = new FileNameExtensionFilter("raw",exetension);
		fileOuvrirImage.addChoosableFileFilter(imagesFilter);
		
		if (fileOuvrirImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
		{
			String pathname = fileOuvrirImage.getSelectedFile().getAbsolutePath();
			JRadioButton RGris = new JRadioButton("Image gris");
			JRadioButton RColor = new JRadioButton("Image couleur");
			ButtonGroup R = new ButtonGroup();
			JPanel jp = new JPanel(new FlowLayout());
			R.add(RGris);R.add(RColor);
			RGris.setSelected(true);
			jp.add(RGris);jp.add(RColor);
			JLabel labelWidth = new JLabel ("Width :");
			JLabel labelHeight = new JLabel ("Height :");
			JTextField Width = new JTextField();
			JTextField Height = new JTextField();
			
			Object [] tab_obj_afficher = new Object []{jp,labelWidth , Width , labelHeight , Height };
			int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher ,fileOuvrirImage.getSelectedFile().getName(),JOptionPane.OK_CANCEL_OPTION ,	
			JOptionPane.INFORMATION_MESSAGE,NColor.icn ,	null ,	null);
	
			if (rep == JOptionPane.OK_OPTION)
			{
				String name = fileOuvrirImage.getSelectedFile().getName();
				if(RGris.isSelected())
				{
					PImage img = new GImage(name,Integer.valueOf(Width.getText ()),Integer.valueOf(Height.getText()));
					img.readImage(pathname,0);
					addOngle(img,name);	
				}
				else
				{
					PImage img = new CImage(name,Integer.valueOf(Width.getText ()),Integer.valueOf(Height.getText()));
					img.readImage(pathname,0);
					addOngle(img,name);	
				}
			}
			
		}
	}
	public void addOngle(PImage img,String name)
	{
		GImageLL gImg = new GImageLL(new JCZoomableImage(img),new JCZoomableImage(img),new HistoriqueLIFO(this));
		tabDImg.add(gImg);
		panOngle.add(name,gImg.getNGImage().getScrolled());
		panOngle.setSelectedIndex(panOngle.getTabCount()-1);
		setInfo();
		setOrigImg();
	}
	public void setImage(JCZoomableImage img,int index)
	{
		JCZoomableImage pImg = new JCZoomableImage(img.getImage());
		panOngle.setComponentAt(index,img.getScrolled()); //men ba3d
	}
	public void setImage(PImage img)
	{
		int index = panOngle.getSelectedIndex();
		JCZoomableImage pImg = new JCZoomableImage(img);
		panOngle.setComponentAt(index,pImg.getScrolled()); //men ba3d
		tabDImg.get(index).getNGImage().setImage(img);
		jpCenter.actualiserJPDiv();
	}
	public void saveImage1()
	{
		int index = panOngle.getSelectedIndex();		
		//Image img = tabDImg[index][1].getImage();
		PImage img = tabDImg.get(index).getNGImage().getImage();
		img.saveImage(img.getPathName());
	}
	public void saveImage2()
	{
		JFileChooser fileEnregistrerImage = new JFileChooser();
		if (fileEnregistrerImage.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
		{
			String pathName = fileEnregistrerImage.getSelectedFile().getAbsolutePath();
			int index = panOngle.getSelectedIndex();		
			PImage img = tabDImg.get(index).getNGImage().getImage();
			img.saveImage(pathName+".raw");
		}
	}
	public String pathToName(String pathname)
	{
		String name =new String("");
		int i = pathname.length(),d=0;
		i-=4;
		char c = pathname.charAt(i);
		while((c = pathname.charAt(i)) != '\\') d=i--;
		for(i=d;i<pathname.length();i++)
			name+=pathname.charAt(i);
		return name;
	}
	public void appTraitement(String str)
	{
		int index = panOngle.getSelectedIndex();
		if(str.equals("mn1_item1"))//ouvrir **
		{
			chargerFichier();
		}
		else if(str.equals("mn1_item2"))//enreg **
		{
			saveImage1();
		}
		else if(str.equals("mn1_item3")) //enreg sous
		{
			saveImage2();
		}
		else if(str.equals("mn1_item4"))//enreg tous **
		{
			for(int i=0;i<20;i++)
			{
				try
				{		
					PImage img = tabDImg.get(i).getOGImage().getImage();
					img.saveImage(img.getPathName());
				}catch(Exception e){};
			}
		}
		else if(str.equals("mn1_item5"))//fermer
		{
			System.exit(0);
		}
		else if(str.equals("mn2_item1"))//annuler
		{
			tabDImg.get(index).getHsImage().avar(etat);
			etat*=-1;
			apTrt = false;
		}
		else if(str.equals("mn2_item2"))//Pas-en-arriere
		{
			tabDImg.get(index).getHsImage().avar(-1);
			apTrt = false;
		}
		else if(str.equals("mn2_item3"))//Pas-en-avant
		{
			tabDImg.get(index).getHsImage().avar(1);
			apTrt = false;
		}
		else if(str.equals("mn2_item4"))//annuler tous
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getOGImage().getImage());
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn3_item1"))//taille **
		{
			
		}
		else if(str.equals("mn3_item2"))//rot90
		{
			PImage img = tabDImg.get(index).getNGImage().getImage();
			img = img.rotation90();
			tabDImg.get(index).getNGImage().setImage(img);
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn3_item3"))//rotm90
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().rotationm90());
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn3_item4"))//rot180
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().rotationm90());
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().rotationm90());
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_1_item1"))//bin
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Binarisation", "Entrer une valeur numerique",JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().binarise(Integer.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_1_item2"))//neg
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().opNeg());
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_1_item3"))//Recadrage dynamique
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().recadrage_Dynamique());
			setImage(tabDImg.get(index).getNGImage(),index);				
		}
		else if(str.equals("mn4_1_item4"))//Convolution
		{
			JPanel jpconv = new JPanel(),jpan = new JPanel(new FlowLayout());
			
			JTextField tf1=new JTextField(3);
			
			JLabel lb1 = new JLabel("Matrice des coefficients:"),lb2 = new JLabel("taille de la Matrice");
			
			
			Object [] tab_obj_afficher  = new Object []{lb2,tf1};
			int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher ,"Taille de la matrice",JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE,NColor.icn ,	null ,	null);
			if (rep == JOptionPane.OK_OPTION)
			{
				int var = Integer.parseInt(tf1.getText());
				jpconv.setLayout(new GridLayout(var,var));
				jtfconv=new  JTextField[var][var];
				for(int i=0;i<var;i++)
				{
					for(int j=0;j<var;j++)
					{
						jtfconv[i][j] = new JTextField(2);
						jpconv.add(jtfconv[i][j]);
					}
				}
				tab_obj_afficher  = new Object []{lb1,jpconv,lb2};
				rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher ,"Convolution",JOptionPane.OK_CANCEL_OPTION ,	
						JOptionPane.INFORMATION_MESSAGE,NColor.icn ,	null ,	null);
				if (rep == JOptionPane.OK_OPTION)
				{
					float [][]tabconv = new float[var][var];
					for(int i=0;i<var;i++)
					{
						for(int j=0;j<var;j++)
						{
							tabconv[i][j] =Float.parseFloat(jtfconv[i][j].getText());
						}	
					}
					tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().convolution(tabconv,var));
					setImage(tabDImg.get(index).getNGImage(),index);	
				}
			}
		}
		else if(str.equals("mn4_1_item5"))//flip
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().flip());
			setImage(tabDImg.get(index).getNGImage(),index);			
		}
		else if(str.equals("mn4_1_item6"))//filtre Gradient
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageGradient());
			setImage(tabDImg.get(index).getNGImage(),index);			
		}
		else if(str.equals("mn4_1_item7"))//filtre Lissage Minmax
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtreLissageMinmax());
			setImage(tabDImg.get(index).getNGImage(),index);			
		}
		else if(str.equals("mn4_1_item8"))//equalization
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().equalization());
			setImage(tabDImg.get(index).getNGImage(),index);			
		}			
		else if(str.equals("mn4_2_item1"))//F-Moyenneur
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageMoyenneur());
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_2_item2"))//F-Moyenneur1 **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre moyenneur", "Entrer une valeur numerique pour ce filtre",JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageMoyenneur(Integer.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_2_item3"))//F-M-Pondere **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre moyenneur Pondere", "Entrer une valeur numerique pour ce filtre",JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageMoyenneur(Integer.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);			
		}
		else if(str.equals("mn4_2_item4"))//F-Dw-Mtm **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre Dw-Mtm", "Entrer la 1ere valeur numerique pour ce filtre",JOptionPane.QUESTION_MESSAGE);
			String w1 = jop.showInputDialog(null, "Filtre Dw-Mtm", "Entrer la 2eme valeur numerique pour ce filtre",JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageDwMtm(Float.valueOf(w),Float.valueOf(w1)));
			setImage(tabDImg.get(index).getNGImage(),index);				
		}
		else if(str.equals("mn4_2_item5"))//F-smooth **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre smooth", "Entrer une valeur numerique pour ce filtre",
						JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageSmooth(Float.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);
						
		}
		else if(str.equals("mn4_2_1_item1"))//F-Gaussien
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageGaussien());
			setImage(tabDImg.get(index).getNGImage(),index);	
		}
		else if(str.equals("mn4_2_1_item2"))//F-Gaussien3
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageGaussienDe3());
			setImage(tabDImg.get(index).getNGImage(),index);					
		}
		else if(str.equals("mn4_2_1_item3"))//F-Gaussien15
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageGaussienDe15());
			setImage(tabDImg.get(index).getNGImage(),index);					
		}
		else if(str.equals("mn4_3_item1"))//F-median **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre mediane", "Entrer une valeur numerique pour ce filtre",
						JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageMediane(Integer.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_3_item2"))//F-Nagao
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageNagao());
			setImage(tabDImg.get(index).getNGImage(),index);					
		}
		else if(str.equals("mn4_3_item3"))//F-Minimum **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre Minimum", "Entrer une valeur numerique pour ce filtre",
						JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtreMin(Integer.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn4_3_item4"))//F-Maximum **
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre Maximum", "Entrer une valeur numerique pour ce filtre",
						JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtreMax(Integer.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);			
		}
		else if(str.equals("mn4_3_item5"))//Filtre Adaptatif
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().filtrageAdaptatif());
			setImage(tabDImg.get(index).getNGImage(),index);					
		}
		else if(str.equals("mn4_3_item6"))//Filtre Correction
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre Correction", "Entrer une valeur numerique pour ce filtre",
						JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().contrastCorrection(Float.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);	
		}
		else if(str.equals("mn4_3_item7"))//Filtre Brighteness
		{
			JOptionPane jop = new JOptionPane();
			String w = jop.showInputDialog(null, "Filtre Brighteness", "Entrer une valeur numerique pour ce filtre",
						JOptionPane.QUESTION_MESSAGE);
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().contrastBrightness(Float.valueOf(w)));
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("mn5_item1"))//Z-plus **
		{
			tabDImg.get(index).getNGImage().setScale(1);	
			setImage(tabDImg.get(index).getNGImage(),index);
			apTrt = false;
		}
		else if(str.equals("mn5_item2"))//Z-moin **
		{
			tabDImg.get(index).getNGImage().setScale(-1);
			setImage(tabDImg.get(index).getNGImage(),index);	
			apTrt = false;
		}
		else if(str.equals("mn5_item3"))//Z-100 
		{
			tabDImg.get(index).getNGImage().setPreferedScale(1);
			setImage(tabDImg.get(index).getNGImage(),index);
			apTrt = false;	
		}
		else if(str.equals("mn5_item4"))//Z-200 **
		{
			tabDImg.get(index).getNGImage().setPreferedScale(2);	
			setImage(tabDImg.get(index).getNGImage(),index);
			apTrt = false;	
		}	
		else if(str.equals("mn7_1_item3"))//togris
		{	
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().toGImage());
			setImage(tabDImg.get(index).getNGImage(),index);
		}
		else if(str.equals("pcontra"))//contra ++
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().contrastBrightness(5));
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mcontra"))//contra --
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().contrastBrightness(-5));
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mn7_1_1_item1"))//SUPR
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().supR());
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mn7_1_1_item2"))//supG
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().supG());
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mn7_1_1_item3"))//supB
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().supB());
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mn7_2_item4"))//Filtre Repoussage
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().repoussage());
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mn7_2_item5"))//Filtre laplacien
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().laplacien());
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		else if(str.equals("mn4_1_item9"))//Filtre Sobel
		{
			tabDImg.get(index).getNGImage().setImage(tabDImg.get(index).getNGImage().getImage().sobel());
			setImage(tabDImg.get(index).getNGImage(),index);						
		}
		
		
		if(index != -1 && apTrt == true)
			tabDImg.get(index).getHsImage().add(tabDImg.get(index).getNGImage().getImage());
		apTrt = true;
	}
	public PImage getImage()
	{
		int index = panOngle.getSelectedIndex();
		return (tabDImg.get(index).getNGImage().getImage());
	}
	public void setInfo()
	{
		int index = panOngle.getSelectedIndex();
		PImage img = tabDImg.get(index).getOGImage().getImage();
		PanCenter.pInf.setDim(new Dimension(img.getWidth(),img.getHeight()));
		PanCenter.pInf.setLoc(img.getPathName());
	}
	public void initInfo()
	{
		PanCenter.pInf.setDim(new Dimension(0,0));
		PanCenter.pInf.setLoc("Path name");
	}
	public void setOrigImg()
	{
		int index = panOngle.getSelectedIndex();
		PanCenter.JPDiv.add(new JCImage(tabDImg.get(index).getOGImage().getImage(),261),BorderLayout.SOUTH);
	}
	public void setHstor()
	{
		int index = panOngle.getSelectedIndex();
		PanCenter.JPDiv.add(tabDImg.get(index).getHsImage().getHistorique(),BorderLayout.CENTER);	
	}
	public LinkedList<GImageLL> getTabDImg(){return tabDImg;}

}