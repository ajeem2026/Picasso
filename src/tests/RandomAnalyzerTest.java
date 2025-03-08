package tests;

import java.util.Stack;

import picasso.parser.tokens.Token;
import picasso.parser.tokens.functions.RandomToken;
import picasso.parser.RandomAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.RandomFunction;

public class RandomAnalyzerTest {

    public static void main(String[] args) {
        Stack<Token> tokens = new Stack<>();
        tokens.push(new RandomToken());

        RandomAnalyzer randomAnalyzer = new RandomAnalyzer();

        ExpressionTreeNode expressionTreeNode = randomAnalyzer.generateExpressionTree(tokens);

        // check random func instnc
        if (expressionTreeNode instanceof RandomFunction) {
            System.out.println("Test Passed: RandomFunction instance created.");
        } else {
            System.out.println("Test Failed: Unexpected expression tree node.");
        }

        RandomFunction randomFunction = (RandomFunction) expressionTreeNode;
        RGBColor color0 = randomFunction.evaluate(0.0, 0.0);
        RGBColor color1 = randomFunction.evaluate(1.0, 1.0);
        RGBColor color2 = randomFunction.evaluate(-1.0, 1.0);

        if (color0.equals(color1) && color1.equals(color2)) {
            System.out.println("Test Passed: Random function returns the same color for all evaluations");
        } else {
            System.out.println("Test Failed: Random function returned different colors");
        }
        
        if (color0 == color1 && color1 == color2) {
            System.out.println("Test Passed: Random function returns same color instance");
        } else {
            System.out.println("Test Failed: Random function did not cache color");
        }
        
    }
}
