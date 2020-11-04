/**
 *  
 * @YANPU HUANG 
 * @1725298 
 */
public class ImageViewer
{    
    private ImageViewerGUI gui;     // the Graphical User Interface
    private Album album;
    private Image image;
    private int currentImageIndex;
    private int height;
    private int width;
    private String name;
    private int counter;
    private int totalWidth;
    private int maxWidth;
    private int maxHeight;
    /**
     * Create an ImageViewer and display its GUI on screen.
     */
    public ImageViewer()
    {
        gui = new ImageViewerGUI(this);
        album = new Album("images");
        currentImageIndex = 0;
        image = album.getImage(currentImageIndex);
        name = image.getName();
        height = image.getHeight();
        width = image.getWidth();
        gui.showImage(image);
        gui.showName(name);
        gui.showStatus("width:"+width+"height:"+height);
        counter = 1;
        totalWidth = width;
        //challenge part 3
        maxWidth = 0;
        maxHeight = 0;
        for(int x = 0 ; x <= album.numberOfImages() - 1; x++ ){
            width = album.getImage(x).getWidth();
            height = album.getImage(x).getHeight();
            if( width > maxWidth ){
                maxWidth = width;
            }
            if( height > maxHeight ){
                maxHeight = height;
            }
        } 
    }

    /**
     * Create an action when you click nextImage
     */
    public void nextImage(){
        if(currentImageIndex >= 0 & currentImageIndex +1 != album.numberOfImages()){
           currentImageIndex ++;
        }
        else{
           currentImageIndex = 0; 
        }
        image = album.getImage(currentImageIndex);
        name = image.getName();
        gui.showImage(image);
        gui.showName(name);
        gui.showStatus("width:"+width+"height:"+height);
        height = image.getHeight();
        width = image.getWidth();
        counter++;    
        totalWidth = totalWidth + width;
    }

    /**
     * Create an acntion when you click previousImage
     */
    public void previousImage(){
        if(currentImageIndex!=0){
            currentImageIndex --;
        }
        else{
            currentImageIndex = album.numberOfImages()-1;
        }
        image = album.getImage(currentImageIndex);
        name = image.getName();
        gui.showImage(image);
        gui.showStatus("width:"+width+"height"+height);
        gui.showName(name);
        height = image.getHeight();
        width = image.getWidth();
        counter++;
        totalWidth = totalWidth + width;
    }

    /**
     * 
     */
    public void fishEye(){
        image.fishEye();
        name = image.getName();  
        gui.showImage(image);
        gui.showStatus("width:"+width+"height:"+height);
        gui.showName(name);
        height = image.getHeight();
        width =  image.getWidth();
    }
    // challenge part 1
    public int getNumberOfImagesViewed(){
        return counter;
    }
    // challenge part 2
    public int averageImageWidth(){
        return totalWidth/counter;
    }
}
