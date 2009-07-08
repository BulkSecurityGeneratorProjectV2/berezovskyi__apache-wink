/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *  
 *   http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *  
 *******************************************************************************/
 

package org.apache.wink.server.internal.registry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.wink.common.internal.lifecycle.LifecycleManagersRegistry;
import org.apache.wink.server.internal.registry.ResourceRecord;
import org.apache.wink.server.internal.registry.ResourceRecordFactory;


import junit.framework.TestCase;

public class ResourceRecordFactoryTest extends TestCase {

    @Path("/path1")
    public static class Resource1 {
        
        @GET
        public String get() {
            return "GET";
        }
    }
    
    @Path("/path2")
    public static class Resource2 {
        
        @GET
        public String get() {
            return "GET";
        }
    }

    public void testFactory() {
        ResourceRecordFactory factory = new ResourceRecordFactory(new LifecycleManagersRegistry());
        
        ResourceRecord record = factory.getResourceRecord(Resource1.class);
        assertEquals("/path1", record.getMetadata().getPath());
        record = factory.getResourceRecord(Resource2.class);
        assertEquals("/path2", record.getMetadata().getPath());
        
        Resource1 r1 = new Resource1();
        ResourceRecord record1 = factory.getResourceRecord(r1);
        assertEquals("/path1", record1.getMetadata().getPath());
        Object o = record1.getObjectFactory().getInstance(null);
        assertTrue(o instanceof Resource1);
        record = factory.getResourceRecord(Resource1.class);
        assertTrue(record == record1);
        o = record.getObjectFactory().getInstance(null);
        assertTrue(o instanceof Resource1);
        
        Resource2 r2 = new Resource2();
        ResourceRecord record2 = factory.getResourceRecord(r2);
        assertEquals("/path2", record2.getMetadata().getPath());
        o = record2.getObjectFactory().getInstance(null);
        assertTrue(o instanceof Resource2);
        record = factory.getResourceRecord(Resource2.class);
        assertTrue(record == record2);
        o = record.getObjectFactory().getInstance(null);
        assertTrue(o instanceof Resource2);
    }
}
