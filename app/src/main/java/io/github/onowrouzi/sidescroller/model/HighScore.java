package io.github.onowrouzi.sidescroller.model;

public class HighScore {
    private String name;
    private int score;

    public HighScore(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public Integer getScore()
    {
        return score;
    }
}
