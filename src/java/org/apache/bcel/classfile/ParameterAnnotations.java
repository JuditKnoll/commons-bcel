/*
 * Copyright  2000-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * base class for parameter annotations
 * 
 * @version $Id: ParameterAnnotations
 * @author  <A HREF="mailto:dbrosius@qis.net">D. Brosius</A>
 */
public abstract class ParameterAnnotations extends Attribute {

    private int num_parameters;
    private ParameterAnnotationEntry[] parameter_annotation_table; // Table of parameter annotations


    /**
     * @param parameter_annotation_type the subclass type of the parameter annotation
     * @param name_index Index pointing to the name <em>Code</em>
     * @param length Content length in bytes
     * @param file Input stream
     * @param constant_pool Array of constants
     */
    ParameterAnnotations(byte parameter_annotation_type, int name_index, int length,
            DataInputStream file, ConstantPool constant_pool) throws IOException {
        this(parameter_annotation_type, name_index, length, (ParameterAnnotationEntry[]) null,
                constant_pool);
        num_parameters = (file.readUnsignedByte());
        parameter_annotation_table = new ParameterAnnotationEntry[num_parameters];
        for (int i = 0; i < num_parameters; i++) {
            parameter_annotation_table[i] = new ParameterAnnotationEntry(file, constant_pool);
        }
    }


    /**
     * @param parameter_annotation_type the subclass type of the parameter annotation
     * @param name_index Index pointing to the name <em>Code</em>
     * @param length Content length in bytes
     * @param parameter_annotation_table the actual parameter annotations
     * @param constant_pool Array of constants
     */
    public ParameterAnnotations(byte parameter_annotation_type, int name_index, int length,
            ParameterAnnotationEntry[] parameter_annotation_table, ConstantPool constant_pool) {
        super(parameter_annotation_type, name_index, length, constant_pool);
        setParameterAnnotationTable(parameter_annotation_table);
    }


    /**
     * Called by objects that are traversing the nodes of the tree implicitely
     * defined by the contents of a Java class. I.e., the hierarchy of methods,
     * fields, attributes, etc. spawns a tree of objects.
     *
     * @param v Visitor object
     */
    public void accept( Visitor v ) {
        //	    v.visitParameterAnnotation(this);
    }


    /**
     * @param parameter_annotation_table the entries to set in this parameter annotation
     */
    public final void setParameterAnnotationTable(
            ParameterAnnotationEntry[] parameter_annotation_table ) {
        this.parameter_annotation_table = parameter_annotation_table;
        num_parameters = (parameter_annotation_table == null)
                ? 0
                : parameter_annotation_table.length;
    }


    /**
     * @return the parameter annotation entry table
     */
    public final ParameterAnnotationEntry[] getParameterAnnotationTable() {
        return parameter_annotation_table;
    }


    /**
     * returns the array of parameter annotation entries in this parameter annotation
     */
    public ParameterAnnotationEntry[] getParameterAnnotationEntries() {
        return parameter_annotation_table;
    }


    /**
     * @return the number of parameter annotation entries in this parameter annotation
     */
    public final int getNumParameterAnnotation() {
        return num_parameters;
    }
}
