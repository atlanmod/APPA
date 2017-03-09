/*
 * Created on 30 juin 07
 *
 */
package fr.inria.atlanmod.appa.service;

import fr.inria.atlanmod.appa.base.Service;

/**
 * @author sunye
 *
 * Key-based timestamping service
 * 
 */
public interface KTService extends Service {

    public long generateTimestamp();
    public long lastTimestamp();
}
