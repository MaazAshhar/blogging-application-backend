package com.ashhar.blogappapis.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID commentId;
	
	@Column(name="comment_content",length=5000)
	private String content;
	
	
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@PrePersist
	protected void onCreate() {
		addedDate=new Date();
	}
}
