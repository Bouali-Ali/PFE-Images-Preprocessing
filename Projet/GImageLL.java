public class GImageLL 
{
	JCZoomableImage nGImage;
	JCZoomableImage oGImage;
	HistoriqueLIFO hsImage;
	
	GImageLL(JCZoomableImage nGImage1,JCZoomableImage oGImage1,HistoriqueLIFO hsImage1)
	{
		nGImage = nGImage1;
		oGImage = oGImage1;
		hsImage = hsImage1;
	}
	
	public JCZoomableImage getNGImage(){return nGImage;}
	public JCZoomableImage getOGImage(){return oGImage;}
	public HistoriqueLIFO getHsImage(){return hsImage;}
	
	public void setNGImage(JCZoomableImage nGImage1){nGImage = nGImage1;}
	public void setOGImage(JCZoomableImage oGImage1){oGImage = oGImage1;}
	public void setHsImage(HistoriqueLIFO hsImage1){hsImage = hsImage1;}
}