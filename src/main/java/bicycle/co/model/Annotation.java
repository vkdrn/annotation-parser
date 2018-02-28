package bicycle.co.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "version")
public class Annotation implements Serializable {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue
    private Integer id;
    @Column(name = "DATE", nullable = false, length = 100)
    private String date;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name; //Class / Method name
    @Column(name = "TYPE", nullable = false, length = 100)
    private String type; // Class or Method
    @Column(name = "AUTHOR", nullable = false, length = 100)
    private String author;
    @Column(name = "COMMENT", nullable = false, length = 100)
    private String comment;

    public Annotation() {
    }

    public Annotation(Integer id, String date, String name, String type, String author, String comment) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.type = type;
        this.author = author;
        this.comment = comment;
    }

    public Annotation(String date, String name, String type, String author, String comment) {
        this.date = date;
        this.name = name;
        this.type = type;
        this.author = author;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
