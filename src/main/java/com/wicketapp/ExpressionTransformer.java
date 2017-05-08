package com.wicketapp;

import java.util.*;

public class ExpressionTransformer {

    public String transform(String result, String scoreboard) {
        String expression;
        ArrayList<String> operands = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        String leftBracket = "(";
        String rightBracket = ")";

        HashMap<String, Integer> operations = new HashMap<String, Integer>();
        operations.put("*", 1);
        operations.put("/", 1);
        operations.put("+", 2);
        operations.put("-", 2);

        Set<String> operationSymbols = new HashSet<String>(operations.keySet());
        operationSymbols.add(leftBracket);
        operationSymbols.add(rightBracket);

        TreeSet<String> set = new TreeSet<>(operations.keySet());

        if (set.contains(String.valueOf(scoreboard.charAt(0)))) {
            if (result.charAt(0) == '-') {
                expression = 0 + result + scoreboard;
            } else {
                expression = result + scoreboard;
            }
        } else {
            expression = scoreboard;
        }

        int index = 0;
        boolean findNext = true;

        while (findNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = null;

            for (String operation : operationSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }

            if (nextOperationIndex == expression.length()) {
                findNext = false;
            } else {
                if (index != nextOperationIndex) {
                    operands.add(expression.substring(index, nextOperationIndex));
                }
                if (nextOperation.equals(leftBracket)) {
                    stack.push(nextOperation);
                } else if (nextOperation.equals(rightBracket)) {
                    while (!stack.peek().equals(leftBracket)) {
                        operands.add(stack.pop());
                        if (stack.empty()) {
                            throw new IllegalArgumentException("Unmatched brackets");
                        }
                    }
                    stack.pop();
                } else {
                    while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                            operations.get(nextOperation) >= operations.get(stack.peek())) {
                        operands.add(stack.pop());
                    }
                    stack.push(nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }
        if (index != expression.length()) {
            operands.add(expression.substring(index));
        }
        while (!stack.empty()) {
            operands.add(stack.pop());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (!operands.isEmpty()) {
            stringBuilder.append(operands.remove(0));
        }
        while (!operands.isEmpty()) {
            stringBuilder.append(" ").append(operands.remove(0));
        }
        return stringBuilder.toString();
    }

}
