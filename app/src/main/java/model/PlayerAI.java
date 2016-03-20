package model;

import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by Amaury on 11/03/2016.
 */
public class PlayerAI extends Player
{
    private int _depth;

    public PlayerAI(Othello othello,int number, int maxDepth)
    {
        super(othello, number);
        _depth = maxDepth;
    }

    public void play()
    {
        int bestValue = Integer.MIN_VALUE;
        Move bestMove = new Move(0, 0);
        Board boardSave = new Board(_othello.getBoardSize());
        Othello othello = new Othello(_othello);
        List<Move> listMoves = _othello.getListMoves(this);
        Random rand = new Random();

        for (Move move : listMoves)
        {
            boardSave.copy(othello.getBoard());
            othello.playAt(this, move.getX(), move.getY());
            int moveValue = alphaBeta(othello, Integer.MIN_VALUE, Integer.MAX_VALUE, _depth, _othello.getOpponent(this));
            othello.setBoard(boardSave);
            if (moveValue >= bestValue)
            {
                if(rand.nextInt(100)>50)
                {
                    bestValue = moveValue;
                    bestMove = move;
                }
            }
        }
        Log.e("play", "best value = " + String.valueOf(bestValue) + " et best move = " + bestMove);
        _othello.playAt(this, bestMove.getX(), bestMove.getY());
    }

    public Move stupid(List<Move> listMoves)
    {
        Random rand = new Random();

        return listMoves.get(rand.nextInt(listMoves.size()));
    }

    public int alphaBeta(Othello othello, int alpha, int beta, int maxDepth, Player player)
    {
        Board boardSave = new Board(othello.getBoardSize());
        int val;

        if (maxDepth == 0 || othello.gameOver())
        {
            //Log.e("alpha-beta", "Profondeur = " + maxDepht);
            return othello.evaluateBoard(this);
        }

        if (player == this)
        {
            val = Integer.MIN_VALUE;

            List<Move> listMoves = othello.getListMoves(player);
            for (Move move : listMoves)
            {
                //Log.e("alpha-beta", "good");
                boardSave.copy(othello.getBoard());
                othello.playAt(player, move.getX(), move.getY());
                int eval = alphaBeta(othello, alpha, beta, maxDepth - 1, othello.getOpponent(player));
                val = (val < eval)?eval:val;
                alpha = (alpha < val)?val:alpha;
                othello.setBoard(boardSave);

                if (beta <= alpha)
                {
                    break;
                }
            }
        }
        else
        {
            val = Integer.MAX_VALUE;

            List<Move> listMoves = othello.getListMoves(player);
            for (Move move : listMoves)
            {
                boardSave.copy(othello.getBoard());
                othello.playAt(player, move.getX(), move.getY());
                int eval = alphaBeta(othello, alpha, beta, maxDepth - 1, othello.getOpponent(player));
                val = (val > eval)?eval:val;
                beta = (beta > val)?val:beta;
                othello.setBoard(boardSave);

                if (beta <= alpha)
                {
                    break;
                }
            }
        }

        return val;
    }

    public void playAt(int x, int y)
    {
        // Do Nothing
    }

    @Override
    public boolean isAI() {
        return true;
    }
}

// TODO: 11/03/2016 Faire l'IA.
