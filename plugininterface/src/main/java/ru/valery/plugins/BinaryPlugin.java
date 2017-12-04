package ru.valery.plugins;

public interface BinaryPlugin extends Plugin {
    Double calculate(Double value, Double value2);
    boolean isHighPriority();
}
