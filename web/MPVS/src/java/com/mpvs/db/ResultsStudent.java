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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "results_student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultsStudent.findAll", query = "SELECT r FROM ResultsStudent r"),
    @NamedQuery(name = "ResultsStudent.findById", query = "SELECT r FROM ResultsStudent r WHERE r.id = :id"),
    @NamedQuery(name = "ResultsStudent.findByStatus", query = "SELECT r FROM ResultsStudent r WHERE r.status = :status"),
    @NamedQuery(name = "ResultsStudent.findByCreatedAt", query = "SELECT r FROM ResultsStudent r WHERE r.createdAt = :createdAt"),
    @NamedQuery(name = "ResultsStudent.findByUpdatedAt", query = "SELECT r FROM ResultsStudent r WHERE r.updatedAt = :updatedAt")})
public class ResultsStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users studentId;
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Results resultId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resultsStudentId")
    private Collection<ResultsContent> resultsContentCollection;

    public ResultsStudent() {
    }

    public ResultsStudent(Integer id) {
        this.id = id;
    }

    public ResultsStudent(Integer id, String status, Date createdAt, Date updatedAt) {
        this.id = id;
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

    public Users getStudentId() {
        return studentId;
    }

    public void setStudentId(Users studentId) {
        this.studentId = studentId;
    }

    public Results getResultId() {
        return resultId;
    }

    public void setResultId(Results resultId) {
        this.resultId = resultId;
    }

    @XmlTransient
    public Collection<ResultsContent> getResultsContentCollection() {
        return resultsContentCollection;
    }

    public void setResultsContentCollection(Collection<ResultsContent> resultsContentCollection) {
        this.resultsContentCollection = resultsContentCollection;
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
        if (!(object instanceof ResultsStudent)) {
            return false;
        }
        ResultsStudent other = (ResultsStudent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.ResultsStudent[ id=" + id + " ]";
    }
    
}
