package org.craftedsw.tripservicekata.user;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.trip.Trip;

public class User {

	private List<Trip> trips = new ArrayList<Trip>();
	private List<User> friends = new ArrayList<User>();
	
	public List<User> getFriends() {
		return friends;
	}
	
	public void addFriend(User user) {
		friends.add(user);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}
	
	public List<Trip> trips() {
		return trips;
	}

    public boolean isFriendsWith(User loggedInUser) {
    	boolean isFriend = false;
    	for (User friend : getFriends()) {
    		if (friend.equals(loggedInUser)) {
    			isFriend = true;
    			break;
    		}
    	}
    	return isFriend;
    }

}
