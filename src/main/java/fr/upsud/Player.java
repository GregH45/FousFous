package fr.upsud;


public class Player
{

  /**
   *  Constantes
  **/
  // Représentation entière des joueurs
  public final static String[] FULL_REPRESENTATION = { null, "blanc", "noir" };

  // Représentation courte des joueurs
  public final static char[] REPRESENTATION = { '-', 'b', 'n' };

  // Identifiant Joueur Vide
  public final static int VIDE = 0;

  // Identifiant Blanc
  public final static int BLANC = 1;

  // Identifiant Joueur Noir
  public final static int NOIR = 2;



  /**
   *  Variables d'exécution
  **/
  private int mPlayerType = BLANC;



  /**
   *  Récupération de l'identifiant du joueur
   *  @param {char} Caractère de représentation du joueur
   *  @return {int} Identifiant du joueur passé en paramètre
  **/
  public static int index(char c)
  {
    for (int i=0; i<REPRESENTATION.length; i++) {
      if (c == REPRESENTATION[i]) {
        return i;
      }
    }

    return -1;
  }


  /**
   *  Récupération de l'identifiant du joueur
   *  @param {char} Caractère de représentation du joueur
   *  @return {int} Identifiant du joueur passé en paramètre
  **/
  public static int index(String s)
  {
    for (int i=0; i<FULL_REPRESENTATION.length; i++) {
      if (FULL_REPRESENTATION[i] != null && FULL_REPRESENTATION[i].equals(s)) {
        return i;
      }
    }

    return -1;
  }


  /**
   *  Constructeur du joueur par défaut
  **/
  public Player() {

  }


  /**
   *  Switch de joueur
  **/
  public void switchPlayer() {
    this.mPlayerType = this.mPlayerType == BLANC ? NOIR : BLANC;
  }

  /**
   *  Représentation du joueur courant
  **/
  public String getFullRepresentation() {
    return FULL_REPRESENTATION[this.mPlayerType];
  }

}