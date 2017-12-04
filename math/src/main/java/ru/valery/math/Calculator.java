package ru.valery.math;

import ru.valery.plugins.Plugin;

public interface Calculator {
    /**
     *
     * @return Возвращает признак состояния текущей формулы (выражения)
     */
    boolean isCorrectFormula();

    /**
     * Сброс калькулятора в начальное состояние
     */
    void reset();

    /**
     * Сброс калькулятора в начальное состояние
     * @param value Начальное значение для калькулятора
     */
    void reset(Double value);

    /**
     * Метод осуществляющий вычисление формулы/выражения
     * @return результат вычисления
     */
    Double calculate();

    /**
     * Добавление в выражение значения
     * @param value значение
     */
    void addValue(Double value);

    /**
     * Добавление в выражение операции
     * @param name Передаются реализации плагинов
     */
    void addOperation(Plugin name);
}
