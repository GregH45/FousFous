package fr.upsud;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.util.Arrays;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Random;
import java.util.Scanner;


public class PlateauFousFous implements Partie1
{

  /**
   *  Constantes
  **/
  public final static int DEFAUT_TAILLE_PLATEAU = 8;


  /**
   *  Variables d'exécution
  **/
  private int[][] mPlateau;
  private int mNbCoups;


  /**
   *  Constructeur du plateau par défaut
  **/
  public PlateauFousFous() {
    this(DEFAUT_TAILLE_PLATEAU);
  }

  /**
   *  Constructeur de plateau
   *  @param {int} Nombre de colonnes du plateau
  **/
  public PlateauFousFous(int taillePlateau) {
    this.mPlateau = new int[taillePlateau][taillePlateau];
    this.mNbCoups = 0;

    for (int i=0; i<this.mPlateau.length; i++) {
      for (int k=0; k<this.mPlateau[i].length; k++) {
        if (i%2 == 0 && k%2 == 1) {
          this.mPlateau[i][k] = Player.BLANC;
        }
        else if (i%2 == 1 && k%2 == 0) {
          this.mPlateau[i][k] = Player.NOIR;
        }
        else {
          this.mPlateau[i][k] = Player.VIDE;
        }
      }
    }
  }

  /**
   *  Constructeur du plateau à partir d'une chaine de caractère
   *  @param {String[]} Chaine de caractère représentant le plateau
  **/
  public PlateauFousFous(String[] plateau) {
    this.mNbCoups = 0;
    setFromArrayString(plateau);
  }



  /**
   *  Récupération de la chaine de caractère des colonnes du plateau
   *  @return {String} Lettres représentants les colonnes du plateau
  **/
  private String getColumnDefinitionString() {
    String line = "% ";

    for (int i=0; i<this.mPlateau.length; i++) {
      line += (char)(65 + i);
    }

    return line;
  }

  /**
   *  Récupération du plateau courant
   *  @return {String} Chaine de caractères représentant le plateau
  **/
  public String toString() {
    String firstLine = "";
    String jeu = "";

    if (this.mPlateau != null) {
      jeu += getColumnDefinitionString() + "\n";

      for (int i=0; i<this.mPlateau.length; i++) {
        String line = (i+1) + " ";

        for (int k=0; k<this.mPlateau[i].length; k++) {
          line += Player.REPRESENTATION[this.mPlateau[i][k]];
        }

        line += " " + (i+1) + "\n";
        jeu += line;
      }

      jeu += getColumnDefinitionString();
    } else {
      jeu = "Pas de plateau";
    }

    return jeu;
  }



  public int getNbCoups() {
    return this.mNbCoups;
  }


  public static PlateauFousFous copy(PlateauFousFous plateau) {
    return new PlateauFousFous(plateau.toString().split("\n"));
  }






  /**
   *  Charger le plateau à partir d'un tableau de chaine de caractères
   *  @param {String[]} Lignes du plateau avec la représentation courte des joueurs
  **/
  private void setFromArrayString(String[] plateau) {
    int cptX = 0, cptY, PlayerIndex;
    char c;

    this.mPlateau = new int[plateau.length][plateau.length];

    for (int i=0; i<plateau.length; i++) {
      c = plateau[i].charAt(0);

      if (c >= '1' && c <= '9') {
        cptY = 0;

        for (int k=2; k<plateau[i].length(); k++) {
          c = plateau[i].charAt(k);

          if ((PlayerIndex = Player.index(c)) >= 0) {
            this.mPlateau[cptX][cptY] = PlayerIndex;
            cptY += 1;
          }
          else if (c >= '1' && c <= '9') {
            break;
          }
        }

        cptX += 1;
      }
    }
  }

  /**
   *  Interface Implémentation
  **/
  public void setFromFile(String fileName) {
    File file = new File(fileName);

    try
    {
      FileReader reader = new FileReader(file);
      BufferedReader buffer = new BufferedReader(reader);
      ArrayList<String> lines = new ArrayList();
      String line;

      while ((line = buffer.readLine()) != null) {
        if (line.charAt(0) != '%') {
          lines.add(line);
        }
      }

      setFromArrayString(lines.toArray(new String[lines.size()]));

    } catch (Exception e) {
      System.out.println("Erreur de lecture du plateau");
      this.mPlateau = null;
      System.out.println(e);
    }
  }
  

  /**
   *  Interface Implémentation
  **/
  public void saveToFile(String fileName) {
    File file = new File(fileName);

    try {
      FileWriter writer = new FileWriter(file);
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      Date date = new Date();

      writer.write("% Plateau du " + dateFormat.format(date) + "\n");
      writer.write(toString());
      writer.close();
    } catch (Exception e) {
      System.out.println("Erreur d'enregistrement du plateau");
    }
  }
  


  /**
   *  Parsing d'un mouvement
   *  @param {String} Mouvement d'un joueur (ex: B1-C2)
   *  @return {int[][]} Tableau représentant les cases du plateau (eq: [MOUV][X|Y])
  **/
  private int[][] parseMove(String move) {
    try {
      String[] moves = move.split("-");

      if (moves.length == 2) {
        int[][] result = new int[moves.length][2];
    
        for (int i=0; i<result.length; i++) {
          int x = ((int) moves[i].charAt(1)) - 49;
          int y = ((int) moves[i].charAt(0)) - 65;

          //System.out.println("X: " + x + ", Y: " + y);

          if (x >= 0 && x <= this.mPlateau.length && y >= 0 && y <= this.mPlateau.length) {
            result[i][0] = x;
            result[i][1] = y;
          } else {
            return null;
          }
        }

        return result;
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  /**
   *  Vérification du chemin entre deux cases
   *  @param {int[][]} Mouvement parsé à vérifier
   *  @return {boolean} Vrai si le chemin est empruntable sinon faux
  **/
  private boolean validWay(int[][] moves) {
    // Equation de la forme : y = ax + b
    int div = moves[1][1] - moves[0][1];

    if (div == 0) {
      return false;
    }

    int a = (moves[1][0] - moves[0][0]) / div;
    int b = moves[0][0] - (a * moves[0][1]);
    int xMin = moves[0][1] > moves[1][1] ? moves[1][1] : moves[0][1];
    int xMax = moves[0][1] < moves[1][1] ? moves[1][1] : moves[0][1];

    if (a == 1 || a == -1) {
      for (int i=1; i<(xMax-xMin); i++) {
        int x = xMin+i;
        int y = a * x + b;

        if (y < 0 || y >= this.mPlateau.length || this.mPlateau[y][x] != Player.VIDE) {
          return false;
        }
      }
    } else {
      return false;
    }


    return true;
  }

  /**
   *  Interface Implémentation
  **/
  public boolean estValide(String move, String player) {
    // Récupération du mouvement en représentation de jeu
    int[][] moves = parseMove(move);

    // Le mouvement existe
    if (moves != null) {
      int playerIndex = Player.index(player);
      int initialPlayer = this.mPlateau[moves[0][0]][moves[0][1]];
      int endPlayer = this.mPlateau[moves[1][0]][moves[1][1]];

      // Player de départ valide
      if (initialPlayer == playerIndex) {

        // Chemin valide
        if (validWay(moves)) {

          // Player d'arrivée valide
          if (endPlayer != playerIndex) {
            return true;
          }
        }
      }
    }

    return false;
  }
  





  /**
   *  Récupération de toutes les cases d'un joueur
   *  @param {String} Représentation longue du joueur cherché
   *  @return {int[][]} Tableau contenant les cases possibles (eq: [CASE][X|Y])
  **/
  public int[][] getAllCasesOf(String player) {
    ArrayList<int[]> moves = new ArrayList();
    int playerIndex = Player.index(player);
    
    for (int i=0; i<this.mPlateau.length; i++) {
      for (int k=0; k<this.mPlateau[i].length; k++) {
        if (this.mPlateau[i][k] == playerIndex) {
          int[] move = {k, i};
          moves.add(move);
        }
      }
    }

    if (moves.size() > 0) {
      return moves.toArray(new int[moves.size()][2]);
    }

    return null;
  }

  /**
   *  Transformation d'une case en chaine de caractère
   *  @param {int[]} Case à transformée
   *  @return {String} Chaine de caractère représentant la case
  **/
  private String stringifyCase(int[] cas) {
    return (char)((cas[0]%this.mPlateau.length)+65) + "" + ((cas[1]%this.mPlateau.length)+1);
  }

  /**
   *  Récupération de l'application d'un décalage à droite de colonne
   *  @param {int[]} Case de départ
   *  @param {int} Nombre de décalage à droite
   *  @return {int[][]} Cases après décalage
  **/
  private int[][] getNeighboorCase(int[] cas, int offset) {
    // Récupération des voisins
    int[] neighbourTop = new int[2];
    int[] neighbourBottom = new int[2];

    if (cas[0]+1 >= this.mPlateau.length || cas[1]+1 >= this.mPlateau.length) {
      neighbourTop[0] = cas[0] - 1;
      neighbourTop[1] = cas[1] - 1;
    } else {
      neighbourTop[0] = cas[0] + 1;
      neighbourTop[1] = cas[1] + 1;
    }

    if (cas[0]+1 >= this.mPlateau.length || cas[1]-1 < 0) {
      neighbourBottom[0] = cas[0] - 1;
      neighbourBottom[1] = cas[1] + 1;
    } else {
      neighbourBottom[0] = cas[0] + 1;
      neighbourBottom[1] = cas[1] - 1;
    }

    // Calcul des droite
    int[] a = new int[2];
    int[] b = new int[2];

    a[0] = (neighbourTop[1] - cas[1]) / (neighbourTop[0] - cas[0]);
    a[1] = (neighbourBottom[1] - cas[1]) / (neighbourBottom[0] - cas[0]);

    b[0] = cas[1] - a[0] * cas[0];
    b[1] = cas[1] - a[1] * cas[0];

    // Calcul du resultat
    int[][] result = new int[2][2];

    // Premier point
    result[0][0] = offset%this.mPlateau.length;
    result[0][1] = a[0] * (offset%this.mPlateau.length) + b[0];

    if (result[0][1] < 0 || result[0][1] >= this.mPlateau.length) {
      result[0] = null;
    }

    // Deuxième point 
    result[1][0] = (cas[0]+offset)%this.mPlateau.length;
    result[1][1] = a[1] * (offset%this.mPlateau.length) + b[1];

    if (result[1][1] < 0 || result[1][1] >= this.mPlateau.length) {
      result[1] = null;
    }

    return result;
  }


  /**
   *  Interface Implémentation
  **/
  public String[] mouvementsPossibles(String player) {
    ArrayList<String> results = new ArrayList();
    int[][] PlayersPlayer = getAllCasesOf(player); // Cases possibles du joeur
    int[][] PlayersAdv = getAllCasesOf(player.equals("blanc") ? "noir" : "blanc"); // Cases possibles de l'adversaire

    // Parcours de chaques cases du joueurs
    if(PlayersAdv != null && PlayersPlayer != null)
    {
	    for (int itPlayer=0; itPlayer<PlayersPlayer.length; itPlayer++) {
	      boolean findDirectMove = false;
	
	      // Recherche Mouvement Direct - Parcours des cases possibles de l'adversaire
	      for (int itAdv=0; itAdv<PlayersAdv.length; itAdv++) {
	        // Création de la chaine de caractère représentant le mouvement
	        String move = stringifyCase(PlayersPlayer[itPlayer]) + "-" + stringifyCase(PlayersAdv[itAdv]);
	
	        // Vérification du mouvement
	        if (estValide(move, player)) {
	          findDirectMove = true;
	          results.add(move);
	        }
	      }
	
	      // Recherche Mouvement Indirect (s'il n'y a pas déjà de mouvement directe possible à partir de cette case)
	      if (!findDirectMove) {
	        for (int itCase=0; itCase<this.mPlateau.length; itCase++) {
	          int[][] newCases = getNeighboorCase(PlayersPlayer[itPlayer], itCase);
	
	          if (newCases != null) {
	            for (int itAdv=0; itAdv<PlayersAdv.length; itAdv++) {
                if (newCases[0] != null) {
	                String move_1 = stringifyCase(newCases[0]) + "-" + stringifyCase(PlayersAdv[itAdv]);

                  if (estValide(move_1, player)) {
                    results.add(move_1);
                  }
                }
                if (newCases[1] != null) {
                  String move_2 = stringifyCase(newCases[1]) + "-" + stringifyCase(PlayersAdv[itAdv]);

  	              if (estValide(move_2, player)) {
  	                results.add(move_2);
  	              }
                }
	            }
	          }
	        }
	      }
	    }
    }

    if (results.size() > 0) {
      return results.toArray(new String[results.size()]);
    }

    return null;
  }
  



  /**
   *  Interface Implémentation
  **/
  public void play(String move, String player) {
    // Le mouvement est valide
    if (estValide(move, player)) {
      int[][] moves = parseMove(move);
      int playerIndex = Player.index(player);

      if (moves != null) {
        // Changement des cases du plateau
        this.mPlateau[moves[0][0]][moves[0][1]] = Player.VIDE;
        this.mPlateau[moves[1][0]][moves[1][1]] = playerIndex;
        this.mNbCoups++;
      }
    }
  }
  


  /**
   *  Interface Implémentation
  **/
  public boolean finDePartie() {
    // Il n'existe plus de mouvements possibles
    if (mouvementsPossibles(Player.FULL_REPRESENTATION[1]) == null && mouvementsPossibles(Player.FULL_REPRESENTATION[2]) == null) {
      return true;
    }

    return false;
  }




  /**
   *  Test des méthodes implémentées
  **/  
  public static void main(String[] args)
  {
    String fileNameOutput = "test_plateau";
    String fileNameInput = null;

    if (args.length > 0) {
      fileNameOutput = args[0];

      if (args.length > 1) {
        fileNameInput = args[1];
      }
    }

    // Construction du plateau par défaut
    PlateauFousFous plateau = new PlateauFousFous();

    if (fileNameInput != null) {
      plateau.setFromFile("files/" + fileNameInput);
    }

    System.out.println("Plateau de jeu");  
    System.out.println(plateau.toString());  


    // Modification du plateau
    Player player = new Player();
    Random randomGen = new Random();
    Scanner scanner = new Scanner(System.in);

    while (!plateau.finDePartie()) {
      String playerStr = player.getFullRepresentation();
      boolean played = false;

      System.out.println("");
      System.out.println("Coup n°" + plateau.mNbCoups);
      System.out.println("Entrez un mouvement (ex: B1-D3) pour le joueur \"" + playerStr + "\" ou appuyez sur entrez pour jouer automatiquement : ");
      
      String move = scanner.nextLine();

      // Quitter le jeu
      if ("q".equals(move)) {
        break;
      }
      // Le joueur n'a rien entré (jeu aléatoire)
      else if (move.length() != 5) {
        String[] moves = plateau.mouvementsPossibles(playerStr);

        System.out.println(Arrays.toString(moves));

        if (moves != null) {
          int rand = randomGen.nextInt(moves.length);

          played = true;
          plateau.play(moves[rand], playerStr);
        }
      }
      // Vérification du coup du joueur
      else if (plateau.estValide(move, playerStr)) {
        played = true;
        plateau.play(move, playerStr);
      }

      // Coup joué -> Coup suivant
      if (played) {
        System.out.println("--------------");
        System.out.println(plateau.toString());
        player.switchPlayer();
      }
      // Coup non joué
      else {
        System.out.println("--------------");
        System.out.println("Le coup n'est pas valide");
      }
    }

    scanner.close();


    // Enregistrement d'un plateau dans un fichier
    plateau.saveToFile("files/" + fileNameOutput);

    System.out.println("");  
    System.out.println("Enregistrement du plateau dans le fichier " + fileNameOutput);  
  }

}
