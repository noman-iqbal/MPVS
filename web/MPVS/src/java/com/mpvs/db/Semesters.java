/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author noman
 */
@Entity
@Table(name = "semesters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semesters.findAll", query = "SELECT s FROM Semesters s"),
    @NamedQuery(name = "Semesters.findById", query = "SELECT s FROM Semesters s WHERE s.id = :id"),
    @NamedQuery(name = "Semesters.findByName", query = "SELECT s FROM Semesters s WHERE s.name = :name")})
public class Semesters implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public Semesters() {
    }

    public Semesters(Integer id) {
        this.id = id;
    }

    public Semesters(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semesters)) {
            return false;
        }
        Semesters other = (Semesters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.Semesters[ id=" + id + " ]";
    }
    
}
