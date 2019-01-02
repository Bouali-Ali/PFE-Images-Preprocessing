import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoriqueLIFO 
{
    Stack<JPTraitement> fifo;
    JPanel historique;
    PanCenter jpcenter;
    PanImage jpimage;
	int nbr = 0;
    private void poll()
	{
        fifo.remove(0);
        historique.remove(20);
    }
    public HistoriqueLIFO(PanImage jpi) 
	{
		jpimage = jpi;
        fifo=new Stack<JPTraitement>();
        historique = new JPanel(new GridLayout(20,1)); 
    }
	
	public void setJPCenter(PanCenter jpc){jpcenter=jpc;}
	
	public void add(PImage  img)
	{
		String desc = "<html><font style=\"color : #0000A0; font-size : 10px;\">Filtre applique : </font>" + img.getName() + "<br><font style=\"color : #0000A0; font-size : 10px;\"> Date : </font>" + getDate() + "</html>";
		JPTraitement test=new JPTraitement(jpcenter,jpimage,img,desc);
        fifo.push(test);
        historique.add(test.getTraitement(),0);
        if(fifo.size() >20)
		{
           poll();
        }
		nbr = 0;
    }
    
    public JScrollPane getHistorique() 
	{
        JScrollPane jh=new JScrollPane(historique);
		
		historique.setBackground(NColor.CLR11);
        jh.setPreferredSize(new Dimension(250,3*60));
        return jh;
    }
	public static String getDate(){
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd 'at' hh:mm:ss");
		return ft.format(new Date()) ;
	}
	public void avar(int nbr1)
	{
		nbr +=nbr1;
		int pos =fifo.size() + nbr -1;
		if(pos<fifo.size() && pos>-1)
		{
			jpimage.setImage(fifo.get(pos).getPImage());
		}
		else if(pos<0) nbr++;
		if(pos>=fifo.size()) nbr--;
	}
}
