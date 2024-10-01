# Команды для работы с Docker

## Работа с образами
### Сборка образа
```
docker build -t image-name .
```
### Скачать образ c docker hub
```
docker pull image-id
```
### Удалить образ
```
### docker rmi image-id
```

---
## Работа с контейнерами
### Запустить контейнер
```
docker run image-id
```
#### Опции:
``--name container-id`` - указать имя контейнера  
``--net=network-id`` - указать в какой сети запустить контейнер  
``-p 80:8080`` - указать внешний и внутренний порт  
``-e username=myName`` - указать переменную окружения  
``-d`` - скрыть консоль контейнера после запуска  
``--rm`` - удалить контейнер после его остановки  
``--restart=always`` - перезапускать контейнер при остановке

### Остановить контейнер
```
docker stop container-id
```
### Удалить контейнер
```
docker rm container-id
```

---
## Работа с сетью
### Создать сеть
```
docker network create network-id
```
### Вывести информацию о сети
```
docker network inspect network-id
```
### Вывести список сетей
```
docker network ls
```
### Удалить сеть
```
docker network rm network-id
```

---
## Работа с файлами
### Сохранить образа в файл
```
docker save -o /image/file.tar image-id
```
### Загрузить образа из файла
```
docker load -i /image/file.tar
```

---
## Дополнительные команды
### Сохранить дамп БД из контейнера
```
docker exec -t docker-postgres pg_dumpall -c -U postgres > dump_`date +%Y-%m-%d"_"%H_%M_%S`.sql
```