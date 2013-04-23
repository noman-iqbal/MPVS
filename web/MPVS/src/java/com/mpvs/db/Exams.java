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
import javax.persistence.Lob;
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
@Table(name = "exams")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exams.findAll", query = "SELECT e FROM Exams e"),
    @NamedQuery(name = "Exams.findById", query = "SELECT e FROM Exams e WHERE e.id = :id"),
    @NamedQuery(name = "Exams.findByTitle", query = "SELECT e FROM Exams e WHERE e.title = :title"),
    @NamedQuery(name = "Exams.findByStartDate", query = "SELECT e FROM Exams e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Exams.findByCreatedAt", query = "SELECT e FROM Exams e WHERE e.createdAt = :createdAt"),
    @NamedQuery(name = "Exams.findByUpdatedAt", query = "SELECT e FROM Exams e WHERE e.updatedAt = :updatedAt"),
    @NamedQuery(name = "Exams.findByStatus", query = "SELECT e FROM Exams e WHERE e.status = :status")})
public class Exams implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examId")
    private Collection<ExamMember> examMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examId")
    private Collection<Results> resultsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examId")
    private Collection<ExamContents> examContentsCollection;
    @JoinColumn(name = "held_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TimeSlots heldId;
    @JoinColumn(name = "user_creater_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userCreaterId;
    @JoinColumn(name = "sb_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Subjects sbId;

    public Exams() {
    }

    public Exams(Integer id) {
        this.id = id;
    }

    public Exams(Integer id, String title, String description, Date startDate, Date createdAt, Date updatedAt, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<ExamMember> getExamMemberCollection() {
        return examMemberCollection;
    }

    public void setExamMemberCollection(Collection<ExamMember> examMemberCollection) {
        this.examMemberCollection = examMemberCollection;
    }

    @XmlTransient
    public Collection<Results> getResultsCollection() {
        return resultsCollection;
    }

    public void setResultsCollection(Collection<Results> resultsCollection) {
        this.resultsCollection = resultsCollection;
    }

    @XmlTransient
    public Collection<ExamContents> getExamContentsCollection() {
        return examContentsCollection;
    }

    public void setExamContentsCollection(Collection<ExamContents> examContentsCollection) {
        this.examContentsCollection = examContentsCollection;
    }

    public TimeSlots getHeldId() {
        return heldId;
    }

    public void setHeldId(TimeSlots heldId) {
        this.heldId = heldId;
    }

    public Users getUserCreaterId() {
        return userCreaterId;
    }

    public void setUserCreaterId(Users userCreaterId) {
        this.userCreaterId = userCreaterId;
    }

    public Subjects getSbId() {
        return sbId;
    }

    public void setSbId(Subjects sbId) {
        this.sbId = sbId;
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
        if (!(object instanceof Exams)) {
            return false;
        }
        Exams other = (Exams) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.Exams[ id=" + id + " ]";
    }
    
}
