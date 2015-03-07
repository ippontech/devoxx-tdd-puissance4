#Test Driven Development
Dans ce concours, nous vous proposons d'appliquer le Test-Driven Development à un cas concret (et volontairement assez simple).

L'exercice à réaliser consiste à développer selon les principes TDD le moteur d'un jeu de Puissance4© capable de stocker l'état de la partie, de  valider un coup, et de déterminer si le jeu est terminé et qui est le vainqueur. 
Pour ce faire, vous devrez écrire une classe Puissance4Impl qui implémente l'interface Puissance4 qui vous est fournie. Conformément aux principes TDD, votre classe doit permettre de passer l'ensemble des tests unitaires définis définis par l'équipe fonctionnelle dans la classe Puissance4Test - qui vous est également fournie. Pour plus de détails, vous êtes invités à consulter le fichier Enonce.pdf.

Vous serez évalués sur la cohérence de votre applicatif avec ces spécifications (il doit passer un maximum des tests unitaires fournis – tous étant le niveau recherché), sur la lisibilité du code et sa rapidité d'exécution (le plus petit nombre d'instructions pour évaluer le jeu étant le meilleur). 

Pour soumettre votre réponse, et gagner une place pour Devoxx, vous devrez nous envoyer le zip généré par la commande 
	mvn package
par mail à jmcaillaud@ippon.fr
