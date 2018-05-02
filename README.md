## Compilation
La compilation avec Maven et le fichier pom.xml
```
mvn [clean] install
```

## Exécution des classes
```
# Fonction main de PlateauFousFous
java -cp target/harba_le-sciellour-1.0.jar fr.upsud.PlateauFousFous <FILE_OUTPUT> <FILE_INPUT>

# Jeu avec une seule IA (IA vs IA)
java -cp target/harba_le-sciellour-1.0.jar fousfous.Solo

# Lancer nos joueurs
java -cp target/harba_le-sciellour-1.0.jar fousfous.ClientJeu fousfous.players.JoueurAleatoire
java -cp target/harba_le-sciellour-1.0.jar fousfous.ClientJeu fousfous.players.JoueurMiniMax
java -cp target/harba_le-sciellour-1.0.jar fousfous.ClientJeu fousfous.players.JoueurIA
```