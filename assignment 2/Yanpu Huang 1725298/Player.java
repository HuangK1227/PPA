import java.util.HashMap;
import java.util.ArrayList;
/**
 * Write a description of class Player here.
 * 
 * @author 
 * @version 05.05.2009
 */
public class Player
{
    private HashMap <String, Item> items;
    private ArrayList<Item> getItem;
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        items = new HashMap <String, Item>();
        getItem = new ArrayList<>();
    }
    
    /**
     * add items into player .
     */ 
    public void addItem(Item x){
        getItem.add(x);
        System.out.println("You get a "+x.getName()+" with you");
    }
    
    /**
     * remove items from player.
     */ 
    public void removeItem(String itemName){
        for (Item x : getItem){
            if (x.getName().equals(itemName)){
            getItem.remove(x);
            System.out.println("You take "+x.getName()+" away");    
            break;
            }
        }
        System.out.println("The item is taken away");
    }
    
    /**
     * check if u have medicine 
     * medicines are in the clothes and cup.
     */
    public boolean checkMedicine()
    {
        
        for(Item x : getItem)
          {
           if (x.getName().equals("clothes")){
           return true;
           }
           if (x.getName().equals("cup")){
           return true; 
           }
          }
        return false;
    }  
    
    /**
     * show name of items on the player
     */
    public Item getItem(String name){
        for (Item x : getItem){
            if(name != null && name.equals(x.getName())){
                return x;
            }
        }
        return null;
    }
}