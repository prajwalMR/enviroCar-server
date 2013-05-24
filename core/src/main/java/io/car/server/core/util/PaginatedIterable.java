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
package io.car.server.core.util;


import com.google.common.base.Objects;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public abstract class PaginatedIterable<T> implements Iterable<T> {
    private final Pagination current;
    private final Pagination next;
    private final Pagination last;
    private final Pagination previous;
    private final Pagination first;

    public PaginatedIterable(Pagination current, int elements) {
        this.first = current == null ? null : current.first();
        this.previous = current == null ? null : current.previous();
        this.current = current;
        this.next = current == null ? null : current.next(elements);
        this.last = current == null ? null : current.last(elements);
    }

    public Pagination getLast() {
        return last;
    }

    public Pagination getNext() {
        return next;
    }

    public Pagination getCurrent() {
        return current;
    }

    public Pagination getPrevious() {

        return previous;
    }
    public Pagination getFirst() {
        return first;
    }

    public boolean isPaginated() {
        return this.current != null;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("first", getFirst())
                .add("previous", getPrevious())
                .add("current", getCurrent())
                .add("next", getNext())
                .add("last", getLast())
                .toString();
    }
}
