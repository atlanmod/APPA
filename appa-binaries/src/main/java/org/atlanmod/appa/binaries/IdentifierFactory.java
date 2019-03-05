package org.atlanmod.appa.binaries;

import org.atlanmod.commons.annotation.Static;

public class IdentifierFactory {

    private Identifier identifier = new HashMapIdentifier();

    private IdentifierFactory() {
    }

    public static IdentifierFactory getInstance() {
        return IdentifierFactory.Holder.INSTANCE;
    }

    public Identifier getDefaultIndentifier() {
        return identifier;
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    @Static
    private static final class Holder {

        /**
         * The instance of the outer class.
         */
        private static final IdentifierFactory INSTANCE = new IdentifierFactory();
    }
}
