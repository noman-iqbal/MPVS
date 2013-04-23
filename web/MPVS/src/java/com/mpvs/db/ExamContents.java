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
@Table(name = "exam_contents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamContents.findAll", query = "SELECT e FROM ExamContents e"),
    @NamedQuery(name = "ExamContents.findById", query = "SELECT e FROM ExamContents e WHERE e.id = :id"),
    @NamedQuery(name = "ExamContents.findByName", query = "SELECT e FROM ExamContents e WHERE e.name = :name"),
    @NamedQuery(name = "ExamContents.findByPercentage", query = "SELECT e FROM ExamContents e WHERE e.percentage = :percentage"),
    @NamedQuery(name = "ExamContents.findByCreatedAt", query = "SELECT e FROM ExamContents e WHERE e.createdAt = :createdAt"),
    @NamedQuery(name = "ExamContents.findByUpdatedAt", query = "SELECT e FROM ExamContents e WHERE e.updatedAt = :updatedAt")})
public class ExamContents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "percentage")
    private float percentage;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Exams examId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examContentsId")
    private Collection<ResultsContent> resultsContentCollection;

    public ExamContents() {
    }

    public ExamContents(Integer id) {
        this.id = id;
    }

    public ExamContents(Integer id, String name, float percentage, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
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

    public Exams getExamId() {
        return examId;
    }

    public void setExamId(Exams examId) {
        this.examId = examId;
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
        if (!(object instanceof ExamContents)) {
            return false;
        }
        ExamContents other = (ExamContents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.ExamContents[ id=" + id + " ]";
    }
    
}
