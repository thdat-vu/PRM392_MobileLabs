package com.example.lab3_3;

public class Player {
    private String Name;
    private String Team;
    private String Nationality;
    private String ImageUri;

    public Player(String name, String team, String nationality, String ImageUri) {
        this.Name = name;
        this.Team = team;
        this.Nationality = nationality;
        this.ImageUri = ImageUri;
    }

    public String getName() {
        return Name;
    }

    public String getTeam() {
        return Team;
    }

    public String getNationality() {
        return Nationality;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setName(String name) {
        Name = name;
    }
    public void setTeam(String team) {
        Team = team;
    }
    public void setNationality(String nationality) {
        Nationality = nationality;
    }
    public void setImageUri (String imageUri) {
        ImageUri = imageUri;
    }
}
