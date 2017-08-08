# PhleboMorph


## Prérequis


Pour pouvoir développer sur ce projet, il vous faut :

- le dernier Java JDK 8 ou + (qui inclu JavaFX 8 ou +)
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- Eclipse 4.4 ou supérieur avec le plugin e(fx)clipse. Le plus simple est de télécharger la distribution préconfigurée depuis le site e(fx)clipse.
http://efxclipse.bestsolution.at/install.html#all-in-one

Afin de pouvoir tester pleinement l'application, il faut lancer deux instances: le Client ET le Server.

## Configuration d'Eclipse

Paramètrer Eclipse pour qu'il utilise le JDK 8 et qu'il sache où trouver le le Scene Builder :

  - Ouvrez les préférences et sélectionnez la partie Java | Installed JREs.

  - Cliquez sur le bouton Add... pour ajoutez le JDK 8 puis sur Standard VM et sélectionnez le dossier contenant le JDK 8.

  - Supprimez les autres JREs et JDKs afin que le JDK 8 devienne le JDK par défaut (default) !

  - Sélectionnez la partie Java | Compiler. Définissez la Compiler compliance level à 1.8 !

## Récupérer le projet

Une fois Eclipse prêt, il faut récupérer le projet.

1. Télécharger le projet depuis ce dépot github et copier le dossier PhleboMorph dans le dossier workspace que vous aurez choisi pour Eclipse

2. Dans Eclipse, créer un nouveau projet JavaFX (File -> New -> Other... -> JavaFX Project)

3. Donner le nom "PhleboMorph" au projet, celui récupérera automatiquement tous les fichier du dossier PhleboMorph


Leo PERNELLE, aout 2017

Configuration inspirée du tutoriel JavaFX de code.makery.ch
