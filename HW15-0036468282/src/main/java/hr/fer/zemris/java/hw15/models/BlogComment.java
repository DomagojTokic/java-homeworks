package hr.fer.zemris.java.hw15.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Blog comment class.
 * 
 * @author Domagoj Tokić
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/** Blog comment id */
	@Id
	@GeneratedValue
	private Long id;

	/** Blog entry corresponding to comment */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogEntry blogEntry;

	/** Email */
	@Column(nullable = false, length = 60)
	private String usersEMail;

	/** Comment message */
	@Column(nullable = false, length = 2048)
	private String message;

	/** Posted-on time */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date postedOn;

	/**
	 * Returns blog comment id.
	 * 
	 * @return blog comment id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets blog comment id.
	 * 
	 * @param id Blog comment id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns blog entry for comment.
	 * 
	 * @return blog entry for comment.
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets blog entry.
	 * 
	 * @param blogEntry Blog entry.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns users email.
	 * 
	 * @return users email.
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets users email.
	 * 
	 * @param usersEMail Users email.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns comment message.
	 * 
	 * @return comment message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets comment message.
	 * 
	 * @param message Comment message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns posted-on time.
	 * 
	 * @return posted-on time.
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets posted-on time.
	 * 
	 * @param postedOn Posted-on time.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}