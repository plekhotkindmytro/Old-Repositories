# SitemapGen
UA Web Challenge VII

## Requirements:

1. Vagrant
2. VirtualBox
3. Java (has to be installed by vagrant)
4. Apache Maven (has to be installed by vagrant)

## Run application: 

1. vagrant up;
2. vagrant provision;
3. vagrant ssh
4. cd /vagrant/
5. Сonfigure application.properties and mail.properties in src/main/resources/ (optional)
7. Run command "mvn clean install" from <project-folder>
8. Run command "mvn jetty:run"
9. Go to URL from browser: http://localhost:8888
