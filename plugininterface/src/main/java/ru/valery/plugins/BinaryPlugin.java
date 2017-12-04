package ru.valery.plugins;

/**
 * Интерфейс для бинарных математических операций
 * таких как + - / *
 */
public interface BinaryPlugin extends Plugin {
    /**
     *
     * @param value Первый операнд
     * @param value2 Второй операнд
     * @return результат вычисления
     */
    Double calculate(Double value, Double value2);

    /**
     *
     * @return Признак повышенного приоритета операции - должен возвращать true для операций * /
     */
    boolean isHighPriority();
}
