UnofficialWOTBalancer
=====================

Unofficial balancer for World Of Tanks clan games (console version).

Requirements:
1. Vagrant;
2. VirtualBox
3. Java (has to be installed by vagrant)
4. Apache Maven (has to be installed by vagrant)

Steps to run the balancer:
Run next commands using terminal:
1. vagrant up;
2. vagrant provision;
3. vagrant ssh
4. cd /vagrant/
5. mvn clean install 
6. cd target/
7. java -jar balancer-0.0.1-SNAPSHOT-jar-with-dependencies.jar
