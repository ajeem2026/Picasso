package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * @author Claire Hamlet
 * Represents the minus sign token
 */
public class MinusToken extends CharToken implements OperationInterface {
    public MinusToken() {
        super(CharConstants.MINUS);
    }
}
