package hr.fer.zemris.java.tecaj.hw5.db.filter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.query.QueryParser;

/**
 * Implementation of {@link IFilter}
 * @author Domagoj
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Query parser
	 */
	private QueryParser parser;

	/**
	 * Creates instance of QueryFilter from query condition string.
	 * 
	 * @param query Query condition
	 */
	public QueryFilter(String query) {
		parser = new QueryParser(query);
	}

	@Override
	public boolean accepts(StudentRecord record) {
		return parser.compare(record);
	}

}
