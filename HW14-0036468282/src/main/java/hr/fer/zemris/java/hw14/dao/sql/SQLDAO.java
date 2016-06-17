package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Implementation of subsystem DAO using SQL technology. Every method expects
 * that it has connection available from {@link SQLConnectionProvider} class.
 * This connection must be set up in some outer class before any of methods
 * implemented in this class are used. Closing connection must occur after all
 * data processing by this class has already occurred.
 * 
 * @author Domagoj Tokić
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls order by id");
			polls = selectPolls(pst);
		} catch (Exception ex) {
			throw new DAOException("Error while reading polls", ex);
		}
		return polls;
	}

	@Override
	public Poll getPollById(long id) {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls where id=?");
			pst.setLong(1, id);
			polls = selectPolls(pst);
		} catch (Exception ex) {
			throw new DAOException("Error while reading polls", ex);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return polls.get(0);
	}

	/**
	 * Executes select query on Polls table in database and returns result in
	 * list.
	 * 
	 * @param pst Prepared query statement.
	 * @return List of {@link Poll} objects.
	 */
	private List<Poll> selectPolls(PreparedStatement pst) {
		List<Poll> polls = new ArrayList<>();
		try {
			ResultSet rs = pst.executeQuery();
			try {
				while (rs != null && rs.next()) {
					Poll poll = new Poll();
					poll.setId(rs.getLong(1));
					poll.setTitle(rs.getString(2));
					poll.setMessage(rs.getString(3));
					polls.add(poll);
				}
			} finally {
				try {
					rs.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (SQLException ignorable) {
		}
		return polls;
	}

	@Override
	public List<PollOption> getPollOptions(String pollID, boolean descending) {
		String query = "select * from PollOptions where pollID=?";
		if (descending) {
			query += " order by votesCount desc";
		}
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(query);
			pst.setLong(1, Long.valueOf(pollID));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						pollOptions.add(pollOption);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while reading vote options", ex);
		}
		return pollOptions;
	}

	@Override
	public List<PollOption> getTopPollOptions(String pollID) {
		String query = "select * from PollOptions where pollID=? and votesCount=(select MAX(votesCount) from PollOptions where pollID=?) order by id";
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(query);
			pst.setLong(1, Long.valueOf(pollID));
			pst.setLong(2, Long.valueOf(pollID));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						pollOptions.add(pollOption);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while reading vote options", ex);
		}
		return pollOptions;
	}

	@Override
	public void addVote(String pollId, String optionId) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(
					"UPDATE PollOptions set votesCount=votesCount+1 WHERE id=? and pollID=?");
			pst.setLong(1, Long.valueOf(optionId));
			pst.setLong(2, Long.valueOf(pollId));

			pst.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}