import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class JPTraitement extends MouseAdapter {
	JCImage imageZone;
	JPanel traitementZone;
	PanCenter jpcenter;
	PanImage jpimage;
	JLabel description;
	int height,width;
	PImage  gimg;
	
	public void mouseClicked(MouseEvent e)
	{
		jpimage.setImage(gimg);
	}
	public PImage getPImage(){return gimg;}
	
	
	public JPTraitement(PanCenter jpc,PanImage jpi,PImage  gimgg,String desc) {
		
	  jpcenter=jpc;
	  jpimage = jpi;
	  gimg=gimgg;

        traitementZone = new JPanel();
		traitementZone.setBackground(NColor.CLR11);
        traitementZone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageZone = new JCImage(gimg,60);
        imageZone.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

         description=new JLabel(desc);         
        
        traitementZone.setLayout(new FlowLayout(FlowLayout.LEFT));
        //traitementZone.setLayout(new GridLayout(1,2));
        
        traitementZone.add(imageZone);
        traitementZone.add(description);
        this.reSize(60*4, 60);
        
	  traitementZone.addMouseListener(this);
	  //description.addMouseListener(this);
	  //imageZone.addMouseListener(this);
	}

	public JPanel getTraitement() {
        return traitementZone;
	}  
      
    public void reSizeImageZone()
	{
        //imageZone.setSize((int)(0.3*width),height);
       imageZone.setPreferredSize(new Dimension((int)(0.4*width),height));
    }
    public void reSizeDescription(){
        description.setSize((int)(0.7*width),height);
       // description.setPreferredSize(new Dimension((int)(0.7*width),height));
    }
    public void reSize(int width,int height){
        this.width=width; this.height=height;
        traitementZone.setSize(this.width,this.height);
        //traitementZone.setPreferredSize(new Dimension(this.width,this.height));
        //reSizeImageZone(); 
        reSizeDescription();
    }
    
}
