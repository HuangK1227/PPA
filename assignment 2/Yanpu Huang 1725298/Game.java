import java.util.ArrayList;
import java.util.HashMap;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item items;
    private Player player;
    private Person person;
    private int currentWeight;
    private int maxWeight;
    private String previous;
    private HashMap<Integer,Room> roomIndex;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
        person = new Person("mom");
        maxWeight = 4;
        currentWeight = 0;
        previous = "";
    }

    /**
     * Create all the rooms and link their exits together.
     * Create all the items and link with rooms.
     */
    private void createRooms()
    {
        Room hall,kitchen,bedRoom,bathRoom,storingRoom,relaxingRoom,studyOffice,dinningRoom,coatRoom;
        Item bottle,camera,clock,sponge,key,clothes,bowl,cup,spoon,book;
        Person mom;
        // create the rooms
        hall = new Room("in the hall of your house,which is the central area"+".\n"+"pick something?");
        kitchen = new Room("in the kitchen of your house,which contains food and water"+".\n"+"pick something?");
        bathRoom = new Room("in the bathroom which can take a shower"+".\n"+"pick something?");
        storingRoom = new Room("in the storingroom which contains a lot of old things"+".\n"+"pick something?");
        relaxingRoom = new Room("in the relaxingroom which has gaming computer and 3D screen"+".\n"+"pick something?");
        studyOffice = new Room("in the studyroom which has a lot of books"+".\n"+"pick something?");
        dinningRoom = new Room("in the dinningroom which has a big dinning table"+".\n"+"pick something?");
        bedRoom = new Room("in the bedroom which has a bed for sleep"+".\n"+"pick something?");
        coatRoom = new Room("in the coatroom which has full of clothes"+".\n"+"pick something?");
        // create the items
        bottle = new Item("bottle","A water bottle", 1);
        camera = new Item("camera","digital camera", 2);
        clock = new Item("clock","wall clock", 1);
        sponge = new Item("sponge","bath sponge", 1);
        key = new Item("key","key to unlock door", 1);
        clothes = new Item("clothes","clothes contain medicine!!",2);
        bowl = new Item("bowl","bowl for rice",1);
        cup = new Item("cup","cup which has a small medicine!",1);
        spoon = new Item("spoon","A  eating spoon ",1);
        book = new Item("book","blue J textbook",3);
        // initialise room exits
        hall.setExit("east",studyOffice);
        hall.setExit("south", relaxingRoom);
        hall.setExit("west", kitchen);
        kitchen.setExit("south",dinningRoom);
        kitchen.setExit("east",hall);
        dinningRoom.setExit("north",kitchen);
        dinningRoom.setExit("east",relaxingRoom);
        dinningRoom.setExit("south",bathRoom);
        bathRoom.setExit("east",storingRoom);
        bathRoom.setExit("notrh",dinningRoom);
        studyOffice.setExit("south",bedRoom);
        studyOffice.setExit("west",hall);
        bedRoom.setExit("north",studyOffice);
        bedRoom.setExit("west",relaxingRoom);
        bedRoom.setExit("south",coatRoom);
        coatRoom.setExit("north",bedRoom);
        coatRoom.setExit("west",storingRoom);
        storingRoom.setExit("north",relaxingRoom);
        storingRoom.setExit("east",coatRoom);
        storingRoom.setExit("west",bathRoom);
        relaxingRoom.setExit("south",storingRoom);
        relaxingRoom.setExit("east",bedRoom);
        relaxingRoom.setExit("west",dinningRoom);
        //add items into rooms
        bedRoom.addItem(key);
        kitchen.addItem(spoon);
        bathRoom.addItem(sponge);
        storingRoom.addItem(clock);
        relaxingRoom.addItem(bottle);
        dinningRoom.addItem(cup);
        studyOffice.addItem(book);
        dinningRoom.addItem(bowl);
        coatRoom.addItem(clothes);
        hall.addItem(camera);
        //add person into rooms
        //start the game from hall
        kitchen.addPerson(person);
        currentRoom = hall;
        //kitchen.addPerson(person);
        //roomIndex.put(0,hall);
        //roomIndex.put(1,kitchen);
        //roomIndex.put(2,bedRoom);
        //roomIndex.put(3,studyOffice);
        //roomIndex.put(4,relaxingRoom);
        //roomIndex.put(5,storingRoom);
        //roomIndex.put(6,bathRoom);
        //roomIndex.put(7,coatRoom);
        //roomIndex.put(8,dinningRoom);
        //person.getRoom(roomIndex);  
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  Successful conditions.
     */
    public void play() 
    { 
       
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (player.checkMedicine()){
                System.out.println(".\n"+"Congratulations!!!"+".\n"+"You finally find medicine!!!!"+".\n"+"They are in the clothes and cup"+".\n"+"You will not bother professor anymore ~zzz");
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Game:Find your medicine");
        System.out.println("This World is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * make a back method to move back to the previous room.
     */
    public void back(Command command){
        command = new Command(previous,"");
        if(previous.equals("")){
            System.out.println("move again!!!");
        }
        else{
            goRoom(command);
        }
    }
    
    /**
     * make a drop mathod to drop items from player to room
     */
    public void dropIt(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            return;
        }
        String itemName = command.getSecondWord();
        Item x = player.getItem(itemName);
        if(x != null){
            player.removeItem(itemName);
            currentRoom.addItem(x);
            currentWeight = currentWeight - x.getWeight();
        }else{
            System.out.println("You do not have this thing");
        }
    }
    
    /**
     * make a pick method to pick up items from room to player
     */
    public void pickUp(Command command){
        if(!command.hasSecondWord()){
            System.out.println("pick what?");
            return;
        } 
        String itemName = command.getSecondWord();
        Item x = currentRoom.getItem(itemName);
        if (x != null){
            currentWeight = x.getWeight() + currentWeight;
            if(currentWeight <= maxWeight){
                player.addItem(x);
                currentRoom.removeItem(x);
            }
            else{
                currentWeight = 0 + currentWeight;
                System.out.println("Now your bag is full"+".\n"+"Try drop something");
            }
        }
        else{
           System.out.println("there is nothing like this") ;
        }
    }
    
    /**
     * make a talk method to get some advice from mom.
     */
    public void talk(Command command){
        System.out.println("mom hears ur voice and says:");
        System.out.println("'Last time I saw u put medicine into clothes'");
        System.out.println("'And you used a cup to drink water with medicine'");
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
            person.move();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("pick")) {
            pickUp(command);
            person.move();
        }
        else if (commandWord.equals("drop")) {
            dropIt(command);
            person.move();
        }
        else if (commandWord.equals("talk")) {
            talk(command);
            person.move();
        }
        else if (commandWord.equals("back")) {
            back(command);
            person.move();
        }
        // else command not recognised.
        return wantToQuit;
    }
    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are a psycho.");
        System.out.println("You must find the medicine to fix ur disease.");
        System.out.println("Or you will dance in front of Professor Kolling's office.");
        System.out.println("And pull fire alarm in the professor Kolling's lecture.");
        System.out.println("Now you are in ur house and you forget where the medicine is.");
        System.out.println("You have a bag which can only afford weight 4");
        System.out.println("Try to pick up items and find if there is ur medicine inside~~");
        System.out.println("Mom is at home ,u can try talk");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            }
           
        }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
