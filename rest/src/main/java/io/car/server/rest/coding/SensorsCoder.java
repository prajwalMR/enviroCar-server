/*
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
package io.car.server.rest.coding;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;

import io.car.server.core.entities.Sensor;
import io.car.server.core.entities.Sensors;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class SensorsCoder extends AbstractEntityEncoder<Sensors> {
    private final EntityEncoder<Sensor> sensorEncoder;

    @Inject
    public SensorsCoder(EntityEncoder<Sensor> sensorEncoder) {
        this.sensorEncoder = sensorEncoder;
    }

    @Override
    public ObjectNode encode(Sensors t, MediaType mediaType) {
        ObjectNode root = getJsonFactory().objectNode();
        ArrayNode sensors = root.putArray(JSONConstants.SENSORS_KEY);
        for (Sensor u : t) {
            sensors.add(sensorEncoder.encode(u, mediaType));
        }
        return root;
    }
}
