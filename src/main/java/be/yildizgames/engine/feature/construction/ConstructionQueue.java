/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.engine.feature.construction;

import be.yildizgames.common.collection.Lists;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.engine.feature.entity.construction.EntityConstructionStatus;
import be.yildizgames.engine.feature.entity.data.EntityType;

import java.util.Collections;
import java.util.List;

/**
 * Simple list of representation for entities in a queue waiting for construction.
 *
 * @author Grégory Van den Borre
 */
public final class ConstructionQueue<R extends EntityConstructionStatus> {

    /**
     * Wrapped list of elements,
     */
    private final List<R> entities = Lists.newList();

    /**
     * Id of the builder holding this queue.
     */
    private final EntityId builderId;

    /**
     * Queue maximum size.
     */
    private int maxSize = 0;

    /**
     * Create a new instance.
     *
     * @param builderId Id of the builder holding this queue.
     * @param maxSize Maximum size for the queue.
     * @throws NullPointerException If builderId is null.
     */
    public ConstructionQueue(final EntityId builderId, final int maxSize) {
        super();
        assert builderId != null;
        this.builderId = builderId;
        this.maxSize = maxSize;
    }

    /**
     * Add a new entity to build in the queue.
     *
     * @param e Entity data.
     */
    public void add(final R e) {
        if(this.entities.size() == this.maxSize) {
            throw new ConstructionQueueFullException();
        }
        this.entities.add(e);
    }

    public boolean isEmpty() {
        return this.entities.isEmpty();
    }

    /**
     * Reset the construction list with new values.
     *
     * @param list New values to set in the list.
     * @throws ConstructionQueueFullException If the list size is bigger than the max size.
     */
    public void set(List<R> list) {
        this.entities.clear();
        if (list.size() > this.maxSize) {
            throw new ConstructionQueueFullException();
        }
        if (list.contains(null)) {
            throw new NullPointerException("The list contains null values.");
        }
        this.entities.addAll(list);
    }

    public List<R> getList() {
        return Collections.unmodifiableList(entities);
    }

    public boolean remove(int request) {
        for (R e : this.entities) {
            if (e.index == request) {
                this.entities.remove(e);
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the number of entities for a given type.
     *
     * @param type Type to check.
     * @return The number of entities in the list matching the type.
     */
    public int getNumberOfEntities(final EntityType type) {
        int result = 0;
        for (R c : this.entities) {
            if (c.type.equals(type)) {
                result++;
            }
        }
        return result;
    }

    public boolean hasOnlyOneElement() {
        return this.entities.size() == 1;
    }

    public EntityId getBuilderId() {
        return builderId;
    }

}
