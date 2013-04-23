/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author noman
 */
@Entity
@Table(name = "results_content")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultsContent.findAll", query = "SELECT r FROM ResultsContent r"),
    @NamedQuery(name = "ResultsContent.findById", query = "SELECT r FROM ResultsContent r WHERE r.id = :id"),
    @NamedQuery(name = "ResultsContent.findByMarks", query = "SELECT r FROM ResultsContent r WHERE r.marks = :marks"),
    @NamedQuery(name = "ResultsContent.findByUpdatedAt", query = "SELECT r FROM ResultsContent r WHERE r.updatedAt = :updatedAt"),
    @NamedQuery(name = "ResultsContent.findByCreatedAt", query = "SELECT r FROM ResultsContent r WHERE r.createdAt = :createdAt")})
public class ResultsContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "marks")
    private Float marks;
    @Lob
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "results_student_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ResultsStudent resultsStudentId;
    @JoinColumn(name = "exam_contents_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExamContents examContentsId;

    public ResultsContent() {
    }

    public ResultsContent(Integer id) {
        this.id = id;
    }

    public ResultsContent(Integer id, Date updatedAt, Date createdAt) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getMarks() {
        return marks;
    }

    public void setMarks(Float marks) {
        this.marks = marks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ResultsStudent getResultsStudentId() {
        return resultsStudentId;
    }

    public void setResultsStudentId(ResultsStudent resultsStudentId) {
        this.resultsStudentId = resultsStudentId;
    }

    public ExamContents getExamContentsId() {
        return examContentsId;
    }

    public void setExamContentsId(ExamContents examContentsId) {
        this.examContentsId = examContentsId;
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
        if (!(object instanceof ResultsContent)) {
            return false;
        }
        ResultsContent other = (ResultsContent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.ResultsContent[ id=" + id + " ]";
    }
    
}
