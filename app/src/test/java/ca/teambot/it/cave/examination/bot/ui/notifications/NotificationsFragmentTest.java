package ca.teambot.it.cave.examination.bot.ui.notifications;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.teambot.it.cave.examination.bot.AlertsAdapter;

public class NotificationsFragmentTest {

    private NotificationsFragment notificationsFragment;

    @Mock
    private DatabaseReference mockDatabaseReference;

    @Mock
    private AlertsAdapter mockAlertsAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        notificationsFragment = new NotificationsFragment();
        notificationsFragment.alertsAdapter = mockAlertsAdapter;
        notificationsFragment.databaseReference = mockDatabaseReference;
    }

    @Test
    public void testRetrieveAlerts() {
        DataSnapshot mockDataSnapshot = mock(DataSnapshot.class);

        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(any());

        // Call the method
        notificationsFragment.retrieveAlerts();

        // Verify that the alertsAdapter is updated with the correct data
        verify(mockAlertsAdapter).setAlertsList(anyList());
    }


    @Test
    public void testShuffleErrors() {
        doAnswer(invocation -> {
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(any());

        notificationsFragment.shuffleErrors();

        verify(mockDatabaseReference).addListenerForSingleValueEvent(any());
    }

}
