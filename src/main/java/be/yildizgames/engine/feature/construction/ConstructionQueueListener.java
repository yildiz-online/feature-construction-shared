/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
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

import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.model.PlayerId;
import be.yildizgames.engine.feature.entity.construction.EntityConstructionStatus;

/**
 * Listen about an entity construction queue state change.
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface ConstructionQueueListener {

    /**
     * Notify about a change in the state.
     * @param queue Queue changed.
     */
    void notify(ConstructionQueue queue);

    /**
     * Notify when a new request is added to the queue.
     * @param toBuild Construction data.
     * @param playerId Id of the player requesting the build.
     * @param builderId Id of the entity building this entity.
     */
    default void add(EntityConstructionStatus toBuild, PlayerId playerId, EntityId builderId) {
    }

}
