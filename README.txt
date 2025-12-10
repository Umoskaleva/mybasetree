# Программа для построения генеалогического дерева #

## Структура программы: ##
mybasetree/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── mybasetree/
│   │   │           ├── MyBaseTreeApplication.java         # Точка входа
│   │   │           ├── TestPerson.java                    # Тестовый класс в корне пакета
│   │   │           │
│   │   │           ├── entity/                            # Сущности JPA
│   │   │           │   ├── Person.java
│   │   │           │   ├── Address.java
│   │   │           │   ├── Photo.java
│   │   │           │   ├── Relationship.java
│   │   │           │   ├── Gender.java
│   │   │           │   ├── AddressType.java
│   │   │           │   └── RelationshipRole.java
│   │   │           │
│   │   │           ├── repository/                        # Репозитории
│   │   │           │   ├── PersonRepository.java
│   │   │           │   └── AddressRepository.java
│   │   │           │
│   │   │           ├── service/                           # Сервисы
│   │   │           │   ├── PersonService.java
│   │   │           │   └── AddressService.java
│   │   │           │
│   │   │           ├── controller/                        # Контроллеры
│   │   │           │   ├── api/                           # API контроллеры
│   │   │           │   │   ├── PersonApiController.java
│   │   │           │   │   └── TreeApiController.java
│   │   │           │   ├── web/                           # Web контроллеры
│   │   │           │   │   ├── PersonWebController.java
│   │   │           │   │   └── TreeWebController.java
│   │   │           │   └── dto/                           # Data Transfer Objects
│   │   │           │       └── TreeNode.java              # Узел генеалогического дерева (для JSON)
│   │   │           │
│   │   │           └── exception/                         # Обработка исключений
│   │   │               ├── PersonNotFoundException.java   # Кастомное исключение
│   │   │               └── GlobalExceptionHandler.java    # Глобальный обработчик ошибок
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   └── js/
│   │       │       └── app.js
│   │       │
│   │       ├── templates/                                  # HTML-шаблоны Thymeleaf
│   │       │   ├── error.html
│   │       │   ├── family-tree.html
│   │       │   ├── person-details.html
│   │       │   ├── search-form.html
│   │       │   ├── tree.html
│   │       │   ├── tree-fragment.html
│   │       │   └── tree-selector.html
│   │       │
│   │       ├── application.properties                      # Общие настройки
│   │       ├── application-dev.properties                  # Для разработки
│   │       ├── application-prod.properties                 # Для продакшена
│   │       └── data.sql
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── mybasetree/
│                   └── TestPerson                          # Тесты
│
└── pom.xml                                                 # Зависимости Maven


---

## ⚙️ Как это работает

1. **Запуск**: `MyBaseTreeApplication.java` инициализирует Spring Boot.
2. **Конфигурация**: `application.properties` настраивает подключение к БД и параметры сервера.
3. **Запрос**:  
   Браузер → `@Controller` → `@Service` → `@Repository` → `@Entity` → База данных
4. **Ответ**:  
   База данных → `@Entity` → `@Repository` → `@Service` → `@Controller` → Браузер

---

## 🎯 Возможности

- 📝 Хранение подробной информации о человеке:
  - Имя, фамилия, девичья фамилия, отчество, прозвище
  - Пол, даты рождения, брака, развода, смерти
- 📍 Адреса с исторической детализацией:
  - Место рождения
  - До 3 мест проживания
  - До 3 мест работы  
  Формат: *губерния → уезд → волость → населённый пункт*
- 👨‍👩‍👧‍👦 Семейные связи:
  - Родители, дети, супруги, братья/сёстры, тёти/дяди, крёстные
  - Поддержка приёмных отношений
- 🔍 Гибкий поиск:
  - По имени, фамилии, дате рождения (до/после)
  - По губернии или населённому пункту (рождения, проживания, любого адреса)

---

## 🛠 Технологии

- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- Thymeleaf (HTML-шаблоны)
- Встроенная БД H2 (по умолчанию)

---

## 🚀 Как запустить

1. Установите JDK 17+
2. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/Umoskaleva/mybasetree.git
   cd mybasetree




