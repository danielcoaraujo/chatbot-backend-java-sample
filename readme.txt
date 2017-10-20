Step by step to test elk persistence:
-download https://github.com/deviantony/docker-elk
-After downloaded, configure /logstash/config/logstash.conf to parse message to json.
-inside the src of docker-elk, run docker-compose up and be happy.

Step by step to test mysql persistence:
-docker pull mysql
-docker run --name chatbotbd -e MYSQL_ROOT_PASSWORD=123 -p3306:3306 -d mysql
-docker exec -it <dockerImage> bash

Inside the container:
-mysql -h localhost -u root -p
-Enter password: 123
-create database chatbotdb;