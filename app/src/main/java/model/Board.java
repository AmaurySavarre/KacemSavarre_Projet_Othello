package model;

import android.util.Log;

/**
 * Created by Amaury Savarre on 2/23/2016.
 *
 * Class to create and manage a Board.
 */
public class Board
{
    private Case[][] _board;    // Matrix containing all the cases of the board.
    private int _size;          // Integer representing the size of the board.

    /**
     * Board Constructor.
     *
     * @param size The size of the board.
     */
    public Board(int size)
    {
        _size = size;
        _board = new Case[_size][_size];

        // Initializing the matrix of cases.
        for(int i = 0 ; i < _size ; ++i)
        {
            for(int j = 0 ; j < _size ; ++j)
            {
                _board[i][j] = new Case();
            }
        }
    }

    /**
     * Board empty Constructor.
     */
    private Board()
    {

    }

    /**
     * Board copy Constructor.
     *
     * @param board The board to copy.
     */
    public Board(Board board)
    {
        _size = board._size;
        _board = new Case[_size][_size];

        // Initializing the matrix of cases.
        for(int x = 0 ; x < _size ; ++x)
        {
            for(int y = 0 ; y < _size ; ++y)
            {
                _board[y][x] = new Case(board._board[x][y]);
            }
        }
    }

    /**
     * Returns the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize()
    {
        return _size;
    }

    /**
     * Initializes the board to its initial state.
     *
     * @param player1 Player 1
     * @param player2 Player 2
     */
    public void initializeCases(Player player1, Player player2)
    {
        // Placing the 4 first disks.
        changeXY(player1, (_size/2 - 1), (_size/2 - 1));
        changeXY(player1, (_size/2), (_size/2));
        changeXY(player2, (_size/2), (_size/2 - 1));
        changeXY(player2, (_size/2 - 1), (_size/2));
    }

    /**
     * Changes the state of the case at the specified coordinates.
     *
     * @param player The player that change the state of the case.
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     */
    public void changeXY(Player player, int X, int Y)
    {
        if(X >= 0 && X < _size && Y >= 0 && Y < _size)
        {
            _board[Y][X].changeState(player);
        }
    }

    /**
     * Returns the case at the specified coordinates.
     *
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return The case at the specified coordinates.
     */
    public Case getXY(int X, int Y)
    {
        if(X >= 0 && X < _size && Y >= 0 && Y < _size)
        {
            return _board[Y][X];
        }

        return null;
    }

    /**
     * Checks if the case is empty.
     *
     * @param X The X coordinate on the board.
     * @param Y The Y coordinate on the board.
     * @return A boolean saying if the case is empty (true) or not (false).
     */
    public boolean caseEmpty(int X, int Y)
    {
        return _board[Y][X].isEmpty();
    }

    /**
     * Copies the given board to this board.
     *
     * @param board The board to copy.
     */
    public void copy(Board board)
    {
        // If the board are of the same size.
        if(this._size == board.getSize())
        {
            // Scan the board.
            for(int x = 0 ; x < _size ; ++x)
            {
                for(int y = 0 ; y < _size ; ++y)
                {
                    // Copy the state of the cases.
                    _board[y][x].copy(board.getXY(x, y));
                }
            }
        }
        else
        {
            Log.e("copy", "Not the same size");
        }
    }

    public String toString()
    {
        String res = "";

        for(int i = 0 ; i < _size ; ++i)
        {
            for(int j = 0 ; j < _size ; ++j)
            {
                res += _board[i][j];
            }
        }

        return res;
    }

    /**
     * Create a board from a string.
     *
     * @param data A string containing the data to create the board.
     * @param size The size of the board.
     * @return A new board created with the data.
     */
    public static Board fromString(String data, int size)
    {
        Board board = new Board();

        board._size = size;
        board._board = new Case[size][size];

        // Read the data.
        for (int i = 0 ; i < data.length() ; ++i)
        {
            int x = i%size;
            int y = i/size;

            // Create a case with the corresponding state.
            int val = Integer.parseInt(data.substring(i, i + 1));
            State state = State.getState(val);
            board._board[y][x] = new Case(state);
        }

        return board;
    }
}
