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

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.model.PlayerId;
import be.yildizgames.engine.feature.entity.EntityToCreate;
import be.yildizgames.engine.feature.entity.construction.EntityConstructionStatus;

import java.util.Optional;

/**
 * This class fill the construction manager with requests coming from the queue manager.
 * @author Grégory Van den Borre
 */
public class ConstructionManagerFiller implements ConstructionQueueListener {

    /**
     * Manager responsible to build entities.
     */
    private final ConstructionManager manager;

    private final BuilderManager builderManager;

    /**
     * Create a new instance filler.
     * @param manager Manager responsible to build entities.
     */
    public ConstructionManagerFiller(BuilderManager builderManager, ConstructionManager manager) {
        super();
        this.manager = manager;
        this.builderManager = builderManager;
    }

    @Override
    public void notify(ConstructionQueue list) {
        //does nothing.
    }

    @Override
    public void add(EntityConstructionStatus toBuild, PlayerId p, EntityId builderId) {
        Optional<Builder> builder = builderManager.getBuilderById(builderId);
        builder.ifPresent(b -> {
            EntityToCreate etc = new EntityToCreate(
                    toBuild.type,
                    //toBuild.data,
                    b.getBuildPosition(),
                    Point3D.BASE_DIRECTION,
                    p);
            this.manager.createEntity(etc);
        });
    }
}
