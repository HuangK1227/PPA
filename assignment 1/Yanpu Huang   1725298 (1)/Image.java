import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

/**
 * Image is a class that defines an image for display.
 * 
 * @author  Michael Kolling 
 * @version 2.0
 */
public class Image extends BufferedImage
{
    // constants for fish-eye effect:
    private final static int SCALE = 20;   // this defines the strenght of the filter
    private final static double TWO_PI = 2 * Math.PI;

    // fields:
    private String name;
    
    /**
     * Static factory method: Create an image from a file.
     */
    public static Image getImage(String fileName)
    {
        try {
            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);
            if(image == null || (image.getWidth(null) < 0)) {
                // we could not load the image - probably invalid file format
                return null;
            }
            return new Image(image, file.getName());
        }
        catch(IOException exc) {
            System.err.println(fileName + " is not a valid image file");
            return null;
        }
    }
    
    /**
     * Create an Image copied from a BufferedImage.
     * @param image The image to copy.
     * @param name The name of the image.
     */
    public Image(BufferedImage image, String name)
    {
         super(image.getColorModel(), image.copyData(null), 
               image.isAlphaPremultiplied(), null);
         this.name = name;
    }
    
    /**
     * Return the name of this image.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the height of this image.
     */
    public int getHeight()
    {
        return super.getHeight();
    }
    
    /**
     * Return the width of this image.
     */
    public int getWidth()
    {
        return super.getWidth();
    }

    /**
     * Apply a fish-eye effect to this image.
     */
    public void fishEye()
    {
        int height = getHeight();
        int width = getWidth();
        Image original = new Image(this, "");

        int[] xa = computeXArray(width);
        int[] ya = computeYArray(height);
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                setPixel(x, y, original.getPixel(x + xa[x], y + ya[y]));
            }
        }
    }

    /**
     * Set a given pixel of this image to a specified color. The
     * color is represented as an (r,g,b) value.
     * @param x The x position of the pixel.
     * @param y The y position of the pixel.
     * @param col The color of the pixel.
     */
    public void setPixel(int x, int y, Color col)
    {
        int pixel = col.getRGB();
        setRGB(x, y, pixel);
    }
    
    /**
     * Get the color value at a specified pixel position.
     * @param x The x position of the pixel.
     * @param y The y position of the pixel.
     * @return The color of the pixel at the given position.
     */
    public Color getPixel(int x, int y)
    {
        int pixel = getRGB(x, y);
        return new Color(pixel);
    }

    /**
     * Compute and return an array of horizontal offsets for each pixel column.
     * These can then be applied as the horizontal offset for each pixel.
     */
    private int[] computeXArray(int width)
    {
        int[] xArray = new int[width];
        
        for(int i=0; i < width; i++) {
            xArray[i] = (int)(Math.sin( ((double)i / width) * TWO_PI) * SCALE);
        }
        return xArray;
    }

    /**
     * Compute and return an array of vertical offsets for each pixel row.
     * These can then be applied as the vertical offset for each pixel.
     */
    private int[] computeYArray(int height)
    {
        int[] yArray = new int[height];
        
        for(int i=0; i < height; i++) {
            yArray[i] = (int)(Math.sin( ((double)i / height) * TWO_PI) * SCALE);
        }
        return yArray;
    }

}
