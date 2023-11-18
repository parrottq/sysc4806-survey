package SYSC4806.survey;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Poll implements Serializable {

    private UUID id;
    private String title;
    private ArrayList<Question> questions;
    private boolean isClosed;

    public Poll() {
        this("", new ArrayList<>());
    }

    public Poll(String title, ArrayList<Question> questions) {
        this.title = title;
        this.questions = questions;
        this.isClosed = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    @OneToMany(cascade = { CascadeType.PERSIST })
    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = new ArrayList<>(questions);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String status(){
        if (isClosed()){
            return "Closed";
        }
        else {
            return "Open";
        }
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String toString() {
        String s = "";
        s += "Survey-Title:" + title;
        s += "|isClosed:" + isClosed;
        s += "|id:" + id;
        s += "|questions:" + questions;

        return s;
    }
}
