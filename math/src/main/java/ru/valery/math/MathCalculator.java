package ru.valery.math;

import ru.valery.plugins.BinaryPlugin;
import ru.valery.plugins.Plugin;
import ru.valery.plugins.UnaryPlugin;
import sun.misc.Queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Класс реализующий обработку вычислений с использованием обратной польской нотации
 */
public class MathCalculator implements Calculator {
    private Deque<Object> values;
    private Stack<Plugin> operationStack;
    private Stack<Double> stack;

    public MathCalculator() {
        values = new ArrayDeque<>();
        operationStack = new Stack<>();
        stack = new Stack<>();
    }

    @Override
    public boolean isCorrectFormula() {
        return false;
    }

    @Override
    public void reset() {

        while (!values.isEmpty()) {
            values.getLast();
        }
        stack.clear();
        operationStack.clear();
    }

    @Override
    public void reset(Double value) {
    }

    @Override
    public Double calculate() {
        stack.clear();
        try {
            while (!operationStack.isEmpty()) {
                values.addLast(operationStack.pop());
            }
            while (!values.isEmpty()) {
                Object object = values.pollFirst();
                if (object instanceof Double) {
                    stack.push((Double) object);
                    continue;
                }
                if (object instanceof BinaryPlugin) {
                    Double v2 = stack.pop();
                    Double v1 = stack.pop();
                    Double result = ((BinaryPlugin) object).calculate(v1, v2);
                    stack.push(result);
                    continue;
                }
                if (object instanceof UnaryPlugin) {
                    Double v1 = stack.pop();
                    Double result = ((UnaryPlugin) object).calculate(v1);
                    stack.push(result);
                    continue;
                }
            }


            return stack.pop();
        } catch (Exception e) {
            return Double.NaN;
        }

    }


    @Override
    public void addValue(Double value) {
        values.addLast(value);
    }

    @Override
    public void addOperation(Plugin plugin) {

        if (operationStack.isEmpty()) {
            operationStack.push(plugin);
            return;
        }
        if (getPriority(operationStack.lastElement()) < getPriority(plugin)) {
            operationStack.push(plugin);
            return;
        }
        if (!Operations.OpenBrace.getPlugin().equals(plugin)) {
            while (!operationStack.isEmpty() && getPriority(operationStack.lastElement()) >= getPriority(plugin)) {
                Plugin op = operationStack.pop();
                if (!Operations.OpenBrace.getPlugin().equals(op)) {
                    values.addLast(op);
                }

            }
            operationStack.push(plugin);
            return;
        }
        operationStack.push(plugin);


    }

    private int getPriority(Plugin plugin) {
        if (plugin instanceof UnaryPlugin) {
            return 4;
        }
        if (plugin instanceof BinaryPlugin) {
            if (((BinaryPlugin) plugin).isHighPriority()) {
                return 3;
            }
            return 2;
        }
        if (Operations.OpenBrace.getPlugin().equals(plugin)) {
            return 1;
        }

        return 0;
    }

}
