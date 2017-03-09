/*
 * Created on 26 juil. 07
 *
 */
package fr.inria.atlanmod.appa.base;

import fr.inria.atlanmod.appa.exception.OperationFailedException;

public interface Registry extends Service {

    public void publish(Service s) throws OperationFailedException;

    public void unpublish(Service s) throws OperationFailedException;

}
