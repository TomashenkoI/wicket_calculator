package com.wicketapp;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Calculation {

    ExpressionTransformer expressionTransformer = new ExpressionTransformer();

    public double calculateExpression(String expression) {
        TreeSet<String> operators = new TreeSet<>();
        operators.add("*");
        operators.add("/");
        operators.add("+");
        operators.add("-");

        String rpn = expressionTransformer.transform(expression);

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

        return stack.pop();
    }
}