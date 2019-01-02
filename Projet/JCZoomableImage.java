import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JComponent;


class JCZoomableImage extends JComponent implements MouseWheelListener {
	PImage  gimage;
	BufferedImage image;
	double x,y;
    double scale, zoomInc;
    
    public JCZoomableImage(PImage  img)
    {
    	super();
    	gimage=img;
		if(img instanceof GImage)
		{
			image = getImageFromArray(gimage.getPixels());
		}
		else
		{
			image = getCImageFromArray(gimage.getCPixels());
		}
        scale = 1.0; 
        zoomInc = 0.05f;
        setBackground(Color.white);
		addMouseWheelListener(this);
		addMouseMotionListener(new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				int posx = e.getX();
				int posy = e.getY();
				
				Dimension dim = getPreferredSize();
				Double	var = dim.getWidth();
				int w = var.intValue();
				var = dim.getHeight();
				int h = var.intValue();
				if(posx>=x && posx<=(x+w) && posy>=y && posy<=(y+h))
				{
					posx -=x;
					posy -=y;
					PanCenter.pInf.setPos(new Point(posx,posy));	
				}
			}
		});
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) 
	{
		scale = Math.max(0, scale - zoomInc * e.getWheelRotation());
		 actualiser();
	}
    
     public void setScale(int direction)
    {
		JFrame f=new JFrame();
    	scale = Math.max(0,scale + direction * zoomInc);
        actualiser();
    }
     
	public void setPreferedScale(int scl)
    {
    	scale = scl;
		image=getImageFromArray(gimage.getPixels());
        actualiser();
    }
       
	protected void actualiser()
	{
		revalidate();		repaint();
	}  
    
	public void setImage(PImage  image) 
	{
		this.gimage = image;
		if(image instanceof GImage)
			this.image=getImageFromArray(gimage.getPixels());
		if(image instanceof CImage)
			this.image=getCImageFromArray(gimage.getCPixels());
		
	}
	
	public PImage  getImage() 
	{
		return gimage;
	}
	
	public double getZoomInc() {
		return zoomInc;
	}

	public void setZoomInc(double zoomInc) {
		this.zoomInc = zoomInc;
	}
	
	protected static BufferedImage getImageFromArray(char[][] p)
	{
		int width=p[0].length,height=p.length; 
		int[] pixels=chartowDimToOneDimIntArray(p);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); // bnsba l gris ghadi ndiroo  
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		image.setData(raster);
		return image;
	}
	
	protected static BufferedImage getCImageFromArray(Pixel[][] p)
	{
		int width=p[0].length,height=p.length; 
		int[] pixels= new int[width*height*3];
		int count = 0;
		for(int i = 0;i<height;i++)
		{
			for(int j = 0;j<width;j++)
			{
				pixels[count++] = (int)p[i][j].getR();
				pixels[count++] = (int)p[i][j].getG();
				pixels[count++] = (int)p[i][j].getB();
			}
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); 
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		image.setData(raster);
		return image;
	}
	
	protected static int [] chartowDimToOneDimIntArray(char[][] towDim){
		int []one=new int[towDim.length*towDim[0].length];
		for(int i=0;i<towDim.length;++i){
			for(int j=0;j<towDim[i].length;++j){
				one[i*(towDim[i].length)+j]=(int)towDim[i][j];
			}
		}
		return one;
	}
 
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);//Controle de qualite
        int w = getWidth();
        int h = getHeight();
        double imageWidth = scale * image.getWidth();
        double imageHeight = scale * image.getHeight();
        x = (w - imageWidth)/2;
        y = (h - imageHeight)/2;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.scale(scale, scale);
        g2.drawRenderedImage(image, at);
    }
  
	public double getMyX(){return x;}
	public double getMyY(){return y;}
	
    public Dimension getPreferredSize()
    {
        int
            w = (int)(scale * image.getWidth()),
            h = (int)(scale * image.getHeight());
        return new Dimension(w, h);
    }	
	
	public JScrollPane getScrolled()
	{
		return (new JScrollPane(this));
	}
	

}
















