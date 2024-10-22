import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.90;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 50;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The food value of a single plant.
    private static final int PLANT_FOOD_VALUE = 5;
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;
    // The rabbit's food level, which is increased by eating plant.
    private int foodLevel;
    // The random weather for everystep.
    private Weather weather;
    /**
     * Create a rabbit. A rabbit can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param diseased If true,the rabit will die when born.
     * @param diseased If false,the rabit will born successfully.
     */
    public Rabbit(boolean randomAge, Field field, Location location,boolean diseased)
    {
        super(field, location,diseased);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(PLANT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = PLANT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around and eat plants.
     * Sometimes it will breed or die of old age or die of hunger
     * or ate by humans and foxes.
     * @param newRabbits A list to return newly born rabbits.
     */
     public void act(List<Animal> newRabbits)
    {
    // Make this rabbit has probability to get a disease.
    // Disease could result in the rabbit's death.
    getDisease();
    incrementAge(); 
    Field field = getField();
    if(isAlive()) {
        // Rabbits only act during this time(the mod of steps/10 < 7).
        // the other time is for sleep.
            if (Simulator.getStep() % 10 < 7){
           // When an alive female(gender=1) meets an alive male 
           // they will give a birth of new rabbit.
                if (getGender() ==1) {
                List<Location> adjacent = field.adjacentLocations(getLocation());
                Iterator<Location> it = adjacent.iterator();
                while(it.hasNext()) {
                   Location where = it.next();
                   Object animal = field.getObjectAt(where);
                   if (animal instanceof Rabbit && ((Animal)animal).getGender() != getGender()) {
                       giveBirth(newRabbits); 
                   }
                }
           }
           // Move towards a source of food if found.
           Location newLocation = findFood();
           if(newLocation == null) { 
             // No food found - try to move to a free location.
             newLocation = getField().freeAdjacentLocation(getLocation());
           }
            // See if it was possible to move.
           if(newLocation != null) {
             setLocation(newLocation);
            }
            else {
             // Overcrowding.
             setDead();
           }
        }
    }
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
     /**
     * Make this rabbit more hungry. This could result in the rabbit's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for plant adjacent to the current location.
     * Only the first live plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Plant) {
                Plant plant = (Plant) animal;
                if(plant.isAlive()) { 
                    plant.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc,false);
            newRabbits.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
