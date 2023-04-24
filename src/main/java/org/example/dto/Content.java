package org.example.dto;

public class Content {
    public int id;
    public String type;
    public String content_name;
    public String location;
    public String time;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;
    public boolean sunday;
    public boolean select;

    public Content(int id, String type, String content_name, String location, String time) {
        this.id = id;
        this.type = type;
        this.content_name = content_name;
        this.location = location;
        this.time = time;
    }

    public Content(int id, String type, String content_name) {
        this.id = id;
        this.type = type;
        this.content_name = content_name;
    }

    public Content(int id, boolean select) {
        this.id = id;
        this.select = select;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content_name='" + content_name + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
