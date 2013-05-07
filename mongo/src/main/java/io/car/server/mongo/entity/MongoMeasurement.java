/**
 * Copyright (C) 2013  Christian Autermann, Jan Alexander Wirwahn,
 *                     Arne De Wall, Dustin Demuth, Saqib Rasheed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.car.server.mongo.entity;

import java.util.Set;

import com.github.jmkgreen.morphia.annotations.Embedded;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Property;
import com.github.jmkgreen.morphia.annotations.Reference;
import com.vividsolutions.jts.geom.Point;

import io.car.server.core.Measurement;
import io.car.server.core.MeasurementValue;
import io.car.server.core.MeasurementValues;
import io.car.server.core.User;

/**
 *
 * @author Arne de Wall
 *
 */
@Entity("measurement")
public class MongoMeasurement extends MongoBaseEntity<MongoMeasurement> implements Measurement {
    public static final String PHENOMENONS = "phenomenons";
    public static final String VALUES = "values";
    public static final String LOCATION = "location";
    public static final String USER = "user";
    @Reference
    private MongoUser user;
    @Property(LOCATION)
    private Point location;
    @Embedded(PHENOMENONS)
    private Set<MongoMeasurementValue> values;

    @Override
    public MeasurementValues getValues() {
        return new MeasurementValues(this.values);
    }

    @Override
    public Point getLocation() {
        return this.location;
    }

    @Override
    public MongoMeasurement setLocation(Point location) {
        this.location = location;
        return this;
    }

    @Override
    public int compareTo(Measurement o) {
        return this.getCreationDate().compareTo(o.getCreationDate());
    }

    @Override
    public MongoUser getUser() {
        return user;
    }

    @Override
    public Measurement setUser(User user) {
        this.user = (MongoUser) user;
        return this;
    }

    @Override
    public Measurement addValue(MeasurementValue<?> value) {
        this.values.add((MongoMeasurementValue) value);
        return this;
    }

    @Override
    public Measurement removeValue(MeasurementValue<?> value) {
        this.values.remove((MongoMeasurementValue) value);
        return this;
    }
}
