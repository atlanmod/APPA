/*
 * Created on 13 juin 2005
 *
 */
package fr.inria.atlanmod.appa.service;

import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;


/**
 * @author sunye
 *
 */
public abstract class AbstractService implements Service {

    final private RamdomId id;
    final private ConnectionDescription cd;


   /**
     *
     */
    public AbstractService(RamdomId id, ConnectionDescription cd) {
        this.id = id;
        this.cd = cd;
    }

    /************** Service Interface *********************/

    public RamdomId id() {
        return id;
    }

    public ConnectionDescription getConnectionDescription() {
         return cd;
    }
}