# JavaFX MVC Converter

Учебный проект на JavaFX по архитектуре MVC.

Что есть в проекте:
- `pom.xml` для Maven-сборки
- `src/` с Java-кодом
- `resources/` с `FXML`
- поддержка `USD`, `EUR`, `RUB`, `BTC`, `ETH`, `XAU`, `XAG`
- валидация ввода и история конвертаций

Запуск вручную:
1. Скомпилировать:
   `D:\GigaIde\jbr\bin\javac.exe --release 8 -cp "C:\Program Files\Java\jre1.8.0_51\lib\ext\jfxrt.jar" -d out-manual ...`
2. Скопировать `resources/converter/view/ConverterView.fxml` в `out-manual/converter/view/`
3. Запустить:
   `C:\Program Files\Java\jre1.8.0_51\bin\java.exe -cp out-manual converter.Main`

Maven:
- `pom.xml` добавлен и настроен под текущую структуру папок.
- На этой машине Maven сейчас блокируется SSL-ошибкой при догрузке стандартных плагинов из `repo.maven.apache.org`.
- Сам код рабочий и вручную запускается.
