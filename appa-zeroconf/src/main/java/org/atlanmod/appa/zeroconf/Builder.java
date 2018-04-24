package org.atlanmod.appa.zeroconf;

import org.atlanmod.appa.zeroconf.builders.IServiceName;
import org.atlanmod.appa.zeroconf.builders.QueryBuilder;
import org.atlanmod.appa.zeroconf.builders.ServiceBuilder;

public class Builder {

    private final Salut salut;

    public Builder(Salut salut) {
        this.salut = salut;
    }

    /**
     * Creates a new instance of ServiceBuilder to create and publish Services.
     *
     * @return A ServiceBuilder
     */
    public IServiceName service() {
        return new ServiceBuilder(salut);
    }

    public QueryBuilder query() {
        return new QueryBuilder();
    }
}
