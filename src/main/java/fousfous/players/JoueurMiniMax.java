package fousfous.players;

import fousfous.IJoueur;
import fousfous.algorithm.Algorithm;
import fousfous.algorithm.AlphaBeta;
import fousfous.algorithm.MiniMax;
import fousfous.heuristique.Heuristique;
import fousfous.heuristique.HeuristiqueSimple;
import fr.upsud.PlateauFousFous;
import fr.upsud.Player;

import java.util.Random;


public class JoueurMiniMax implements IJoueur {

  private final static int TOTAL_CASES = 32;

  private PlateauFousFous mPlateau;
  private Heuristique mHeuristique;
  private int mMyColour;
  private String mMyFullRepresentation;
  private String mAdvFullRepresentation;

  private Algorithm mMiniMax;
  private Algorithm mAlphaBeta;


  public void initJoueur(int mycolour) {
    this.mPlateau = new PlateauFousFous();
    this.mHeuristique = new HeuristiqueSimple();
    this.mMiniMax = new MiniMax();
    this.mAlphaBeta = new AlphaBeta();
    this.mMyColour = mycolour;
    this.mMyFullRepresentation = Player.FULL_REPRESENTATION[this.mMyColour == 1 ? Player.NOIR : Player.BLANC];
    this.mAdvFullRepresentation = Player.FULL_REPRESENTATION[this.mMyColour == 1 ? Player.BLANC : Player.NOIR];
  
    this.mMiniMax.initPlayers(this.mMyFullRepresentation, this.mAdvFullRepresentation);
    this.mMiniMax.setHeuristique(this.mHeuristique);
    
    this.mAlphaBeta.initPlayers(this.mMyFullRepresentation, this.mAdvFullRepresentation);
    this.mAlphaBeta.setHeuristique(this.mHeuristique);
  }


  public int getNumJoueur() {
    return this.mMyColour;
  }


  private PlateauFousFous copyPlateau(PlateauFousFous plateau) {
    return new PlateauFousFous(plateau.toString().split("\n"));
  }

  public String choixMouvement() {
    int[][] mesCases = this.mPlateau.getAllCasesOf(this.mMyFullRepresentation);
    int[][] sesCases = this.mPlateau.getAllCasesOf(this.mAdvFullRepresentation);
    int nbCases = mesCases.length + sesCases.length;
    int profondeur = TOTAL_CASES / nbCases;


    String move = this.mMiniMax.findTheBestMove(copyPlateau(this.mPlateau), profondeur);
    
    if (move != null) {
      this.mPlateau.play(move, this.mMyFullRepresentation);

      return move;
    }

    // Le joueur passe
    return "PASSE";
  }


  public void declareLeVainqueur(int colour) {
    if (colour == this.mMyColour) {
      System.out.println("Winner!!");
    } else {
      System.out.println("Looser :(");
    }
  }


  public void mouvementEnnemi(String coup) {
    this.mPlateau.play(coup, this.mAdvFullRepresentation);
  }


  public String binoName() {
    return "Grégoire Harba - Thomas Le Sciellour";
  }

}