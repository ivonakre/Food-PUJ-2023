package ba.sum.fpmoz.food.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 50, message = "Polje mora sadržavati barem 2 znaka i manje od 50 znakova.") // Anotator za provjeru duljine unesenog teksta.
    @Column(nullable = false, length = 50)
    private String firstname;

    @Size(min = 2, max = 50, message = "Polje mora sadržavati barem 2 znaka i manje od 50 znakova.") // Anotator za provjeru duljine unesenog teksta.
    @Column(nullable = false, length = 50)
    private String lastname;
    @NotBlank(message="Molimo unesite Vašu email adresu.") // polje email mora biti jedinstveno
    @Email(message = "Unesite ispravnu email adresu.") // polje email mora biti u obliku email adrese
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message="Molimo unesite Vašu lozinku.") // polje mora imati vrijednost
    @Column(nullable = false)
    private String password;

    @NotBlank(message="Molimo ponovite Vašu lozinku.") // polje mora imati vrijednost
    @Transient // polje nije zapisano u bazi no potrebno je radi provjere ponovnog unosa.
    private String passwordRepeat;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "user")
    List<Article> articles;
    @OneToMany(mappedBy = "user")
    List<Invoice> invoices;

    @OneToMany(mappedBy = "user")
    List<Rating> ratings;

    public User(Long id, String firstname, String lastname, String email, String password, String role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Rating> getRantings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}