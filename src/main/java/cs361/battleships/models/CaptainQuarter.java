package cs361.battleships.models;

public class CaptainQuarter extends Square {
    public CaptainQuarter(int row, char column){
        super(row, column);
        this.timesHit = 0;
        this.maxHits = 2;
        this.type = "CQ";
    }

    public CaptainQuarter(CaptainQuarter old){
        super(old.getRow(), old.getColumn());
        this.timesHit = 0;
        this.maxHits = 2;
        this.type = "CQ";
    }
}