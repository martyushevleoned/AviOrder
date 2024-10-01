# Команды для работы с SSH

---
## Подключение к серверу
### Сгенерировать SSH ключ
```
ssh-keygen -t rsa
```
### Копирование публичных ключей
```
ssh-copy-id -i .ssh/id_rsa.pub username@ip
```
### Подключиться к серверу
```
ssh username@ip
```
#### Опции:
``-p 22`` - указать порт подключения
### Обновить пакеты
```
apt update && apt upgrade
```

---
## Изменение порта по умолчанию
### Изменить порт
```
nano /etc/ssh/sshd_config
```
### Перезапустить сервис
```
systemctl restart sshd
```

---
## Перенос файлов
### Скопировать файл на сервер с локального компьютера
```
scp /local/path username@ip:/remote/path
```
### Скачать файл с сервера
```
scp username@ip:/remote/path /local/path
```