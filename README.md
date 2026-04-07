# JavaFX MVC Converter

Учебный проект по JavaFX с архитектурой MVC.

Что реализовано:
- `ConverterModel` с логикой конвертации и коэффициентами.
- `ConverterController` с обработкой кнопок, валидацией и историей.
- `ConverterView.fxml` с графическим интерфейсом.
- `InputValidator` с несколькими проверками ввода.
- `RateProvider` для загрузки курсов из API с безопасным локальным фолбэком.

Структура:
- `src/converter/Main.java`
- `src/converter/model/ConverterModel.java`
- `src/converter/controller/ConverterController.java`
- `src/converter/service/RateProvider.java`
- `src/converter/util/InputValidator.java`
- `src/converter/view/ConverterView.fxml`

Запуск в IDE:
1. Откройте папку проекта.
2. Убедитесь, что используется JDK 8 с JavaFX.
3. Запустите класс `converter.Main`.

Для проверки модели отдельно можно запустить `converter.model.ConverterModel`.
