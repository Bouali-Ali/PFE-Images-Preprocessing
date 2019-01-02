import javax.swing.KeyStroke;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel; 
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

class NMenu extends JMenu
{
	NMenu(String str)
	{
		super(str);
		Font f = new Font("Default",Font.BOLD,13);
		setBackground(NColor.CLR5);
		setFont(f);
		setForeground(NColor.CLR6);
	}
}
class NMenuItem extends JMenuItem
{
	NMenuItem(String str)
	{
		super(str);
		Font f = new Font("Default",Font.BOLD,13);
		setBackground(NColor.CLR2);
		setFont(f);
		setForeground(NColor.CLR3);
	}
}
public class Menu
{
	private PanCenter panCenter;
	private JMenuBar menuBar = new JMenuBar();
	
	private NMenu mn1 = new NMenu("Fichier");
	private NMenu mn2 = new NMenu("Edition");
	private NMenu mn3 = new NMenu("Image");
	private NMenu mn3_mn1 = new NMenu("Rotation");
	private NMenu mn4 = new NMenu("Gris");
	private NMenu mn5 = new NMenu("Affichage");
	private NMenu mn6 = new NMenu("Aide");
	private NMenu mn7 = new NMenu("Couleur");
	private NMenu mn8 = new NMenu("Filtres");
	
	private NMenuItem mn6_item1 = new NMenuItem("A propos");
	//fichier
	private NMenuItem mn1_item1 = new NMenuItem("Ouvrir");
	private NMenuItem mn1_item2 = new NMenuItem("Enregistrer");
	private NMenuItem mn1_item3 = new NMenuItem("Enregistrer sous");
	private NMenuItem mn1_item4 = new NMenuItem("Enregistrer tous");
	private NMenuItem mn1_item5 = new NMenuItem("Fermer");
	
	//editeur
	private NMenuItem mn2_item1 = new NMenuItem("Annuler");
	private NMenuItem mn2_item2 = new NMenuItem("pas en arriere");
	private NMenuItem mn2_item3 = new NMenuItem("pas en avant");
	private NMenuItem mn2_item4 = new NMenuItem("Annuler tous");
	
	//Image
	private NMenuItem mn3_item1 = new NMenuItem("Proprietes");
	private NMenuItem mn3_item2 = new NMenuItem("Rotation 90°");
	private NMenuItem mn3_item3 = new NMenuItem("Rotation -90°");
	private NMenuItem mn3_item4 = new NMenuItem("Rotation 180°");

	
	//filtre
	
	private NMenu mn4_1 = new NMenu("Filtres Globaux");
	private NMenuItem mn4_1_item1 = new NMenuItem("Binarise");
	private NMenuItem mn4_1_item2 = new NMenuItem("Negation");
	private NMenuItem mn4_1_item3 = new NMenuItem("Recadrage dynamique");
	private NMenuItem mn4_1_item4 = new NMenuItem("Convolution");
	private NMenuItem mn4_1_item5 = new NMenuItem("flip");
	private NMenuItem mn4_1_item6 = new NMenuItem("filtre Gradient");
	private NMenuItem mn4_1_item7 = new NMenuItem("filtre Lissage");
	private NMenuItem mn4_1_item8 = new NMenuItem("equalization");
	private NMenuItem mn4_1_item9 = new NMenuItem("filtre Sobel");
	
	private NMenu mn4_2 = new NMenu("lineaires");
	private NMenuItem mn4_2_item1 = new NMenuItem("Filtre Moyenneur");
	private NMenuItem mn4_2_item2 = new NMenuItem("Filtre Moyenneur1");
	private NMenuItem mn4_2_item3 = new NMenuItem("Filtre Moyenneur Pondere");
	private NMenuItem mn4_2_item4 = new NMenuItem("Filtre Dw-Mtm");
	private NMenuItem mn4_2_item5 = new NMenuItem("Filtre smooth");
	private NMenu mn4_2_1 = new NMenu("Filtres Gaussien");
	private NMenuItem mn4_2_1_item1 = new NMenuItem("Filtre Gaussien");
	private NMenuItem mn4_2_1_item2 = new NMenuItem("Filtre Gaussien de 3");
	private NMenuItem mn4_2_1_item3 = new NMenuItem("Filtre Gaussien de 15");
	

	private NMenu mn4_3 = new NMenu("non-lineaires");
	private NMenuItem mn4_3_item1 = new NMenuItem("Filtre median");
	private NMenuItem mn4_3_item2 = new NMenuItem("Filtre Nagao");
	private NMenuItem mn4_3_item3 = new NMenuItem("Filtre Minimum");
	private NMenuItem mn4_3_item4 = new NMenuItem("Filtre Maximum");
	private NMenuItem mn4_3_item5 = new NMenuItem("Filtre Adaptatif");
	private NMenu mn4_3_1 = new NMenu("Contraste");
	private NMenuItem mn4_3_item6 = new NMenuItem("Correction");
	private NMenuItem mn4_3_item7 = new NMenuItem("Brighteness");

	
	//AFFICHAGE
	private NMenuItem mn5_item1 = new NMenuItem("Zoom plus");
	private NMenuItem mn5_item2 = new NMenuItem("Zoom moin");
	private NMenuItem mn5_item3 = new NMenuItem("100%");
	private NMenuItem mn5_item4 = new NMenuItem("200%");
	
	//Traitement_Couleur
	private NMenu mn7_1 = new NMenu("Traitements");
	private NMenuItem mn7_1_item1 = new NMenuItem("Binarise");
	private NMenuItem mn7_1_item2 = new NMenuItem("Negation"); 
	private NMenuItem mn7_1_item3 = new NMenuItem("Convertir en image gris");
	private NMenuItem mn7_1_item4 = new NMenuItem("Flip"); 
	
	private NMenu mn7_1_1 = new NMenu("Supprimer Couleur");  
	private NMenuItem mn7_1_1_item1 = new NMenuItem("Rouge"); 
	private NMenuItem mn7_1_1_item2 = new NMenuItem("Vert"); 
	private NMenuItem mn7_1_1_item3 = new NMenuItem("Bleu"); 
	private NMenu mn7_2 = new NMenu("Filtres");
	private NMenuItem mn7_2_item1 = new NMenuItem("Filtre Moyenneur");
	private NMenuItem mn7_2_item2 = new NMenuItem("Contraste Correction");
	private NMenuItem mn7_2_item3 = new NMenuItem("Filtre Convolution");
	private NMenuItem mn7_2_item4 = new NMenuItem("Filtre Repoussage");
	private NMenuItem mn7_2_item5 = new NMenuItem("Filtre laplacien");
	private NMenuItem mn7_2_item6 = new NMenuItem("Filtre Sobel");
	
	private NMenuItem mn8_item1 = new NMenuItem("Plusieurs Images");
	
	private Color bg = NColor.CLR4;
	
	private void initFichier()
	{
		//fichier
		mn1.add(mn1_item1);
		mn1.addSeparator();
		mn1.add(mn1_item2);
		mn1.add(mn1_item3);
		mn1.addSeparator();
		mn1.add(mn1_item4);
		mn1.addSeparator();
		mn1.add(mn1_item5);
		menuBar.add(mn1);
			//racc
		mn1_item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O
			,KeyEvent.CTRL_DOWN_MASK));//Ctrl+O
		mn1_item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S
			,KeyEvent.CTRL_DOWN_MASK));//Ctrl+S
		mn1_item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O
			,KeyEvent.ALT_DOWN_MASK+KeyEvent.CTRL_DOWN_MASK));//Ctrl+ALT+O
		mn1_item5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4
			,KeyEvent.ALT_DOWN_MASK));//ALT+F4	
	
		
		mn1_item1.addActionListener(new Action());
		mn1_item2.addActionListener(new Action());
		mn1_item3.addActionListener(new Action());
		mn1_item4.addActionListener(new Action());
		mn1_item5.addActionListener(new Action());
	}
	
	private void initEdition()
	{
		//edition
		mn2.add(mn2_item1);
		mn2.add(mn2_item2);
		mn2.add(mn2_item3);
		mn2.add(mn2_item4);
		menuBar.add(mn2);
			//racc
		mn2_item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z
			,KeyEvent.CTRL_DOWN_MASK));//Ctrl+Z
		mn2_item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z
			,KeyEvent.ALT_DOWN_MASK+KeyEvent.CTRL_DOWN_MASK));//Ctrl+ALT+Z
		mn2_item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z
			,KeyEvent.SHIFT_DOWN_MASK+KeyEvent.CTRL_DOWN_MASK));//Ctrl+SHIFT+Z
		mn2_item4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,KeyEvent.CTRL_DOWN_MASK));//ALT+I
		
		mn2_item1.addActionListener(new Action());
		mn2_item2.addActionListener(new Action());
		mn2_item3.addActionListener(new Action());
		mn2_item4.addActionListener(new Action());
	}
	
	private void initImage()
	{
		//Image	
		mn3.add(mn3_item1);
		mn3.addSeparator();	
		mn3.add(mn3_item2);	
		mn3.add(mn3_item3);	
		mn3.add(mn3_item4);
		menuBar.add(mn3);
		
		mn3_item1.addActionListener(new Action());
		mn3_item2.addActionListener(new Action());
		mn3_item3.addActionListener(new Action());
		mn3_item4.addActionListener(new Action());
	}
	
	private void initFiltre()
	{
		//filtre
		mn4_1.setForeground(Color.black);
		mn4_2.setForeground(Color.black);
		mn4_3.setForeground(Color.black);
		mn4_2_1.setForeground(Color.black);
		
		mn4_1.add(mn4_1_item1);
		mn4_1.add(mn4_1_item2);
		mn4_1.add(mn4_1_item3);
		mn4_1.add(mn4_1_item4);
		mn4_1.add(mn4_1_item5);
		mn4_1.add(mn4_1_item6);
		mn4_1.add(mn4_1_item7);
		mn4_1.add(mn4_1_item9);
		mn4_1.add(mn4_1_item8);
		mn4.add(mn4_1);
		mn4.addSeparator();
		
		mn4_2.add(mn4_2_item1);
		mn4_2.add(mn4_2_item2);
		mn4_2.add(mn4_2_item3);
		mn4_2.add(mn4_2_item4);
		mn4_2.add(mn4_2_item5);
		mn4_2_1.add(mn4_2_1_item1);
		mn4_2_1.add(mn4_2_1_item2);
		mn4_2_1.add(mn4_2_1_item3);
		mn4_2.add(mn4_2_1);
		mn4.add(mn4_2);
		
		
		mn4_3.add(mn4_3_item1);
		mn4_3.add(mn4_3_item2);
		mn4_3.add(mn4_3_item3);
		mn4_3.add(mn4_3_item4);
		mn4_3.add(mn4_3_item5);
		mn4_3_1.add(mn4_3_item6);
		mn4_3_1.add(mn4_3_item7);
		mn4_3.add(mn4_3_1);
		
		mn4.add(mn4_3);
		
		mn8.add(mn4);	
		
		//globale
		mn4_1_item1.addActionListener(new Action());
		mn4_1_item2.addActionListener(new Action());
		mn4_1_item3.addActionListener(new Action());
		mn4_1_item4.addActionListener(new Action());
		mn4_1_item5.addActionListener(new Action());
		mn4_1_item6.addActionListener(new Action());
		mn4_1_item7.addActionListener(new Action());
		mn4_1_item8.addActionListener(new Action());
		mn4_1_item9.addActionListener(new Action());
		//lineaires
		mn4_2_item1.addActionListener(new Action());
		mn4_2_item2.addActionListener(new Action());
		mn4_2_item3.addActionListener(new Action());
		mn4_2_item4.addActionListener(new Action());
		mn4_2_item5.addActionListener(new Action());
		mn4_2_1_item1.addActionListener(new Action());
		mn4_2_1_item2.addActionListener(new Action());
		mn4_2_1_item3.addActionListener(new Action());
		//non-lineaires
		mn4_3_item1.addActionListener(new Action());
		mn4_3_item2.addActionListener(new Action());
		mn4_3_item3.addActionListener(new Action());
		mn4_3_item4.addActionListener(new Action());
		mn4_3_item5.addActionListener(new Action());
		mn4_3_item6.addActionListener(new Action());
		mn4_3_item7.addActionListener(new Action());
		
	}
	
	private void initAffichage()
	{
		//affichage
		mn5.add(mn5_item1);
		mn5.add(mn5_item2);
		mn5.add(mn5_item3);
		mn5.add(mn5_item4);
		menuBar.add(mn5);
		
		mn5_item1.addActionListener(new Action());
		mn5_item2.addActionListener(new Action());
		mn5_item3.addActionListener(new Action());
		mn5_item4.addActionListener(new Action());
	}
	
	private void initAide()
	{
		//Aide
		mn6.add(mn6_item1);
		menuBar.add(mn6);
		mn6_item1.addActionListener(new Action());
	}
	
	private void initTrtClr()
	{
		mn7_1_1.add(mn7_1_1_item1);
		mn7_1_1.add(mn7_1_1_item2);
		mn7_1_1.add(mn7_1_1_item3);
		
		mn7_1.add(mn7_1_item1);
		mn7_1.add(mn7_1_item2);
		mn7_1.add(mn7_1_item4);
		mn7_1.add(mn7_1_item3);
		mn7_1.add(mn7_1_1);
		
		mn7_2.add(mn7_2_item1);
		mn7_2.add(mn7_2_item4);
		mn7_2.add(mn7_2_item5);
		mn7_2.add(mn7_2_item6);
		mn7_2.add(mn7_2_item2);
		mn7_2.add(mn7_2_item3);
		
		mn7.add(mn7_1);
		mn7.addSeparator();
		mn7.add(mn7_2);
		mn8.addSeparator();
		mn8.add(mn7);
		mn8.addSeparator();
		mn8.add(mn8_item1);
		menuBar.add(mn8);
		
		mn7_1_item1.addActionListener(new Action());
		mn7_1_item2.addActionListener(new Action());
		mn7_1_item3.addActionListener(new Action());
		mn7_1_item4.addActionListener(new Action());
		mn7_1_1_item1.addActionListener(new Action());
		mn7_1_1_item2.addActionListener(new Action());
		mn7_1_1_item3.addActionListener(new Action());
		mn7_2_item1.addActionListener(new Action());
		mn7_2_item2.addActionListener(new Action());
		mn7_2_item3.addActionListener(new Action());
		mn7_2_item4.addActionListener(new Action());
		mn7_2_item5.addActionListener(new Action());
		mn7_2_item6.addActionListener(new Action());
		mn8_item1.addActionListener(new Action());
	}
	
	class Action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(mn6_item1))//a propos
			{
				Object [] tab_obj_afficher = new Object []{new JLabel("<html><center>Cette application est le sujet d'un projet de fin d'étude<br>"+
				"dans le cadre de la formation à l'école supérieure de technologie d'Agadir en 2015<br>"+
				"Réalisé par : Ibrahim EL-IDRISSI, Ali BOUALI, Anas AIT< BAHA.<br>"+
				"Encadré par Mr. Mustapha AMROUCH </center> </html>") };
			int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher ,"A propos ",JOptionPane.OK_CANCEL_OPTION ,	
			JOptionPane.INFORMATION_MESSAGE,NColor.icn ,	null ,	null);
	
				
				
			} 
			if(e.getSource().equals(mn1_item1))//ouvrir
			{
				panCenter.appTraitement("mn1_item1");
			} 
			else if(e.getSource().equals(mn1_item2))//enreg **
			{
				panCenter.appTraitement("mn1_item2");
			}
			else if(e.getSource().equals(mn1_item3))//enreg sous
			{
				panCenter.appTraitement("mn1_item3");				
			}
			else if(e.getSource().equals(mn1_item4))//enreg tous **
			{
				panCenter.appTraitement("mn1_item4");				
			}
			else if(e.getSource().equals(mn1_item5))//fermer
			{
				System.exit(0);
			}
			else if(e.getSource().equals(mn2_item1))//annuler
			{
				panCenter.appTraitement("mn2_item1");				
			}
			else if(e.getSource().equals(mn2_item2))//Pas-en-arriere
			{
				panCenter.appTraitement("mn2_item2");				
			}
			else if(e.getSource().equals(mn2_item3))//Pas-en-avant
			{
				panCenter.appTraitement("mn2_item3");				
			}
			else if(e.getSource().equals(mn2_item4))//annuler tous
			{
				panCenter.appTraitement("mn2_item4");				
			}
			else if(e.getSource().equals(mn3_item1))//taille **
			{
				panCenter.appTraitement("mn3_item1");					
			}
			else if(e.getSource().equals(mn3_item2))//rot90
			{
				panCenter.appTraitement("mn3_item2");			
			}
			else if(e.getSource().equals(mn3_item3))//rotm90
			{
				panCenter.appTraitement("mn3_item3");	
			}
			else if(e.getSource().equals(mn3_item4))//rot180
			{
				panCenter.appTraitement("mn3_item4");
			}
			else if(e.getSource().equals(mn4_1_item1))//bin
			{
				panCenter.appTraitement("mn4_1_item1");
			}
			else if(e.getSource().equals(mn4_1_item2))//neg
			{
				panCenter.appTraitement("mn4_1_item2");
			}
			else if(e.getSource().equals(mn4_1_item3))//Recadrage dynamique
			{
				panCenter.appTraitement("mn4_1_item3");				
			}
			else if(e.getSource().equals(mn4_1_item4))//Convolution
			{
				panCenter.appTraitement("mn4_1_item4");				
			}
			else if(e.getSource().equals(mn4_1_item5))//flip
			{
				panCenter.appTraitement("mn4_1_item5");				
			}
			else if(e.getSource().equals(mn4_1_item6))//filtre Gradient
			{
				panCenter.appTraitement("mn4_1_item6");				
			}
			else if(e.getSource().equals(mn4_1_item7))//filtre Lissage Minmax
			{
				panCenter.appTraitement("mn4_1_item7");				
			}
			else if(e.getSource().equals(mn4_1_item8))//equalization
			{
				panCenter.appTraitement("mn4_1_item8");				
			}	
			else if(e.getSource().equals(mn4_1_item9))//Sobel
			{
				panCenter.appTraitement("mn4_1_item9");				
			}			
			else if(e.getSource().equals(mn4_2_item1))//F-Moyenneur
			{
				panCenter.appTraitement("mn4_2_item1");
			}
			else if(e.getSource().equals(mn4_2_item2))//F-Moyenneur1 **
			{
				panCenter.appTraitement("mn4_2_item2");				
			}
			else if(e.getSource().equals(mn4_2_item3))//F-M-Pondere **
			{
				panCenter.appTraitement("mn4_2_item3");				
			}
			else if(e.getSource().equals(mn4_2_item4))//F-Dw-Mtm **
			{
				panCenter.appTraitement("mn4_2_item4");					
			}
			else if(e.getSource().equals(mn4_2_item5))//F-smooth **
			{
				panCenter.appTraitement("mn4_2_item5");					
			}
			else if(e.getSource().equals(mn4_2_1_item1))//F-Gaussien
			{
				panCenter.appTraitement("mn4_2_1_item1");		
			}
			else if(e.getSource().equals(mn4_2_1_item2))//F-Gaussien3
			{
				panCenter.appTraitement("mn4_2_1_item2");					
			}
			else if(e.getSource().equals(mn4_2_1_item3))//F-Gaussien15
			{
				panCenter.appTraitement("mn4_2_1_item3");						
			}
			else if(e.getSource().equals(mn4_3_item1))//F-median **
			{
				panCenter.appTraitement("mn4_3_item1");					
			}
			else if(e.getSource().equals(mn4_3_item2))//F-Nagao
			{
				panCenter.appTraitement("mn4_3_item2");						
			}
			else if(e.getSource().equals(mn4_3_item3))//F-Minimum **
			{
				panCenter.appTraitement("mn4_3_item3");					
			}
			else if(e.getSource().equals(mn4_3_item4))//F-Maximum **
			{
				panCenter.appTraitement("mn4_3_item4");				
			}
			else if(e.getSource().equals(mn4_3_item5))//Filtre Adaptatif
			{
				panCenter.appTraitement("mn4_3_item5");				
			}
			else if(e.getSource().equals(mn4_3_item6))//Filtre Correction
			{
				panCenter.appTraitement("mn4_3_item6");				
			}
			else if(e.getSource().equals(mn4_3_item7))//Filtre "Brighteness"
			{
				panCenter.appTraitement("mn4_3_item7");				
			}
			else if(e.getSource().equals(mn5_item1))//Z-plus **
			{
				panCenter.appTraitement("mn5_item1");					
			}
			else if(e.getSource().equals(mn5_item2))//Z-moin **
			{
				panCenter.appTraitement("mn5_item2");		
			}
			else if(e.getSource().equals(mn5_item3))//Z-100 
			{
				panCenter.appTraitement("mn5_item3");	
			}
			else if(e.getSource().equals(mn5_item4))//Z-200 **
			{
				panCenter.appTraitement("mn5_item4");					
			}
			else if(e.getSource().equals(mn7_1_item1))//bin
			{
				panCenter.appTraitement("mn4_1_item1");
			}
			else if(e.getSource().equals(mn7_1_item2))//neg
			{
				panCenter.appTraitement("mn4_1_item2");
			}
			else if(e.getSource().equals(mn7_1_item3))//togris
			{
				panCenter.appTraitement("mn7_1_item3");
			}
			else if(e.getSource().equals(mn7_1_item4))//flip
			{
				panCenter.appTraitement("mn4_1_item5");
			}
			else if(e.getSource().equals(mn7_2_item1))//Moyenneur
			{
				panCenter.appTraitement("mn4_2_item1");
			}
			else if(e.getSource().equals(mn7_2_item2))//Correction
			{
				panCenter.appTraitement("mn4_3_item6");
			}
			else if(e.getSource().equals(mn7_2_item3))//Convolution
			{
				panCenter.appTraitement("mn4_1_item4");
			}
			else if(e.getSource().equals(mn8_item1))//plusieurs Image
			{
				TPImage fenetre = new TPImage();
			}
			else if(e.getSource().equals(mn7_1_1_item1))//supR
			{
				panCenter.appTraitement("mn7_1_1_item1");
			}
			else if(e.getSource().equals(mn7_1_1_item2))//supG
			{
				panCenter.appTraitement("mn7_1_1_item2");
			}
			else if(e.getSource().equals(mn7_1_1_item3))//supB
			{
				panCenter.appTraitement("mn7_1_1_item3");
			}
			else if(e.getSource().equals(mn7_2_item4))//Filtre Repoussage
			{
				panCenter.appTraitement("mn7_2_item4");
			}
			else if(e.getSource().equals(mn7_2_item5))//Filtre laplacien
			{
				panCenter.appTraitement("mn7_2_item5");
			}
			else if(e.getSource().equals(mn7_2_item6))//Filtre Sobel
			{
				panCenter.appTraitement("mn4_1_item9");
			}
		}
	}
	
	Menu(PanCenter pc)
	{
		panCenter = pc;
		menuBar.setBackground(bg);
		initFichier();
		initEdition();
		initImage();
		initFiltre();
		initTrtClr();
		initAffichage();
		initAide();
	}
	
	public JMenuBar getJMenuBar(){return menuBar;}
}