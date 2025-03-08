package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents an expression read from the first line of a file 
 * 
 * @author Abid Jeem
 */
public class FileExpression extends ExpressionTreeNode {

    private ExpressionTreeNode expression;

    /**
     * Constructs a FileExpression.
     *
     * @param expression the parsed expression tree from the file
     */
    public FileExpression(ExpressionTreeNode expression) {
        this.expression = expression;
    }

    @Override
    public RGBColor evaluate(double x, double y) {
        return expression.evaluate(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileExpression) {
            FileExpression other = (FileExpression) obj;
            return this.expression.equals(other.expression);
        }
        return false;
    }

    @Override
    public String toString() {
        return "FileExpression: " + expression.toString();
    }
}
