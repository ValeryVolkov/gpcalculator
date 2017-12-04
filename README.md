# gpcalculator
Калькулятор с динамической подгрузкой операций

**Проектные директории должны находится на одном уровне иерархии (необходимо для корректной упаковки плагинов)**

git clone https://github.com/ValeryVolkov/gpcalculator.git

git clone https://github.com/ValeryVolkov/mathoperations.git



Сборка проекта gpcalculator
--
cd gpcalculator

mvn clean package

Запуск приложения
--
cd calculator/target

java -jar calculator-1.0-SNAPSHOT-jar-with-dependencies.jar {полный путь к файлу конфигурации: calculator.properties}

Пример команды для запуска приложения:
--
cd calculator/target

java -jar calculator-1.0-SNAPSHOT-jar-with-dependencies.jar /Users/valery/calculator.properties

Файл конфигурации calculator.properties
--
plugins.path={путь к расположению плагинов}

Пример содержимого файла конфигурации
--
plugins.path=/Users/valery/IdeaProjects/calculator-plugins


Сборка проекта с реализацией математических плагинов
--

cd mathoperations

mvn clean package

После сборки плагинов - можем деплоить jar'ники в каталог {plugins.path}