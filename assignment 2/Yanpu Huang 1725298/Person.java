import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
/**
 * create the class of Person
 */
public class Person
{
    // instance variables - replace the example below with your own
    private String name;
    private ArrayList<Item> getItem;
    private HashMap<Integer,Room> room;

    /**
     * Constructor for objects of class Person.
     */
    public Person(String name)
    {
        // initialise instance variables
        this.name = name;
        room = new HashMap<>();
        getItem = new ArrayList<>();
    }

    /**
     * return name of getName method.
     */
    public String getName(){
        return name;
    }
    
    /**
     * definate rooms into room of hashmap method.
     */
    public void getRoom(HashMap rooms){
        room = rooms;
    }
    
    /**
     * make a random move action of person.
     */
    public void move()
    {
        Random r = new Random();
        int x = r.nextInt(9) ;
        for(int i=0;i<room.size();i++)
        {
            room.get(i).removePerson(this);
        }
        //room.get(x).addPerson(this);
    }
    
    /**
     * make a checking for the position of the person.
     */
     public Room getPlace()
    {
        int i=0;
        boolean check = false;
        while(i<9 && check == false)
        {
            check = room.get(i).checkPerson(this);
            i++;
        }
        return room.get(i);
    }
}
