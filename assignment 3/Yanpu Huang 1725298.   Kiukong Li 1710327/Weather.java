import java.util.Random;

public class Weather{
    // The weather situation.
    private static boolean sunny;
    private static boolean foggy;
    private static boolean rainy;
    // Set one String value x to return at the final.
    private static String x;
    // A shared random number generator to control random weather selection.
    private static final Random rand = Randomizer.getRandom();
    public Weather(){
        sunny = false;
        foggy = false;
        rainy = false;
        x = "sunny";
    }
    
    public static String getWeather(){
        //The random selection in 3 values to make random weather everyday.
        int i = rand.nextInt(3);
        if(i == 0){
          sunny = true;
          x = "sunny";
        }
        else if(i == 1){
          foggy = true;
          x = "foggy";
        }
        else if(i == 2){
          rainy = true;
          x = "rainy";
        }
        return x;
    }
}