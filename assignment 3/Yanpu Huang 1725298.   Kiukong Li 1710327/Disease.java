import java.util.Random;

public class Disease{
    // The disease situation.(disease or not disease).
    private boolean setDisease;
    // A shared random number generator to control disease probability.
    private static final Random rand = Randomizer.getRandom();
    public Disease(){
        //When there is no disease.
        setDisease = false;
    }
    
    public boolean getDisease(){
        //The disease probality is equal to 1/1000.
        if(rand.nextInt(1000) == 0){   
        //When there is a disease happen.
        setDisease = true;
        }
        return setDisease;
    }
}


