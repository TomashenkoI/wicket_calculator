package com.wicketapp;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculation {
    ExpressionTransformation et = new ExpressionTransformation();

    public double calculateExpression(String expression) {
        ArrayList<String> operations1 = new ArrayList<>();
        operations1.add("*");
        operations1.add("/");
        operations1.add("+");
        operations1.add("-");

        String rpn = et.expressionTransformation(expression);
        StringTokenizer tokenizer = new StringTokenizer(rpn, " ");
        Stack<Double> stack = new Stack<Double>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (!operations1.contains(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                if (token.equals("*")) {
                    stack.push(operand1*operand2);
                } else if (token.equals("/")) {
                    stack.push(operand1/operand2);
                } else if (token.equals("+")) {
                    stack.push(operand1+operand2);
                } else if (token.equals("-")) {
                    stack.push(operand1-operand2);
                }
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expression syntax error.");
        }

        return stack.pop();
    }
}