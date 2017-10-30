/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * @LastModified: Oct 2017
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/**
 * Marks a class of errors in which XSLTC has reached some incorrect internal
 * state from which it cannot recover.
 */
public class InternalError extends Error {
    private static final long serialVersionUID = -6690855975016554786L;

    /**
     * Construct an <code>InternalError</code> with the specified error message.
     * @param msg the error message
     */
    public InternalError(String msg) {
        super(msg);
    }
}
