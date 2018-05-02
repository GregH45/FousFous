package fousfous.algorithm;

import fousfous.heuristique.Heuristique;
import fr.upsud.PlateauFousFous;


public interface Algorithm {

  /**
   *  Initialise les joueurs
   *  @param {String} Joueur pour lequel l'algorithme joue
   *  @param {String} Joueur contre lequel l'algorithme joue
  **/
  public void initPlayers(String myPlayer, String EnnemiPlayer);

  /**
   *  Enregistrement de l'heuristique de jeu
   *  @param {Heuristique} Heuristique de jeu
  **/
  public void setHeuristique(Heuristique heuristique);

  /**
   *  Retourne le meilleur coup possible
   *  @param {PlateauFousFous} Plateau de jeu
   *  @return {String} Meilleur coup trouvé
  **/
  public String findTheBestMove(PlateauFousFous plateau);
  public String findTheBestMove(PlateauFousFous plateau, int depth);

}