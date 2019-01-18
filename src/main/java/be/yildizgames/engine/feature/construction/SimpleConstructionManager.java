/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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

import be.yildizgames.common.model.EntityId;
import be.yildizgames.engine.feature.entity.EntityInConstruction;

/**
 * An entity construction manager will build entities, once the process is done, the listeners are notified.
 * @param <E> EntityInConstruction real type.
 * @author Grégory Van den Borre
 */
public interface SimpleConstructionManager<E extends EntityInConstruction> {

    /**
     * Add one or several listener to notify when a construction is completed.
     *
     * @param l Listeners to notify.
     */
    void willNotify(ConstructionListener... l);

    /**
     * Build an entity without any waiting time.
     * @param eic Entity to build state.
     * @param builderId Unique id of the builder.
     * @param index Unique index for this construction.
     */
    void createEntity(E eic, EntityId builderId, int index);
}
