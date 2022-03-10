package org.craftedsw.tripservicekata.trip;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    private static final List<Trip> NO_TRIPS = emptyList();

    private static final List<Trip> TRIPS = Arrays.asList(new Trip());

    private static final User USER = new User();

    @Spy
    TripService tripService;

    @Test
    void no_logged_in_user_throws_exception() {
        loggedInUserIs(null);
        assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(USER));
    }

    @Test
    void logged_in_user_throws_no_exception() {
        loggedInUserIs(USER);

        assertDoesNotThrow(() -> tripService.getTripsByUser(USER));
    }

    @Test
    void when_user_has_no_friends_then_no_trips_are_returned() {
        User userWithNoFriends = new User();

        loggedInUserIs(USER);

        List<Trip> trips = tripService.getTripsByUser(userWithNoFriends);
        assertTrue(trips.isEmpty());
        verify(tripService, never()).tripsBy(userWithNoFriends);
    }

    @Test
    void when_user_is_not_friends_with_logged_in_user_no_trips_are_returned(){
        User userWithFriends = new User();
        User loggedInUser = new User();
        userWithFriends.addFriend(new User());
        userWithFriends.addFriend(new User());
        userWithFriends.addFriend(new User());

        loggedInUserIs(loggedInUser);

        List<Trip> trips = tripService.getTripsByUser(userWithFriends);
        assertTrue(trips.isEmpty());
        verify(tripService, never()).tripsBy(userWithFriends);
    }

    @Test
    void when_user_is_friends_with_logged_in_user_return_friends_trips() {
        User user = new User();
        User usersFriend = new User();
        user.addFriend(usersFriend);

        loggedInUserIs(usersFriend);
        doReturn(TRIPS).when(tripService).tripsBy(user);

        List<Trip> trips = tripService.getTripsByUser(user);
        assertTrue(!trips.isEmpty());
    }

    @Test
    void user_is_friend_of_logged_in_user_that_has_no_trips_returns_no_trips() {
        User user = new User();
        User usersFriend = new User();
        user.addFriend(usersFriend);

        loggedInUserIs(usersFriend);
        doReturn(NO_TRIPS).when(tripService).tripsBy(user);

        List<Trip> trips = tripService.getTripsByUser(user);
        assertTrue(trips.isEmpty());
    }

    private void loggedInUserIs(User user) {
        doReturn(user).when(tripService).loggedInUser();
    }
}
