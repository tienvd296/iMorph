# PhleboMorph


## Prérequis


Pour pouvoir développer sur ce projet, il vous faut :

  - le dernier Java JDK 8 ou + (qui inclu JavaFX 8 ou +)
  http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

  - Eclipse 4.4 ou supérieur avec le plugin e(fx)clipse. Le plus simple est de télécharger la distribution préconfigurée depuis le site   e(fx)clipse.
http://efxclipse.bestsolution.at/install.html#all-in-one


## Configuration d'Eclipse

Paramètrez Eclipse pour qu'il utilise le JDK 8 :

  - Ouvrez les préférences et sélectionnez la partie Java | Installed JREs.

  - Cliquez sur le bouton Add... pour ajouter le JDK 8 puis sur Standard VM et sélectionnez le dossier contenant le JDK 8.

  - Supprimez les autres JREs et JDKs afin que le JDK 8 devienne le JDK par défaut (default) !

  - Sélectionnez la partie Java | Compiler. Définissez la Compiler compliance level à 1.8 !
 


## Récupérer le projet

Une fois Eclipse prêt, il faut récupérer le projet.

  - Téléchargez le projet depuis ce dépot github et copiez le dossier PhleboMorph dans le dossier workspace que vous aurez choisi pour Eclipse

  - Dans Eclipse, créez un nouveau projet JavaFX (File -> New -> Other... -> JavaFX Project)

  - Donnez le nom "PhleboMorph" au projet, celui récupérera automatiquement tous les fichiers du dossier PhleboMorph


Leo PERNELLE, aout 2017

Configuration inspirée du tutoriel JavaFX de code.makery.ch

Version multilingue : http://code.makery.ch/library/javafx-8-tutorial/part1/
