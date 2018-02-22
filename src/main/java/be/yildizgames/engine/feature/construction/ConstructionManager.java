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

import be.yildizgames.common.frame.EndFrameListener;
import be.yildizgames.common.frame.FrameManager;
import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.model.PlayerId;
import be.yildizgames.engine.feature.entity.Entity;
import be.yildizgames.engine.feature.entity.EntityCreator;
import be.yildizgames.engine.feature.entity.EntityInConstruction;
import be.yildizgames.engine.feature.entity.EntityToCreate;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Check all builder List and execute their build method. Primary task is Call all builder to create their units, if they don't have anything to create, they are removed from the builder list.
 * @author Grégory Van den Borre
 */
public class ConstructionManager<T extends Entity, E extends EntityInConstruction, D> extends EndFrameListener implements CompleteConstructionManager<E> {

    private static final Logger LOGGER = LogFactory.getInstance().getLogger(ConstructionManager.class);

    /**
     * List of entities waiting to be build.
     */
    private final List<WaitingEntity<E>> entityToBuildList = new ArrayList<>();

    /**
     * Factory to build the entities.
     */
    private final EntityFactory<T, E> associatedFactory;

    /**
     * Listener to notify when a construction is completed.
     */
    private final Set<ConstructionListener> listenerList = new TreeSet<>();

    private final EntityCreator creator;

    private List<EntityToCreate> entityToCreateList = new ArrayList<>();

    /**
     * Create a new BuilderManager.
     * @param frame Frame manager listening to this object.
     * @param factory Entity factory to materialize entities.
     * @param creator The class creating the entities in the system.
     */
    public ConstructionManager(FrameManager frame, EntityFactory<T, E> factory, EntityCreator creator) {
        super();
        this.associatedFactory = factory;
        this.creator = creator;
        frame.addFrameListener(this);
    }

    @Override
    public void createEntity(final E entity, final EntityId builderId, final int index) {
        T buildEntity = this.associatedFactory.createEntity(entity);
        LOGGER.debug("Entity built " + entity.getId());
        this.listenerList.forEach(l -> l.entityComplete(buildEntity.getId(), buildEntity.getOwner(), buildEntity.getType(), builderId, index));
    }

    @Override
    public void createEntity(final EntityToCreate entity) {
        this.entityToCreateList.add(entity);
    }

    @Override
    public void cancel(final WaitingEntity w) {
        if (this.entityToBuildList.remove(w)) {
            this.listenerList.forEach(l -> l.entityConstructionCanceled(w));
        }
    }

    /**
     * Call the building logic for all builder in the list.
     *
     * @param time Time since the last call.
     */
    @Override
    public boolean frameEnded(final long time) {
        for (int i = 0; i < this.entityToBuildList.size(); i++) {
            WaitingEntity<E> waitingEntity = this.entityToBuildList.get(i);
            waitingEntity.representation.reduceTimeLeft(time);
            if (waitingEntity.representation.isTimeElapsed()) {
                T buildEntity = this.associatedFactory.createEntity(waitingEntity.entity);
                LOGGER.debug("Entity built " + waitingEntity.entity.getId());
                this.listenerList.forEach(l -> l.entityComplete(buildEntity.getId(), buildEntity.getOwner(), buildEntity.getType(), waitingEntity.builderId, waitingEntity.representation.index));
                this.entityToBuildList.remove(i);
                i--;
            }
        }
        while(!this.entityToCreateList.isEmpty()) {
            this.creator.create(this.entityToCreateList.remove(0));
            //FIXME missing listener call?
        }
        return true;
    }

    @Override
    public void willNotify(final ConstructionListener... listeners) {
        if (listeners != null) {
            Collections.addAll(this.listenerList, listeners);
        }
    }

    /**
     * Remove a listener to notify when a construction is completed.
     *
     * @param listener Listener to remove.
     */
    public void removeListener(final ConstructionListener listener) {
        this.listenerList.remove(listener);
    }

    @Override
    public List<WaitingEntity> getEntityToBuildList() {
        return Collections.unmodifiableList(this.entityToBuildList);
    }

    @Override
    public List<WaitingEntity> getEntityToBuildList(final PlayerId player) {
        return this.entityToBuildList.stream()
                .filter(w -> w.isOwned(player))
                .collect(Collectors.toList());
    }
}
