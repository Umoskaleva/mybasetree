# Программа для построения генеалогического дерева #

## Структура программы: ##
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── mybasetree/
│   │           ├── MyBaseTreeApplication.java
│   │           ├── entity/
│   │           ├── repository/
│   │           ├── service/
│   │           └── controller/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── mybasetree/

### Как это работает: ###
1. Запуск: MyBaseTreeApplication.java - запускает Spring
2. Конфигурация: application.properties - настраивает БД и сервер
3. Запрос: Браузер - controller - service - repository - entity - БД
4. Ответ: БД - entity - repository - service - controller - Браузер




