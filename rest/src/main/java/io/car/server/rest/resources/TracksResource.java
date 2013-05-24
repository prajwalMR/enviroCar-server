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
package io.car.server.rest.resources;

import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.car.server.core.entities.Track;
import io.car.server.core.entities.Tracks;
import io.car.server.core.entities.User;
import io.car.server.core.exception.ResourceAlreadyExistException;
import io.car.server.core.exception.TrackNotFoundException;
import io.car.server.core.exception.UserNotFoundException;
import io.car.server.core.exception.ValidationException;
import io.car.server.rest.MediaTypes;
import io.car.server.rest.RESTConstants;
import io.car.server.rest.Schemas;
import io.car.server.rest.auth.Authenticated;
import io.car.server.rest.validation.Schema;

/**
 * @author Arne de Wall <a.dewall@52north.org>
 */
public class TracksResource extends AbstractResource {
    public static final String TRACK = "{track}";
    private User user;

    @Inject
    public TracksResource(@Assisted @Nullable User user) {
        this.user = user;
    }

    @GET
    @Schema(response = Schemas.TRACKS)
    @Produces(MediaTypes.TRACKS)
    public Tracks get(
            @QueryParam(RESTConstants.LIMIT) @DefaultValue("0") int limit)
            throws UserNotFoundException {
        return user != null
               ? getService().getTracks(user)
               : getService().getTracks();
    }

    @POST
    @Schema(request = Schemas.TRACK_CREATE)
    @Consumes(MediaTypes.TRACK_CREATE)
    @Authenticated
    public Response create(Track track) throws ValidationException,
                                               ResourceAlreadyExistException,
                                               UserNotFoundException {
        if (user != null && !canModifyUser(user)) {
            throw new WebApplicationException(Status.FORBIDDEN);
        }
        track = getService().createTrack(track.setUser(
                getService().getUser(getCurrentUser())));
        return Response.created(
                getUriInfo()
                .getRequestUriBuilder()
                .path(track.getIdentifier()).build()).build();
    }

    @Path(TRACK)
    public TrackResource user(@PathParam("track") String track)
            throws TrackNotFoundException {
        return getResourceFactory().createTrackResource(track);
    }
}
