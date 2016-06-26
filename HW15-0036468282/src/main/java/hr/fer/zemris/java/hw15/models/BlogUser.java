package hr.fer.zemris.java.hw15.models;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Blog user class.
 * 
 * @author Domagoj Tokić
 *
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/** Blog user id */
	@Id
	@GeneratedValue
	private Long id;

	/** First name */
	@Column(nullable = false, length = 20)
	private String firstName;

	/** First name */
	@Column(nullable = false, length = 30)
	private String lastName;

	/** Nickname */
	@Column(nullable = false, length = 15, unique = true)
	private String nick;

	/** Email */
	@Column(nullable = false, length = 30)
	private String email;

	/** Password hash */
	@Column(nullable = false, length = 40)
	private String passwordHash;

	/** Users blog entries */
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	private List<BlogEntry> blogEntries;

	/**
	 * Returns users id.
	 * @return users id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets users id.
	 * @param id Users id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns first name.
	 * @return first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name.
	 * @param firstName First name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns last name.
	 * @return last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name.
	 * @param lastName last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns nickname.
	 * @return nickname.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets nickname.
	 * @param nick Nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns email.
	 * @return email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns password hash.
	 * @return password hash.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets password hash.
	 * @param passwordHash Password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Returns blog entries.
	 * @return blog entries.
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Sets blog entries.
	 * @param blogEntries Blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
