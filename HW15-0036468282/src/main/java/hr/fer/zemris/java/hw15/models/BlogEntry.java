package hr.fer.zemris.java.hw15.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Blog entry class.
 * 
 * @author Domagoj Tokić
 *
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** Blog entry id */
	@Id
	@GeneratedValue
	private Long id;

	/** Comments for current blog entry */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();

	/** Blog entry author */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser author;

	/** Time of creation */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	/** Time last modified */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastModifiedAt;

	/** Blog entry title */
	@Column(nullable = false, length = 40)
	private String title;

	/** Blog entry text */
	@Column(nullable = false, length = 4096)
	private String text;

	/**
	 * Return blog entry id.
	 * @return blog entry id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets blog entry id.
	 * @param id Blog entry id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns blog comments.
	 * @return blog comments.
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Adds comment to blog entry
	 * @param comment Comment
	 */
	public void addComment(BlogComment comment) {
		comments.add(comment);
	}

	/**
	 * Returns time of creation.
	 * @return time of creation.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets time of creation 
	 * @param createdAt Time of creation.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns last modified time.
	 * @return last modified time.
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets last modified time.
	 * @param lastModifiedAt Last modified time.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns blog entry title.
	 * @return blog entry title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets blog entry title.
	 * @param title Blog entry title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns blog entry text.
	 * @return blog entry text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets blog entry text.
	 * @param text Blog entry text.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns blog entry author.
	 * @return blog entry author.
	 */
	public BlogUser getAuthor() {
		return author;
	}

	/**
	 * Sets blog entry author.
	 * @param author Blog entry author.
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}