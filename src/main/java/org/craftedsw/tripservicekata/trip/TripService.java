package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		if (!userIsLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		
		if (user.isFriendsWith(loggedInUser())) {
			return tripsBy(user);
		}
		
		return Collections.emptyList();
	}

	private boolean userIsLoggedIn() {
		return loggedInUser() != null;
	}

	protected List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User loggedInUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
