package example.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Singleton
public class CounterService {

    private static final HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();

    private IMap<String, Integer> map = hazelcast.getMap("counter");

    public CounterService() {
        map.putIfAbsent("counterKey", 0);
    }

    public int get() {
        return map.get("counterKey");
    }

    public int increase(final int delta) {
        return (Integer) map.executeOnKey("counterKey", new CounterEntryProcessor(delta));
    }

}
