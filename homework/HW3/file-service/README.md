# Домашнее задание 3 по КПО

# Система проверки работ на плагиат

#### Хашпаков Астемир БПИ242

## Краткое описание

Система для проверки студенческих работ на плагиат с микросервисной архитектурой. Состоит из трёх сервисов:
1. **File Storage Service** - хранение файлов
2. **File Analysis Service** - анализ файлов и проверка плагиата
3. **API Gateway** - единая точка входа

## Архитектура системы

```
Клиент → API Gateway (8080) → Маршрутизация
                          │
                          ├──→ Storage Service (8081)
                          │      ├── Сохраняет файлы на диск
                          │      └── Хранит метаданные в PostgreSQL
                          │
                          └──→ Analysis Service (8082)
                                 ├── Анализирует тексты
                                 ├── Проверяет плагиат по хешу SHA-256
                                 └── Генерирует облака слов через QuickChart API
```

**Технологии:** Spring Boot 3, Java 21, PostgreSQL, Docker, Gradle

## Пользовательские сценарии

### Сценарий 1: Загрузка и проверка работы студента

**1. Загрузка файла:**
```
Клиент → POST /api/files/upload (Gateway:8080)
        ↓
Gateway → Storage Service:8081
        ↓
Storage → Сохраняет файл на диск
        → Вычисляет SHA-256 хеш
        → Сохраняет метаданные в БД
        ↓
Ответ: {id, name, hash, path}
```

**2. Запуск анализа:**
```
Клиент → GET /api/analysis/{fileId} (Gateway:8080)
        ↓
Gateway → Analysis Service:8082
        ↓
Analysis → GET /api/files/{fileId}/content (Storage:8081)
        ↓
Storage → Возвращает содержимое файла
        ↓
Analysis → Анализирует текст:
          - Считает параграфы, слова, символы
          - Сохраняет статистику в БД
        ↓
Ответ: Статистика файла
```

**3. Проверка на плагиат:**
```
Клиент → GET /api/analysis/{fileId}/plagiarised (Gateway:8080)
        ↓
Gateway → Analysis Service:8082
        ↓
Analysis → GET /api/files/{fileId}/same (Storage:8081)
        ↓
Storage → Находит файлы с таким же хешем в БД
        → Возвращает количество совпадений
        ↓
Analysis → Если совпадений > 1 → ПЛАГИАТ (true)
          Иначе → УНИКАЛЬНО (false)
```

**4. Генерация облака слов:**
```
Клиент → GET /api/analysis/{fileId}/wordcloud (Gateway:8080)
        ↓
Gateway → Analysis Service:8082
        ↓
Analysis → GET /api/files/{fileId}/content (Storage:8081)
        ↓
Analysis → Отправляет текст на QuickChart API
        → Получает PNG изображение
        ↓
Ответ: Изображение облака слов
```

### Сценарий 2: Получение информации о файле

**Прямое взаимодействие с Storage:**
```
Клиент → GET /api/files/{id} (Gateway:8080)
        ↓
Gateway → Storage Service:8081
        ↓
Storage → Ищет файл в БД по ID
        ↓
Ответ: Метаданные файла
```

### Технические сценарии обмена данными

**1. REST API между Analysis и Storage:**
```java
// Analysis Service вызывает Storage Service
String content = restTemplate.getForObject(
    "http://storage:8081/api/files/{fileId}/content", 
    String.class
);

Integer sameCount = restTemplate.getForObject(
    "http://storage:8081/api/files/{fileId}/same", 
    Integer.class
);
```

**2. Работа с базой данных:**
- **Storage Service**: Таблица `files` (id, name, path, hash)
- **Analysis Service**: Таблица `statistics` (file_id, paragraphs, words, symbols)
- Оба сервиса используют одну БД `file_db` (PostgreSQL)

**3. Алгоритм проверки плагиата:**
```
1. При загрузке файла → вычисляется SHA-256 хеш
2. Хеш сохраняется в БД Storage
3. При проверке плагиата → поиск файлов с таким же хешем
4. Если нашли файл от другого студента → плагиат
```

## Быстрый запуск

```bash
docker-compose up
```

**Доступ к системе:**
- Swagger UI: http://localhost:8080/swagger-ui
- Storage: http://localhost:8081
- Analysis: http://localhost:8082

**Пример использования:**
```bash
# Загрузить файл
curl -X POST http://localhost:8080/api/files/upload \
  -F "file=@homework.txt"

# Проверить на плагиат
curl http://localhost:8080/api/analysis/1/plagiarised

# Получить облако слов
curl http://localhost:8080/api/analysis/1/wordcloud -o cloud.png
```

## Ключевые особенности

- **Микросервисная архитектура** с четким разделением ответственности
- **Алгоритм антиплагиата**: проверка по SHA-256 хешам
- **Облако слов**: интеграция с QuickChart API
- **Контейнеризация**: полный запуск через docker-compose
- **Документация**: Swagger UI для всех API
- **Тестирование**: покрытие тестами >70%