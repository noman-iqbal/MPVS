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
@Table(name = "users_semester")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersSemester.findAll", query = "SELECT u FROM UsersSemester u"),
    @NamedQuery(name = "UsersSemester.findById", query = "SELECT u FROM UsersSemester u WHERE u.id = :id"),
    @NamedQuery(name = "UsersSemester.findByUserStudentId", query = "SELECT u FROM UsersSemester u WHERE u.userStudentId = :userStudentId"),
    @NamedQuery(name = "UsersSemester.findBySemesterId", query = "SELECT u FROM UsersSemester u WHERE u.semesterId = :semesterId")})
public class UsersSemester implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_student_id")
    private int userStudentId;
    @Basic(optional = false)
    @Column(name = "semester_id")
    private int semesterId;

    public UsersSemester() {
    }

    public UsersSemester(Integer id) {
        this.id = id;
    }

    public UsersSemester(Integer id, int userStudentId, int semesterId) {
        this.id = id;
        this.userStudentId = userStudentId;
        this.semesterId = semesterId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserStudentId() {
        return userStudentId;
    }

    public void setUserStudentId(int userStudentId) {
        this.userStudentId = userStudentId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
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
        if (!(object instanceof UsersSemester)) {
            return false;
        }
        UsersSemester other = (UsersSemester) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.UsersSemester[ id=" + id + " ]";
    }
    
}
