# CAFE SERVER

# Config mysql db
-----------------
Follow this steps.

* docker volume create mysql_data
* docker volume create mysql_config
* docker network create mysqlnet
* docker run -it --rm -d -v mysql_data:/var/lib/mysql -v mysql_config:/etc/mysql/conf.d --network mysqlnet --name mysqldb -e MYSQL_USER=cafe -e MYSQL_PASSWORD=cafe -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cafe -p 3309:3309 mysql:8.0

# Run mysql db
-----------------
* docker exec -ti mysqldb mysql -u root -p

# Run images
-----------------
* docker run --rm -d --name cafe-server --network mysqlnet -e MYSQL_URL=jdbc:mysql://mysqldb/cafe -p 8080:8080 nix322/cafe-server