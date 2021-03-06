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
@Table(name = "requests_to")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestsTo.findAll", query = "SELECT r FROM RequestsTo r"),
    @NamedQuery(name = "RequestsTo.findById", query = "SELECT r FROM RequestsTo r WHERE r.id = :id")})
public class RequestsTo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users toId;
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Requests requestId;

    public RequestsTo() {
    }

    public RequestsTo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getToId() {
        return toId;
    }

    public void setToId(Users toId) {
        this.toId = toId;
    }

    public Requests getRequestId() {
        return requestId;
    }

    public void setRequestId(Requests requestId) {
        this.requestId = requestId;
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
        if (!(object instanceof RequestsTo)) {
            return false;
        }
        RequestsTo other = (RequestsTo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mpvs.db.RequestsTo[ id=" + id + " ]";
    }
    
}
