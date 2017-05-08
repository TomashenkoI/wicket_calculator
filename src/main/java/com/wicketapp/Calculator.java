package com.wicketapp;

import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Calculator {

    private ExpressionTransformer expressionTransformer;

    public String calculateExpression(String result, String scoreboard) {

        TreeSet<String> operators = new TreeSet<>(Arrays.asList(new String[]{"+", "-", "/", "*"}));

        String rpn = expressionTransformer.transform(result, scoreboard);

        StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
        Stack<Double> stack = new Stack<Double>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!operators.contains(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                switch (token) {
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand1 / operand2);
                        break;
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                }
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expression syntax error.");
        }

        return stack.pop().toString();
    }

    public void setExpressionTransformer(ExpressionTransformer expressionTransformer) {
        this.expressionTransformer = expressionTransformer;
    }
}
