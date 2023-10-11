/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.bcel.generic;

import org.apache.bcel.AbstractTestCase;
import org.apache.bcel.classfile.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verify that a constant pool containing dynamic constants (condy) - in this case created by JaCoCo - can be read and
 * the corresponding constant is found after parsing.
 */
public class JiraBcel362TestCase extends AbstractTestCase {

    @Test
    public void testProcessConstantPoolWithCondyEntry() throws ClassNotFoundException {
        final JavaClass clazz = getTestJavaClass("issue362.Bcel362");
        final ConstantPoolGen constantPoolGen = assertDoesNotThrow(() -> new ConstantPoolGen(clazz.getConstantPool()));
        assertTrue(constantPoolGen.lookupUtf8("$jacocoData") != -1);
    }

    @Test
    public void testBCEL370() throws ClassNotFoundException {
        final JavaClass clazz = getTestJavaClass("com.Foo");
        final ConstantPoolGen cpg = new ConstantPoolGen(clazz.getConstantPool());
        for (final Method method : clazz.getMethods()) {
            final Code code = method.getCode();
            if (code != null) {
                final InstructionList instructionList = new InstructionList(code.getCode());
                for (final InstructionHandle instructionHandle : instructionList) {
                    instructionHandle.accept(new EmptyVisitor() {
                        @Override
                        public void visitLDC(LDC obj) {
                            assertDoesNotThrow(() -> obj.getType(cpg));
                            assertDoesNotThrow(() -> obj.getValue(cpg));
                        }
                    });
                }
            }
        }
    }
}
