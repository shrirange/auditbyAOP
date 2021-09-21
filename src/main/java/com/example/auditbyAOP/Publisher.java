package com.example.auditbyAOP;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang3.SerializationUtils;

import com.example.postgrespartition.Publisher;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Publisher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "publisherId")
	private Integer publisherId;
	
	@Column(name = "publisherName" , length = 1000)
	private String publisherName;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="authorId" , nullable = false)
	private Author author;
	
	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Transient
    private transient Publisher savedState;

    public Publisher getSavedState() {
		return savedState;
	}

	@PostLoad
    private void saveState(){
       this.savedState = SerializationUtils.clone(this); // from apache commons-lang
    }

	@Override
	public String toString() {
		return "Publisher [publisherId=" + publisherId + ", publisherName=" + publisherName + "]";
	}
	
}
