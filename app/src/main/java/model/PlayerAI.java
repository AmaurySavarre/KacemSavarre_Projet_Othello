package model;

import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * Created by Amaury on 11/03/2016.
 */
public class PlayerAI extends Player
{
    public PlayerAI(Othello othello,int number)
    {
        super(othello, number);
    }

    public void play()
    {
        /*while (!_hasPlayed)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.e("play()", e.toString());
            }
        }

        _hasPlayed = false;*/

        /*List<Move> listMoves = _othello.getListMoves(this);

        Move move = stupid(listMoves);
        _othello.playAt(this, move.getX(), move.getY());*/

        MoveValue bestMove = alphaBeta(new Othello(_othello), Integer.MIN_VALUE, Integer.MAX_VALUE, 6, this);
        Log.e("play", "best value = " + String.valueOf(bestMove.value) + " et best move = " + bestMove.move);
        _othello.playAt(this, bestMove.move.getX(), bestMove.move.getY());
    }

    public Move stupid(List<Move> listMoves)
    {
        Random rand = new Random();

        return listMoves.get(rand.nextInt(listMoves.size()));
    }

    public class MoveValue
    {
        public Move move;
        public int value;

        public MoveValue()
        {
            move = null;
            value = 0;
        }

        public MoveValue(Move move, int value)
        {
            this.move = move;
            this.value = value;
        }
    }

    public MoveValue alphaBeta(Othello othello, int alpha, int beta, int maxDepth, Player player)
    {
        Board boardSave;
        MoveValue bestMove = new MoveValue();

        if (maxDepth == 0 || othello.gameOver())
        {
            //Log.e("alpha-beta", "Profondeur = " + maxDepht);
            bestMove.value =  othello.evaluateBoard(this);
            return bestMove;
        }

        if (player == this)
        {
            bestMove.value = Integer.MIN_VALUE;

            List<Move> listMoves = othello.getListMoves(player);
            for (Move move : listMoves)
            {
                Log.e("alpha-beta", "good");
                boardSave = othello.getBoard();
                othello.playAt(player, move.getX(), move.getY());
                MoveValue eval = alphaBeta(othello, alpha, beta, maxDepth - 1, othello.getOpponent(player));
                eval.move = move;
                bestMove = (bestMove.value < eval.value)?eval:bestMove;
                alpha = (alpha < bestMove.value)?bestMove.value:alpha;
                othello.setBoard(boardSave);

                if (beta <= alpha)
                {
                    bestMove.value = beta;
                    break;
                }
            }
        }
        else
        {
            bestMove.value = Integer.MAX_VALUE;

            List<Move> listMoves = othello.getListMoves(player);
            for (Move move : listMoves)
            {
                boardSave = othello.getBoard();
                othello.playAt(player, move.getX(), move.getY());
                MoveValue eval = alphaBeta(othello, alpha, beta, maxDepth - 1, othello.getOpponent(player));
                eval.move = move;
                bestMove = (bestMove.value > eval.value)?eval:bestMove;
                beta = (beta > bestMove.value)?bestMove.value:beta;
                othello.setBoard(boardSave);

                if (beta <= alpha)
                {
                    bestMove.value = alpha;
                    break;
                }
            }
        }

        return bestMove;
    }

    public void playAt(int x, int y)
    {
        /*if(_othello.isPlayable(this, x, y))
        {
            _othello.playAt(this, x, y);
            _hasPlayed = true;
        }*/
    }
}

// TODO: 11/03/2016 Faire l'IA.
