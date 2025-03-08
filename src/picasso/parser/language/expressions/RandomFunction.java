package picasso.parser.language.expressions;

import java.util.Random;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents random function
 * Generates a random RGB color once and uses that same color consistently
 * @Author Claire Hamlet
 */
public class RandomFunction extends ExpressionTreeNode {
    private final RGBColor randomColor;
    /**
     * Constructor
     * Generates random color and caches it
     */
    public RandomFunction() {
        Random random = new Random();
        // Generate RGB values in range [-1, 1]
        double red = -1 + 2 * random.nextDouble();
        double green = -1 + 2 * random.nextDouble();
        double blue = -1 + 2 * random.nextDouble();
        // Cache generated color
        this.randomColor = new RGBColor(red, green, blue);
    }
    /**
     * Evaluate random function at given x y coordinates
     * Same color regardless of x y
     * 
     * @param x coord
     * @param y coord
     * @return cached random color
     */
    @Override
    public RGBColor evaluate(double x, double y) {
        return randomColor;
    }
}
