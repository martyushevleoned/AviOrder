# Автоматизация взаимодействия с сервером при помощи Ansible

---
## Установка и настройка Ansible

### Установка
Инструкции по установке из [официальной документации](https://docs.ansible.com/ansible/latest/installation_guide/)

### Настройка
Параметры подключения к хостам указываются в [hosts.yml](../ansible/hosts.yml)

### Содержание hosts.yml
```shell
ansible-inventory -i ../ansible/hosts.yml --list
```
### Пинг хостов
```shell
ansible all -i ../ansible/hosts.yml -m ping
```

---
## Ansible playbook
### Playbook для [локальной работы](../ansible/local)
* [build_app_image](../ansible/local/build_app_image.yml) - собрать образ приложения
* [deploy](../ansible/local/deploy.yml) - локально запустить приложение и БД
* [docker_info](../ansible/local/docker_info.yml) - вывести список образов и контейнеров
* [drop_all_containers](../ansible/local/drop_all_containers.yml) - остановить и удалить все контейнеры
* [drop_all_images](../ansible/local/drop_all_images.yml) - удалить все образы
* [recreate_app_container](../ansible/local/recreate_app_container.yml) - пересоздать контейнер приложения
* [recreate_db_container](../ansible/local/recreate_db_container.yml) - пересоздать контейнер БД
### Playbook для [работы с сервером](../ansible/server)
* [deploy](../ansible/server/deploy.yml) - отправить образ приложения и запустить все сервисы
* [docker_info](../ansible/server/docker_info.yml) - вывести информацию об образах и контейнерых на сервере
* [drop_all](../ansible/server/drop_all.yml) - удалить все контейнеры и образы на сервере
* [update](../ansible/server/update.yml) - отправить образ приложения и перезапустить контейнер
### Playbook [утилиты](../ansible/utils)
* [docker_info](../ansible/utils/docker_info.yml) - вывести список образов и контейнеров
* [drop_all_containers](../ansible/utils/drop_all_containers.yml) - остановить и удалить все контейнеры
* [drop_all_images](../ansible/utils/drop_all_images.yml) - удалить все образы
* [install_docker](../ansible/utils/install_docker.yml) - установить докер

---
## Установка Docker при помощи Ansible
### Установка на локальный компьютер
```shell
ansible-playbook -i ../ansible/hosts.yml -e "host=localhost" ../ansible/utils/install_docker.yml
```
### Установка на сервер
```shell
ansible-playbook -i ../ansible/hosts.yml -e "host=server" ../ansible/utils/install_docker.yml
```