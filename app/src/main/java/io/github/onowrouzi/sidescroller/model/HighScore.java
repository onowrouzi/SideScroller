package io.github.onowrouzi.sidescroller.model;

public class HighScore {
    private String name;
    private int score;

    public HighScore(String name, int score)
    {
        if(name.length() > 25)
            this.name = name.substring(0,24);
        else
            this.name = name;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public int getScore()
    {
        return score;
    }

    public String toString()
    {
        // Built this algorithm because String.format() does not work with ListView Object.
            // Can't find anything so this stitch should work for now
        int temp = 5;
        int additional = 0; //every 3 spaces needs an extra space attached in order to match up (something with font-size)
        if(name.length() < 25) //25 max username size
        {
            additional = (25 - name.length()) / 3;
            temp = 5 + additional + (25 - name.length());
        }
        String padding = "%-25s %" + temp + "d";

        return String.format(padding, name, score);
    }
}
