# Домашняя работа №4 по КПО: Асинхронное межсервисное взаимодействие
## Хашпаков Астемир, БПИ242

## Описание проекта
Реализация системы интернет-магазина «Гоzон» с микросервисной архитектурой для обработки заказов и платежей в условиях высокой нагрузки.

## Архитектура
Система состоит из трёх микросервисов:

### API Gateway
- Единая точка входа для всех запросов
- Маршрутизация запросов к соответствующим сервисам
- Интеграция Swagger UI для документации API

### Order Service
**Ответственность:** Управление заказами пользователей  
**API эндпоинты:**
- `POST /orders/` - Создать новый заказ (запускает асинхронный процесс оплаты)
- `GET /orders/{id}` - Получить статус заказа

**Статусы заказа:**
- `NEW` - Заказ создан
- `FINISHED` - Оплата успешно завершена
- `CANCELLED` - Оплата не удалась

### Payments Service
**Ответственность:** Управление счетами и обработка платежей  
**API эндпоинты:**
- `POST /payments/` - Создать счет для пользователя
- `POST /payments/{userId}` - Пополнить счет
- `GET /payments/{id}` - Получить текущий баланс счета

## Технологический стек
- **Язык:** Java 17+
- **Фреймворк:** Spring Boot 3
- **База данных:** PostgreSQL (отдельная БД для каждого сервиса)
- **Брокер сообщений:** Apache Kafka
- **Оркестрация:** Docker Compose
- **Документация:** OpenAPI 3 / Swagger UI

## Ключевые архитектурные паттерны

### Transactional Outbox (Order Service)
При создании заказа в рамках одной транзакции:
1. Сохраняется заказ в БД
2. Сохраняется событие в таблицу `outbox_order`
3. Отдельный процесс (`@Scheduled`) отправляет события в Kafka

### Transactional Inbox + Outbox (Payments Service)
**Inbox (получение):**
1. Получение события из Kafka
2. Сохранение в таблицу `inbox_order`
3. Обработка с идемпотентностью (исключение дублей)

**Outbox (отправка):**
1. Сохранение результата оплаты в таблицу `outbox_order_processed_event`
2. Отправка статуса оплаты обратно в Order Service

### Exactly-Once семантика
- Идемпотентная обработка платежей через проверку дублей
- Гарантированное однократное списание средств

## Запуск проекта

### Требования
- Docker 20.10+
- Docker Compose 2.0+
- 4GB свободной RAM (для Kafka, Zookeeper, PostgreSQL и сервисов)

### Быстрый запуск
```bash
# Собрать и запустить все сервисы
docker compose up --build
```

### Сервисы и порты
После запуска будут доступны:
- **API Gateway:** http://localhost:8080
- **Order Service:** Прямой доступ внутри Docker контейнера (см. примеры ниже)
- **Payments Service:** http://localhost:8080/payments
- **Kafka UI (мониторинг):** http://localhost:8085
- **Swagger UI:** http://localhost:8080/webjars/swagger-ui/index.html

## Работа с API

### Через Swagger UI
1. Откройте http://localhost:8080/webjars/swagger-ui/index.html
2. В правом верхнем углу выберите нужный сервис:
    - `gateway-api` - общая документация
    - `orders` - API заказов
    - `payments` - API платежей

### Примеры запросов

#### 1. Создание счета
curl -X POST "http://localhost:8080/payments/?userId=100&balance=1000"
#### 2. Пополнение счета
curl -X POST "http://localhost:8080/payments/100?delta=500"
#### 3. Создание заказа
docker exec order-service curl -X POST "http://localhost:8080/orders/?userId=100&amount=300&description=Ноутбук"
#### 4. Проверка статуса заказа
docker exec order-service curl "http://localhost:8080/orders/1"

## Особенности реализации

### Асинхронная обработка
- Создание заказа → Kafka → Оплата → Kafka → Обновление статуса
- Периодичность обработки очередей: 500ms

### Гарантии доставки
- At-least-once доставка через Kafka
- Exactly-once семантика через идемпотентную обработку
- Транзакционное сохранение событий

### Безопасность и надежность
- Отдельные БД для каждого сервиса
- Проверка отрицательного баланса
- Обработка конкурентных операций

## Структура проекта
```
.
├── docker-compose.yml          # Оркестрация всех сервисов
├── gateway/                    # API Gateway
│   ├── Dockerfile
│   └── src/main/java/gateway/
├── orders/                     # Order Service
│   ├── Dockerfile
│   └── src/main/java/order_service/
├── payments/                   # Payments Service  
│   ├── Dockerfile
│   └── src/main/java/payments_service/
└── README.md
```

## Остановка проекта
```bash
# Остановить все контейнеры
docker compose down
```