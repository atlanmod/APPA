/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.inria.atlanmod.appa.tester.tester;

import java.lang.reflect.Method;

import fr.inria.atlanmod.appa.tester.parser.BeforeClass;

/**
 *
 * @author sunye
 */
public class BeforeClassMethod extends TestMethod {
    
    public  BeforeClassMethod(Method m) {
        BeforeClass ac = m.getAnnotation(BeforeClass.class);
        timeout = ac.timeout();
        method = m;
        range = this.newRange(ac.range());
    }
}
