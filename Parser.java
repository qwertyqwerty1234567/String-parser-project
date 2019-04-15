

/**
 * Created by liuwanzhen on 17/8/23.
 */
public class Parser {
    private String expStr;
    private int currentIndex;
    private int maxIndex;
    private Token currentToken;

    public Parser(String expStr) {
        this.expStr = expStr;
        this.currentIndex = 0;
        this.maxIndex = expStr.length() - 1;
        advance();
    }

    public double parse() {
        return expr();
    }

    private void slup(String s) {
        if (currentToken.getText().equals(s)) {
            advance();
            return;
        }
        throw new RuntimeException("expect " + s + ",but got " + currentToken.getText());
    }

    private void advance() {
        if (currentIndex > maxIndex) {
            this.currentToken = new Token("", Token.TOKEN_TYPE_EOF);
            return;
        }
        while (isBlank(expStr.charAt(currentIndex))) {
            currentIndex++;
        }
        if (isNumber(expStr.charAt(currentIndex))) {
            int numStart = currentIndex;
            int numEnd = currentIndex;
            while (currentIndex <= maxIndex && (isNumber(expStr.charAt(currentIndex)) || expStr.charAt(currentIndex) == '.')) {
                this.currentIndex++;
            }

            this.currentToken = new Token(expStr.substring(numStart, currentIndex), Token.TOKEN_TYPE_NUMBER);
            return;
        }
        switch (expStr.charAt(currentIndex)) {
            case '(':
                currentIndex++;
                this.currentToken = new Token("(", Token.TOKEN_TYPE_SYMBOL_LEFT);
                break;
            case ')':
                currentIndex++;
                this.currentToken = new Token(")", Token.TOKEN_TYPE_SYMBOL_RIGHT);
                break;
            case '+':
                currentIndex++;
                this.currentToken = new Token("+", Token.TOKEN_TYPE_OP_ADD);
                break;
            case '-':
                currentIndex++;
                this.currentToken = new Token("-", Token.TOKEN_TYPE_OP_SUB);
                break;
            case '*':
                currentIndex++;
                this.currentToken = new Token("*", Token.TOKEN_TYPE_OP_MUL);
                break;
            case '/':
                currentIndex++;
                this.currentToken = new Token("/", Token.TOKEN_TYPE_OP_DIV);
                break;

        }


    }

    private boolean isBlank(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * expr ::= term ((+|-) term)*
     * term ::= factor ((*|/) factor)*
     * factor ::= number | expr
     * number ::= [0-9.]+
     *
     * @return
     */
    private double expr() {
        double ret = term();
        while (currentToken.getType() == Token.TOKEN_TYPE_OP_ADD || currentToken.getType() == Token.TOKEN_TYPE_OP_DIV) {
            String text = currentToken.getText();
            slup(text);
            if ("+".equals(text)) {
                ret += term();
            } else {
                ret -= term();
            }

        }
        return ret;
    }

    private double term() {
        double ret = factor();
        while (currentToken.getType() == Token.TOKEN_TYPE_OP_MUL || currentToken.getType() == Token.TOKEN_TYPE_OP_DIV) {
            String text = currentToken.getText();
            slup(text);
            if ("*".equals(text)) {
                ret *= term();
            } else {
                ret /= term();
            }
        }
        return ret;
    }

    private double factor() {
        double ret = 0;
        int currentTokenType = currentToken.getType();
        switch (currentTokenType) {
            case Token.TOKEN_TYPE_SYMBOL_LEFT:
                slup("(");
                ret = expr();
                slup(")");
                break;
            case Token.TOKEN_TYPE_NUMBER:
                ret = Double.parseDouble(currentToken.getText());
                slup(currentToken.getText());
                break;
            default:
                throw new RuntimeException("unexpected exception");
        }
        return ret;
    }

    public static void main(String[] args) {
        String[] exprs = new String[]{"1+2", "1 + 2", "2+3*4", " (2 + 3 ) * 4", " (2 + 5 )/2", " (2.3 + 5.7 )/2"};
        for (String expr : exprs) {
            Parser parser = new Parser(expr);
            double ret = parser.parse();
            System.out.println(expr + "=" + ret);
        }

    }
}
