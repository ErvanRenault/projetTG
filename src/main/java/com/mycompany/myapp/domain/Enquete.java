package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Enquete.
 */
@Entity
@Table(name = "enquete")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_societe")
    private String nomSociete;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "stay")
    private Boolean stay;

    @Column(name = "internship_salary")
    private Double internshipSalary;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "comment")
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public Enquete nomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
        return this;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getPhone() {
        return phone;
    }

    public Enquete phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Enquete email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isStay() {
        return stay;
    }

    public Enquete stay(Boolean stay) {
        this.stay = stay;
        return this;
    }

    public void setStay(Boolean stay) {
        this.stay = stay;
    }

    public Double getInternshipSalary() {
        return internshipSalary;
    }

    public Enquete internshipSalary(Double internshipSalary) {
        this.internshipSalary = internshipSalary;
        return this;
    }

    public void setInternshipSalary(Double internshipSalary) {
        this.internshipSalary = internshipSalary;
    }

    public Double getSalary() {
        return salary;
    }

    public Enquete salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getComment() {
        return comment;
    }

    public Enquete comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enquete enquete = (Enquete) o;
        if(enquete.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enquete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enquete{" +
            "id=" + id +
            ", nomSociete='" + nomSociete + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", stay='" + stay + "'" +
            ", internshipSalary='" + internshipSalary + "'" +
            ", salary='" + salary + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
