package SYSC4806.survey;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Poll implements Serializable {

    private Long id;
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
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
