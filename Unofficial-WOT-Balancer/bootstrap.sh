#install java
sudo apt-get update
sudo apt-get -y install openjdk-6-jdk
sudo apt-get -y install maven2

cd /vagrant
chmod 777 run_balancer.sh
