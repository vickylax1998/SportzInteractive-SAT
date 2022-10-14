package com.adventbiztech.pojo;

public class Player {
    String sr_no;
    String p_name;
    boolean is_caption;
    boolean is_wicket_keeper;
    String batting_style;
    String bowling_style;
    String team_no;

    public Player(String sr_no, String p_name, boolean is_caption, boolean is_wicket_keeper, String batting_style, String bowling_style, String team_no) {
        this.sr_no = sr_no;
        this.p_name = p_name;
        this.is_caption = is_caption;
        this.is_wicket_keeper = is_wicket_keeper;
        this.batting_style = batting_style;
        this.bowling_style = bowling_style;
        this.team_no = team_no;
    }

    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public boolean isIs_caption() {
        return is_caption;
    }

    public void setIs_caption(boolean is_caption) {
        this.is_caption = is_caption;
    }

    public boolean isIs_wicket_keeper() {
        return is_wicket_keeper;
    }

    public void setIs_wicket_keeper(boolean is_wicket_keeper) {
        this.is_wicket_keeper = is_wicket_keeper;
    }

    public String getBatting_style() {
        return batting_style;
    }

    public void setBatting_style(String batting_style) {
        this.batting_style = batting_style;
    }

    public String getBowling_style() {
        return bowling_style;
    }

    public void setBowling_style(String bowling_style) {
        this.bowling_style = bowling_style;
    }

    public String getTeam_no() {
        return team_no;
    }

    public void setTeam_no(String team_no) {
        this.team_no = team_no;
    }
}
