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
import org.elasticsearch.painless.Definition.Sort;
import org.elasticsearch.painless.Variables;
import org.elasticsearch.painless.MethodWriter;

/**
 * Represents the top-level node for an expression as a statement.
 */
public final class SExpression extends AStatement {

    AExpression expression;

    public SExpression(int line, String location, AExpression expression) {
        super(line, location);

        this.expression = expression;
    }

    @Override
    void analyze(Variables variables) {
        expression.read = lastSource;
        expression.analyze(variables);

        if (!lastSource && !expression.statement) {
            throw new IllegalArgumentException(error("Not a statement."));
        }

        final boolean rtn = lastSource && expression.actual.sort != Sort.VOID;

        expression.expected = rtn ? Definition.OBJECT_TYPE : expression.actual;
        expression.internal = rtn;
        expression = expression.cast(variables);

        methodEscape = rtn;
        loopEscape = rtn;
        allEscape = rtn;
        statementCount = 1;
    }

    @Override
    void write(MethodWriter adapter) {
        writeDebugInfo(adapter);
        expression.write(adapter);

        if (methodEscape) {
            adapter.returnValue();
        } else {
            adapter.writePop(expression.expected.sort.size);
        }
    }
}
