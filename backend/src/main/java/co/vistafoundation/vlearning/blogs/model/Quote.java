package co.vistafoundation.vlearning.blogs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idquote", nullable = false)
    private Integer idquote;

    @Column(name = "quote", length = 500)
    private String quote;

    @Column(name = "author", length = 45)
    private String author;

    // Constructors
    public Quote() {
    }

    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    // Getters and Setters
    public Integer getIdquote() {
        return idquote;
    }

    public void setIdquote(Integer idquote) {
        this.idquote = idquote;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // toString method
    @Override
    public String toString() {
        return "Quote{" +
                "idquote=" + idquote +
                ", quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}