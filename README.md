# PhleboMorph


## Prérequis


Pour pouvoir développer sur ce projet, il vous faut :

- le dernier Java JDK 8 ou + (qui inclu JavaFX 8 ou +)
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- Eclipse 4.4 ou supérieur avec le plugin e(fx)clipse. Le plus simple est de télécharger la distribution préconfigurée depuis le site e(fx)clipse.
http://efxclipse.bestsolution.at/install.html#all-in-one

Afin de pouvoir tester pleinement l'application, il faut lancer deux instances: le Client ET le Server.

##Configuration d'Eclipse

​Dans le dossier **Client/**, lancez la commande suivante afin d'installer toutes les dépendances. Ca devrait prendre un petit peu de temps.

```
$ npm install
```

Une fois terminé, vous devriez pouvoir lancer:

```
$ npm start
```

Le client devrait ensuite être disponible à l'adresse suivante : [http://localhost:5555](http://localhost:5555)



#### Installation du Serveur



Dans le dossier **Server/** maintenant, on répète la procédure, c'est à dire:

```
$ npm install
```

Puis

```
$ npm start
```

Vous pouvez également utiliser 'nodemon' afin d'avoir un refresh du serveur à chaque modification:
```
$ (sudo) npm install -g nodemon
$ nodemon index.js
```

Le serveur est ensuite accessible depuis cette adresse: [http://localhost:3000/](http://localhost:3000/)

Cette adresse vous renvoit en toute logique une erreur 404 , car la route '/' n'est pas définie. Mais si on essaie d'accéder à [http://localhost:3000/api/v1/api/](http://localhost:3000/api/v1/api/), on devrait pouvoir voir une réponse dans le navigateur. ('/api/v1' est le base path et est succeptible de changer au cours du projet)


#### Installer la BD en local

**1. Installer PostgreSQL et PostGIS**

[http://www.enterprisedb.com/products-services-training/pgdownload](http://www.enterprisedb.com/products-services-training/pgdownload)

Choisissez comme mot de passe: *padmin* de préférence pour la base de données.

A la fin de l'installation, cochez bien la case permettant d'installer l'extension spatiale postgis

**2. Créer la base de donnée spatiale**

La base de données doit s'appeller mobytick

Soit via pgAdmin en selectionnant comme template postgis_sample

Soit via les commandes:

```
$ psql -U postgres -c 'CREATE DATABASE mobytick;'
$ psql -U postgres -c 'CREATE EXTENSION postgis;'
```

**3. Remplir la base de données**

Via pgAdmin: executer le fichier script.sql à la main 

ou via psql:

Se rendre dans le dossier Server/ puis:

```
$ psql -U postgres -d mobytick -f script.sql
```



**4. Créer la base de données de test**

via pgAdmin: Créer une base de données 'mobytick_test' prenant comme modèle (template) 'mobytick'



via psql:

```
$ psql -U postgres -d mobytick -c 'CREATE DATABASE mobytick_test TEMPLATE mobytick;'
```


#### Documentation de l'API

Pour accéder à la documentation de l'API, vous pouvez consulter: [http://localhost:3000/doc/](http://localhost:3000/doc/)

Pour ajouter votre propre documentation, veuillez suivre les exemples et respecter la librairie Swagger.io
