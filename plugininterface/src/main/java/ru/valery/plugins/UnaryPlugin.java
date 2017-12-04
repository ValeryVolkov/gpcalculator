package ru.valery.plugins;

/**
 * Интерфейс для унарных математических операций
 * К таким относятся функции: sqrt, sqr, etc.
 */
public interface UnaryPlugin extends Plugin {
    /**
     *
     * @param value операнд над которым будет выполнена операция
     * @return результат вычислений
     */
    Double calculate(Double value);
}
