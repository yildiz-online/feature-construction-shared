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

import be.yildizgames.common.model.PlayerId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Grégory Van den Borre
 */
class BuilderManagerTest {

    @Nested
    class GetBuilderByPlayer {

        @Test
        void happyFlow() {
            BuilderManager<Builder> manager = new BuilderManager<>();
            Builder b = Mockito.mock(Builder.class);
            Mockito.when(b.getOwner()).thenReturn(PlayerId.valueOf(2));
            manager.addBuilder(b);
            List<Builder> builders = manager.getBuilderByPlayer(PlayerId.valueOf(2));
            assertEquals(1, builders.size());
        }

        @Test
        void withEmptyResult() {
            BuilderManager<Builder> manager = new BuilderManager<>();
            List<Builder> builders = manager.getBuilderByPlayer(PlayerId.valueOf(5));
            assertTrue(builders.isEmpty());
        }

        @Test
        void withNull() {
            BuilderManager<Builder> manager = new BuilderManager<>();
            assertThrows(AssertionError.class, () -> manager.getBuilderByPlayer(null));
        }

    }

}
