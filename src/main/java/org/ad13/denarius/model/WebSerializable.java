package org.ad13.denarius.model;

import java.util.Map;

/**
 * Web Serializable objects can return a map with all the useful information for
 * that object.
 * 
 * @author Andrew Dodd
 */
public interface WebSerializable {
    public Map<String, Object> serializeForWeb();
}
