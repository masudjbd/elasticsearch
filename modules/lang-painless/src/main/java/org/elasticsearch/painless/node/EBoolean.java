/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.painless.node;

import org.elasticsearch.painless.Definition;
import org.elasticsearch.painless.Variables;
import org.elasticsearch.painless.MethodWriter;

/**
 * Represents a boolean constant.
 */
public final class EBoolean extends AExpression {

    public EBoolean(int line, String location, boolean constant) {
        super(line, location);

        this.constant = constant;
    }

    @Override
    void analyze(Variables variables) {
        actual = Definition.BOOLEAN_TYPE;
    }

    @Override
    void write(MethodWriter adapter) {
        throw new IllegalArgumentException(error("Illegal tree structure."));
    }
}
