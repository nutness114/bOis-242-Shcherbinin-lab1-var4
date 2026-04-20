# JavaFX MVC Converter

Учебный проект на JavaFX по архитектуре MVC для лабораторной работы.

## Что делает приложение

Программа конвертирует:
- валюты: `USD`, `EUR`, `RUB`
- криптовалюты: `BTC`, `ETH`
- драгоценные металлы: `XAU`, `XAG`

В проекте реализованы:
- архитектура MVC
- интерфейс на `FXML`
- поддержка дробных чисел
- валидация ввода
- история последних конвертаций
- загрузка курсов через API
- локальные резервные коэффициенты, если API недоступен

## Что сделано для переносимости

Проект обновлён так, чтобы его можно было открыть на другом устройстве после клонирования из GitHub и запустить без привязки к вашему локальному `jre1.8.0_51`.

Основные изменения:
- добавлен переносимый [pom.xml](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/pom.xml)
- JavaFX подключается через Maven и `OpenJFX`
- убрана жёсткая зависимость от локального пути `C:\Program Files\Java\jre1.8.0_51`
- проект переведён на стандартную Maven-структуру:
  - `src/main/java`
  - `src/main/resources`
- добавлен Maven Wrapper:
  - `mvnw`
  - `mvnw.cmd`
  - `.mvn/wrapper`
- в `.gitignore` добавлены локальные служебные папки IDE и сборки

## Структура проекта

- [pom.xml](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/pom.xml)
- [Main.java](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/java/converter/Main.java)
- [ConverterController.java](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/java/converter/controller/ConverterController.java)
- [ConverterModel.java](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/java/converter/model/ConverterModel.java)
- [RateProvider.java](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/java/converter/service/RateProvider.java)
- [InputValidator.java](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/java/converter/util/InputValidator.java)
- [ConverterView.fxml](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/resources/converter/view/ConverterView.fxml)

## Как взять проект с GitHub

Склонируйте репозиторий:

```powershell
git clone https://github.com/nutness114/bOis-242-Shcherbinin-lab1-var4.git
cd bOis-242-Shcherbinin-lab1-var4
```

После этого проект можно открыть в `GIGA IDE`.

## Что нужно на другом устройстве

Чтобы проект работал после загрузки из GitHub, на другом компьютере желательно иметь:
- `JDK 17` или новее
- `GIGA IDE` или другую Java IDE
- доступ к интернету для первой загрузки Maven и `OpenJFX`

Отдельно установленный Maven не обязателен, потому что в проект уже добавлен Maven Wrapper.

## Как запустить в GIGA IDE

### Вариант 1. Через Maven

Это основной и самый удобный способ.

1. Откройте проект в `GIGA IDE`.
2. Дождитесь индексации.
3. Откройте окно `Maven`.
4. Нажмите `Reload All Maven Projects`.
5. Откройте раздел `Plugins`.
6. Найдите `javafx`.
7. Запустите цель `javafx:run`.

Важно:
- в настройках проекта или Maven должен использоваться `JDK 17+`, а не старый `JRE 8`
- если IDE спросит, какой JDK выбрать, укажите `JDK 17` или встроенный `JBR` от `GIGA IDE`

### Вариант 2. Через класс Main

Если Maven уже подгрузил зависимости и IDE всё распознала:

1. Откройте [Main.java](/D:/Student/Щербинин/Кроссплатформы/Лабы/Labb%201/src/main/java/converter/Main.java).
2. Нажмите зелёный треугольник рядом с методом `main`.
3. Выберите `Run 'Main.main()'`.

## Как запустить вручную в GIGA IDE

Если хотите именно ручной запуск через встроенный терминал `GIGA IDE`, лучше использовать Maven Wrapper:

1. Откройте проект.
2. Откройте вкладку `Terminal`.
3. Выполните команду:

```powershell
.\mvnw.cmd javafx:run
```

Если терминал запущен на неправильной Java, сначала задайте `JAVA_HOME`:

```powershell
$env:JAVA_HOME="D:\GigaIde\jbr"
$env:Path="D:\GigaIde\jbr\bin;" + $env:Path
.\mvnw.cmd javafx:run
```

## Как собрать проект вручную

Компиляция:

```powershell
.\mvnw.cmd clean compile
```

Запуск:

```powershell
.\mvnw.cmd javafx:run
```

Сборка jar:

```powershell
.\mvnw.cmd clean package
```

После первой сборки зависимости `OpenJFX` сохранятся локально, и дальше проект будет запускаться проще.
