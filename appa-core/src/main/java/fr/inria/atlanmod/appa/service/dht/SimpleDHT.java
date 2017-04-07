package fr.inria.atlanmod.appa.service.dht;

import fr.inria.atlanmod.appa.core.DHT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created on 05/04/2017.
 *
 * @author AtlanMod team.
 */
public class SimpleDHT<K extends Serializable, V extends Serializable> implements DHT<K,V> {

    private Map<K, V> data = Collections.synchronizedMap(new HashMap<>());
    private Logger logger = LoggerFactory.getLogger(SimpleDHT.class);

    @Override
    public void put(K key, V value) {
        data.put(key, value);
    }

    @Override
    public V get(K key) {
        if (!data.containsKey(key)) {
            logger.warn("Key [{}] does not exist.");
        }
        return data.get(key);
    }

    @Override
    public void remove(K key) {
        data.remove(key);
    }


}
