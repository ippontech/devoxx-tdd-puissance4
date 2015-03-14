package fr.ippon.contest.puissance4;

import com.google.common.base.Preconditions;

public class Puissance4Impl implements Puissance4 {
    
    private static final long NEW_GAME = 0L;
    private static final long GAME_OVER = 0b111111_111111_111111_111111_111111_111111_111111L;
    private static final long COL_MASK = 0b111111L;
    
    private static final long[] DIRECTIONS = new long[] {1,6,5,7};
    private static final long[] DIRECTION_MASK = new long[] {
        0b111110_111110_111110_111110_111110_111110_111110L,
        0b111111_111111_111111_111111_111111_111111_000000L,
        0b011111_011111_011111_011111_011111_011111_011111L,
        0b111110_111110_111110_111110_111110_111110_111110L
    };

    private static final char EMPTY = '-';
    private static final char YELLOW = 'J';
    private static final char RED = 'R';

    private long red = NEW_GAME;
    private long yellow = NEW_GAME;
    private long board = NEW_GAME;
    private char who = EMPTY;
    
    @Override
    public void nouveauJeu() {
        this.red = NEW_GAME;
        this.yellow = NEW_GAME;
        this.board = NEW_GAME;
        this.who = RED;
    }

    @Override
    public void chargerJeu(char[][] grille, char tour) {
        Preconditions.checkArgument(tour == RED || tour == YELLOW);
        Preconditions.checkArgument(grille.length == 6);
        nouveauJeu();
        for (int row = 5; row >= 0; row--) {
            Preconditions.checkArgument(grille[row].length == 7);
            for (int col = 0; col < 7; col++) {
                if (grille[row][col] != EMPTY) {
                    this.who = grille[row][col];
                    jouer(col);
                }
            }
        }
        this.who = tour;
    }

    @Override
    public EtatJeu getEtatJeu() {
        if (hasWon(this.red)) {
            return EtatJeu.ROUGE_GAGNE;
        }
        if (hasWon(this.yellow)) {
            return EtatJeu.JAUNE_GAGNE;
        }
        return this.board == GAME_OVER ? EtatJeu.MATCH_NUL : EtatJeu.EN_COURS;
    }

    @Override
    public char getTour() {
        return this.who;
    }

    @Override
    public char getOccupant(int ligne, int colonne) {
        Preconditions.checkArgument(ligne >= 0);
        Preconditions.checkArgument(colonne >= 0);
        Preconditions.checkArgument(ligne <= 5);
        Preconditions.checkArgument(colonne <= 6);
        long move = (1L << (6 * colonne)) << (5 - ligne);
        if ((this.board & move) != 0) {
            if ((this.yellow & move) != 0) {
                return YELLOW;
            }
            return RED;
        }
        return EMPTY;
    }

    @Override
    public void jouer(int colonne) {
        Preconditions.checkArgument(colonne >= 0);
        Preconditions.checkArgument(colonne <= 6);
        int offset = 6 * colonne;
        Preconditions.checkState((this.board & (1L << (offset + 5))) == 0);
        long move = (((this.board << 1) & DIRECTION_MASK[0]) & (COL_MASK << offset) 
                | (1L << offset)) ^ (this.board  & (COL_MASK << offset));
        this.board |= move;
        switch (this.who) {
        case RED:
            this.red |= move;
            this.who = YELLOW;
            break;
        case YELLOW:
            this.yellow |= move;
            this.who = RED;
            break;
        }
    }
    
    private boolean hasWon(final long bitboard) {
        for (int dir = 0; dir < 4; dir++) {
            long val = bitboard;
            for (int n = 0; n < 3; n++) {
                val &= ((val << DIRECTIONS[dir]) & DIRECTION_MASK[dir]);
                if (val == 0) {
                    break;
                }
                if (n == 2) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
