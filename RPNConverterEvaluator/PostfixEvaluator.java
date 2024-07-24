package ReversePolishNotationConverter.RPNConverterEvaluator;

import java.util.*;

public class PostfixEvaluator {

    private String expression;
    private String postfix = "";
    private final Deque<Character> operatorStack;
    public Map<Character, Integer> operatorsMap = new HashMap<>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
        put('^', 3);

    }};

    public Map<Character, Boolean> leftAssociativityMap = new HashMap<>(){{
        put('+', true);
        put('-', true);
        put('*', true);
        put('/', true);
        put('^', false);
    }};

    public PostfixEvaluator(String expression) {
        this.expression = expression.trim().replaceAll("\\s+", "");
        this.operatorStack = new LinkedList<>();

        generateNotation();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        generateNotation();
    }

    private boolean isHigherPriorityOrEqual(char prevOperator, char currentOperator) {
        if (!operatorsMap.containsKey(prevOperator) || !operatorsMap.containsKey(currentOperator)) {
            return false;
        }

        int priority1 = operatorsMap.get(prevOperator);
        int priority2 = operatorsMap.get(currentOperator);

        if (priority1 > priority2){
            return true;
        }

        if (priority1 < priority2){
            return false;
        }
        else {
            return leftAssociativityMap.get(currentOperator);
        }
    }






    private void generateNotation() {
        for (char c : this.expression.toCharArray()){
            if (!operatorsMap.containsKey(c) && c != ')' && c != '('){
                postfix += c ;
            }

            else if (c == '('){
                operatorStack.push(c);
            }

            else if (c == ')'){
                while (!operatorStack.isEmpty() && operatorStack.peek() != '('){
                    postfix += operatorStack.pop();
                }
                operatorStack.pop();

            }
            else if (operatorsMap.containsKey(c)) {

                while (!operatorStack.isEmpty() && isHigherPriorityOrEqual(operatorStack.peek(), c)) {
                    postfix += operatorStack.pop();

                }
                operatorStack.push(c);


            }

        }
        while(!operatorStack.isEmpty()){
            postfix += operatorStack.pop();
        }

    }


    public static int evaluateNotation(String postfix) {
        char[] chars = postfix.toCharArray();
        Integer result;
        Stack<Integer> charsStack = new Stack<>();
        for (char c : chars){
            if (Character.isDigit(c)){
                charsStack.push(Character.getNumericValue(c));
            }
            else {
                int op1 = charsStack.pop();
                int op2 = charsStack.pop();
                switch (c){
                    case '+':
                        charsStack.push(op2+op1);
                        break;

                    case '-':
                        charsStack.push(op2-op1);
                        break;

                    case '*':
                        charsStack.push(op2 * op1);
                        break;
                    case '/':
                        charsStack.push(op2 / op1);
                        break;

                    case '^':
                        charsStack.push((int)Math.pow(op2,op1));
                        break;

                }
            }

        }
        return charsStack.pop();
    }

    public String getPostfix() {
        return postfix;
    }
}

 class Main {
    public static final int POSTFIX_GENERATE = 1;
    public static final int POSTFIX_EVAL = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int exercise = scanner.nextInt();
        scanner.nextLine();
        String expression = scanner.nextLine();

        switch (exercise){
            case POSTFIX_GENERATE:
                PostfixEvaluator e = new PostfixEvaluator(expression);
                System.out.println(e.getPostfix());
                break;

            case POSTFIX_EVAL:
                System.out.println(PostfixEvaluator.evaluateNotation(expression));
                break;

        }
    }
}
