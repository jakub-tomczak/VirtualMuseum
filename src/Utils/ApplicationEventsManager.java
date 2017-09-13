package Utils;

import Interfaces.IApplicationEvents;

import java.util.ArrayList;
import java.util.List;

public class ApplicationEventsManager {
    private static ApplicationEventsManager instance;

    public static ApplicationEventsManager getInstance() {
        if (instance == null) {
            instance = new ApplicationEventsManager();
        }

        return instance;
    }

    List<IApplicationEvents> applicationEventsSubscribers = new ArrayList<>(10);

    public List<IApplicationEvents> getApplicationEventsSubscribers() {
        return applicationEventsSubscribers;
    }

    private ApplicationEventsManager()
    {

    }

    public void onApplicationStarted()
    {
        for(IApplicationEvents event : applicationEventsSubscribers)
        {
            event.onApplicationStarted();
        }
    }
    public void onApplicationEnded()
    {
        for(IApplicationEvents event : applicationEventsSubscribers)
        {
            event.onApplicationEnded();
        }
    }

    public void subscribeToApplicationEvents(IApplicationEvents subscriber)
    {
        if(!applicationEventsSubscribers.contains(subscriber))
            applicationEventsSubscribers.add(subscriber);
    }

}
