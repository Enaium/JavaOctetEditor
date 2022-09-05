/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.joe.wrapper;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Enaium
 * @since 1.2.0
 */
public class ObjectWrapper extends Wrapper<Object> {
    public ObjectWrapper(Object wrapper) {
        super(wrapper);
    }

    @Override
    public String toString() {
        if (getWrapper() instanceof AnnotationNode) {
            return ((AnnotationNode) getWrapper()).desc;
        } else if (getWrapper() instanceof ArrayList<?>) {
            return ((ArrayList<?>) getWrapper()).stream().map(it -> new ObjectWrapper(it).toString()).collect(Collectors.toList()).toString();
        } else if (getWrapper() instanceof Object[]) {
            return Arrays.toString(((Object[]) getWrapper()));
        } else if (getWrapper() instanceof Type) {
            return ((Type) getWrapper()).toString();
        }
        return getWrapper().toString();
    }
}
