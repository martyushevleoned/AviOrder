# Команды для работы с Docker

## Работа с образами
Сборка образа
```
sudo docker build image-name .
```
Скачать образ c docker hub
```
sudo docker pull image-id
```
Удалить образ
```
sudo docker rmi image-id
```
## Работа с контейнерами
Запустить контейнер с приложением (Обязательно указать параметры бд)
```
sudo docker run image-id
```
Остановить контейнер
```
sudo docker stop container-id
```
Удалить контейнер
```
sudo docker rm container-id
```
#### флаги запуска
``--name container-id`` - указать имя контейнера  
``--net=network-id`` - указать в какой сети запустить контейнер  
``-p 80:8080`` - указать внешний и внутренний порт  
``-e username=myName`` - указать переменную окружения  
``-d`` - скрыть консоль контейнера после запуска  
``--rm`` - удалить контейнер после его остановки  
``--restart=always`` - перезапускать контейнер при остановке
## Работа с сетью
Создать сеть
```
sudo docker network create network-id
```
Вывести информацию о сети
```
sudo docker network inspect network-id
```
Вывести список сетей
```
sudo docker network ls
```
Удалить сеть
```
sudo docker network rm network-id
```
## Работа с файлами
Сохранить образа в файл
```
sudo docker save -o /image/file.tar image-id
```
Загрузить образа из файла
```
sudo docker load -i /image/file.tar
```
### Дополнительные команды
Сохранить дамп БД из контейнера
```
sudo docker exec -t docker-postgres pg_dumpall -c -U postgres > dump_`date +%Y-%m-%d"_"%H_%M_%S`.sql
```