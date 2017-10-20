Step by step to test mysql persistence:
-docker pull mysql
-docker run --name chatbotbd -e MYSQL_ROOT_PASSWORD=123 -p3306:3306 -d mysql
-docker exec -it <dockerImage> bash

Inside the container:
-mysql -h localhost -u root -p
-Enter password: 123
-create database chatbotdb;