![TgBot](https://github.com/W4NTER/financeApp/actions/workflows/tgBot.yml/badge.svg)
![Finance](https://github.com/W4NTER/financeApp/actions/workflows/finance.yml/badge.svg)

💸 **Финансовый Telegram-бот**

Финансовый бот — это Telegram-сервис для отслеживания и обработки финансовой информации. Поддерживает масштабируемую архитектуру с асинхронным взаимодействием между микросервисами через Kafka.

---

## 🚀 Возможности

- 📊 Получение и отслеживание финансовой информации  
- 💬 Взаимодействие через Telegram  
- 🔄 Асинхронное взаимодействие между компонентами (Kafka)  
- 🔍 Мониторинг производительности (JavaMelody) *(не реализовано)*  
- 🧠 Обработка и хранение данных с использованием Spring Data JPA и PostgreSQL  
- 📡 REST API для администрирования и интеграций  
- 🧾 Асинхронная генерация отчётов по операциям  

---

## ⚙️ Используемые технологии

- Java 21  
- Spring Boot  
- Spring Web / WebFlux *(пока не переписывал приложение на реактив)*  
- Spring Kafka  
- Spring Data JPA  
- PostgreSQL  
- Docker / Docker Compose  
- Kafka / KRaft  
- JavaMelody *(не реализовано)*  
- Liquibase  
- Maven  
- [Pengrad Java Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api)  

---

## 🧼 Качество кода

- Checkstyle  
- JaCoCo (Code Coverage)  
- GitHub Actions (CI/CD)  

---

## 🛠 Планы по доработке

- Поддержка Redis для кеширования  
- Расширение бизнес-логики  
- Панель администратора  
- Хранение истории операций  
