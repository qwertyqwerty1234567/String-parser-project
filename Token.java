
/**
 * Created by liuwanzhen on 17/8/23.
 */
public class Token {
    public static final int TOKEN_TYPE_EOF = 0; 
    public static final int TOKEN_TYPE_NUMBER = 1; 

    public static final int TOKEN_TYPE_SYMBOL_LEFT = 2; 
    public static final int TOKEN_TYPE_SYMBOL_RIGHT = 3;

    public static final int TOKEN_TYPE_OP_ADD = 4;
    public static final int TOKEN_TYPE_OP_SUB = 5;
    public static final int TOKEN_TYPE_OP_MUL = 6;
    public static final int TOKEN_TYPE_OP_DIV = 7;

    private String text;
    private int type;


    public Token(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
