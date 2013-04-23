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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author noman
 */
@Entity
@Table(name = "users_subject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersSubject.findAll", query = "SELECT u FROM UsersSubject u"),
    @NamedQuery(name = "UsersSubject.findById", query = "SELECT u FROM UsersSubject u WHERE u.id = :id")})
public class UsersSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Subjects subjectId;
    @JoinColumn(name = "users_student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users usersStudentId;

    public UsersSubject() {
    }

    public UsersSubject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subjects getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subjects subjectId) {
        this.subjectId = subjectId;
    }

    public Users getUsersStudentId() {
        return usersStudentId;
    }

    public void setUsersStudentId(Users usersStudentId) {
        this.usersStudentId = usersStudentId;
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
        if (!(object instanceof UsersSubject)) {
            return false;
        }
        UsersSubject other = (UsersSubject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.UsersSubject[ id=" + id + " ]";
    }
    
}
