# gpcalculator
Калькулятор с динамической подгрузкой операций

Сборка проекта
--
mvn clean package

Запуск приложения
--
java -jar calculator-1.0-SNAPSHOT-jar-with-dependencies.jar {полный путь к файлу конфигурации: calculator.properties}

Пример:
--
java -jar calculator-1.0-SNAPSHOT-jar-with-dependencies.jar /Users/valery/calculator.properties

Файл конфигурации calculator.properties
--
plugins.path={путь к расположению плагинов}

Пример
--
plugins.path=/Users/valery/IdeaProjects/calculator-plugins

