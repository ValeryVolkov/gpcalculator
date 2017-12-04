package ru.valery.math;

import ru.valery.plugins.BinaryPlugin;
import ru.valery.plugins.Plugin;
import ru.valery.plugins.UnaryPlugin;

import java.util.Collection;

public interface Calculator {
    boolean isCorrectFormula();
    void reset();
    void reset(Double value);
    Double calculate();

    void addValue(Double value);

    void addOperation(Plugin name);
}
