package sbac.model;

import static sbac.util.StringUtil.trim;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.utils.IndexType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Indexes(@Index(fields = { @Field(value = "name", type = IndexType.TEXT),
		@Field(value = "year", type = IndexType.TEXT) }))
public final class Name {
	private static final Logger log = LoggerFactory.getLogger(Name.class);

	@Id
	private String _id = null;
	@Property
	private String name = null;
	private char gender = 'u';
	private int count = 0;
	private String year = null;

	public Name(String name, char gender, int count, String year) {
		name(name);
		gender(gender);
		count(count);
		year(year);
	}

	public String _id() {
		return _id;
	}

	public String name() {
		return name;
	}

	private void name(String name) {
		name = trim(name);
		if (name.isEmpty()) {
			log.error("name must not be null or empty string");
			throw new IllegalArgumentException();
		}
		this.name = name.toLowerCase();
	}

	public char gender() {
		return gender;
	}

	private void gender(char gender) {
		if (gender != 'm' && gender != 'f') {
			log.error("gender must either be 'm' or 'f'. gender={}", gender);
			throw new IllegalArgumentException();
		}
		this.gender = gender;
	}

	public int count() {
		return count;
	}

	private void count(int count) {
		if (count < 1) {
			log.error("count must be greater than zero. count={}", count);
			throw new IllegalArgumentException();
		}
		this.count = count;
	}

	public String year() {
		return year;
	}

	private void year(String year) {
		year = trim(year);
		if (year.isEmpty()) {
			log.error("year must not be null or empty string");
			throw new IllegalArgumentException();
		}
		int len = year.length();
		if (len != 4) {
			log.error("year must be exactly 4 chars long. year len={}", len);
			throw new IllegalArgumentException();
		}
		this.year = year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + gender;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Name)) {
			return false;
		}
		Name other = (Name) obj;
		if (count != other.count) {
			return false;
		}
		if (gender != other.gender) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!year.equals(other.year)) {
			return false;
		}
		return true;
	}

}
