package example.guice;

import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;

import java.util.Map;

/**
* Created by joker on 7/5/2014.
*/
public class CounterEntryProcessor implements EntryProcessor<String, Integer>, EntryBackupProcessor<String, Integer> {
    private final int delta;

    public CounterEntryProcessor(int delta) {
        this.delta = delta;
    }

    @Override
    public Integer process(Map.Entry<String, Integer> entry) {
        int newValue = entry.getValue() + delta;
        entry.setValue(newValue);
        return newValue;
    }

    @Override
    public EntryBackupProcessor<String, Integer> getBackupProcessor() {
        return this;
    }

    @Override
    public void processBackup(Map.Entry<String, Integer> entry) {
        process(entry);
    }
}
