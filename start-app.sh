#!/bin/bash
echo "Начинаю выполнение скрипта запуска"

echo "Подтягиваю изменения из Git"
git pull

echo "Собираем сборку"
mvn clean install

echo "Убиваем все приложения"
tmux kill-server
sleep 1
tmux ls
sleep 2

echo "Запускаем приложение"
# Запускаем новую сессию tmux в фоновом режиме
tmux new-session -d -s my-session 'mvn spring-boot:run'
# Отображаем список сессий tmux
tmux list-sessions
tmux ls

echo "Рестартуем nginx"
sudo systemctl restart nginx
sudo systemctl status nginx