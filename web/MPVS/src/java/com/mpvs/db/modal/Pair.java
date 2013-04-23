/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db.modal;

import com.mpvs.db.ResultsContent;
import com.mpvs.db.ResultsStudent;
import com.mpvs.db.Users;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author noman
 */
public class Pair implements Serializable {

    ResultsStudent student;

    public Pair() {
        this.student = null;
        this.rc = null;
    }

    public Pair(ResultsStudent student, ArrayList<ResultsContent> rc) {
        this.student = student;
        this.rc = rc;
    }
    ArrayList<ResultsContent> rc;

    public ResultsStudent getStudent() {
        return student;
    }

    public void setStudent(ResultsStudent student) {
        this.student = student;
    }

    public ArrayList<ResultsContent> getRc() {
        return rc;
    }

    public void setRc(ArrayList<ResultsContent> rc) {
        this.rc = rc;
    }
}
