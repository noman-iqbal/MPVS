/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author noman
 */
@Entity
@Table(name = "subjects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subjects.findAll", query = "SELECT s FROM Subjects s"),
    @NamedQuery(name = "Subjects.findById", query = "SELECT s FROM Subjects s WHERE s.id = :id"),
    @NamedQuery(name = "Subjects.findBySubjectId", query = "SELECT s FROM Subjects s WHERE s.subjectId = :subjectId"),
    @NamedQuery(name = "Subjects.findByName", query = "SELECT s FROM Subjects s WHERE s.name = :name"),
    @NamedQuery(name = "Subjects.findByCreditHr", query = "SELECT s FROM Subjects s WHERE s.creditHr = :creditHr"),
    @NamedQuery(name = "Subjects.findByStatus", query = "SELECT s FROM Subjects s WHERE s.status = :status"),
    @NamedQuery(name = "Subjects.findByCreatedAt", query = "SELECT s FROM Subjects s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Subjects.findByUpdatedAt", query = "SELECT s FROM Subjects s WHERE s.updatedAt = :updatedAt")})
public class Subjects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "subject_id")
    private String subjectId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "credit_hr")
    private int creditHr;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "subjectId")
    private Collection<TimeSlots> timeSlotsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectId")
    private Collection<UsersSubject> usersSubjectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sbId")
    private Collection<Exams> examsCollection;

    public Subjects() {
    }

    public Subjects(Integer id) {
        this.id = id;
    }

    public Subjects(Integer id, String subjectId, String name, int creditHr, String status, Date createdAt, Date updatedAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.name = name;
        this.creditHr = creditHr;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHr() {
        return creditHr;
    }

    public void setCreditHr(int creditHr) {
        this.creditHr = creditHr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @XmlTransient
    public Collection<TimeSlots> getTimeSlotsCollection() {
        return timeSlotsCollection;
    }

    public void setTimeSlotsCollection(Collection<TimeSlots> timeSlotsCollection) {
        this.timeSlotsCollection = timeSlotsCollection;
    }

    @XmlTransient
    public Collection<UsersSubject> getUsersSubjectCollection() {
        return usersSubjectCollection;
    }

    public void setUsersSubjectCollection(Collection<UsersSubject> usersSubjectCollection) {
        this.usersSubjectCollection = usersSubjectCollection;
    }

    @XmlTransient
    public Collection<Exams> getExamsCollection() {
        return examsCollection;
    }

    public void setExamsCollection(Collection<Exams> examsCollection) {
        this.examsCollection = examsCollection;
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
        if (!(object instanceof Subjects)) {
            return false;
        }
        Subjects other = (Subjects) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.Subjects[ id=" + id + " ]";
    }
    
}
