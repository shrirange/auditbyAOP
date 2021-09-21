package com.example.auditbyAOP;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang3.SerializationUtils;

@Entity
public class Author implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Transient
    private transient Author savedState;

    public Author getSavedState() {
		return savedState;
	}

	@PostLoad
    private void saveState(){
       this.savedState = SerializationUtils.clone(this); // from apache commons-lang
    }
	
	

	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", authorName=" + authorName + ", dateofbirth=" + dateofbirth + "]";
	}



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "authorId")
	private Integer authorId;
	
	@Column(name = "authorName" , length = 1000)
	private String authorName;
	
	@Column(name = "dateofbirth")
	private Date dateofbirth;
	
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}


}
