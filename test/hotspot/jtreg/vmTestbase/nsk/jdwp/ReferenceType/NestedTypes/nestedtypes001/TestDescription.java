/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdwp/ReferenceType/NestedTypes/nestedtypes001.
 * VM Testbase keywords: [quick, jpda, jdwp]
 * VM Testbase readme:
 * DESCRIPTION
 *     This test performs checking for
 *         command set: ReferenceType
 *         command: NestedTypes
 *     Test checks that debugee accept the command packet and
 *     replies with correct reply packet. Also test checks that
 *     returned ReferenceTypeIDs for nested types are equal to
 *     thye expected ones.
 *     Test consists of two compoments:
 *         debugger: nestedtypes001
 *         debuggee: nestedtypes001a
 *     First, debugger uses nsk.share support classes to launch debuggee
 *     and obtain Transport object, that represents JDWP transport channel.
 *     Also communication channel (IOPipe) is established between
 *     debugger and debuggee to exchange with synchronization signals.
 *     Next, debugger obtains ReferenceTypeIDs for the tested class
 *     from debugee, and for all classes been nested for this class.
 *     Then, debugger creates command packet for NestedTypes command with the
 *     found referenceTypeID as an argument, writes packet to the transport
 *     channel, and waits for a reply packet.
 *     When reply packet is received, debugger parses the packet structure
 *     and extracts ReferenceTypeIDs of the nested types. Also test checks
 *     that the extreacted ReferenceTypeIDs for nested types are equal
 *     to the expected ones.
 *     Finally, debugger sends debuggee signal to quit, waits for it exits
 *     and exits too with the proper exit code.
 * COMMENTS
 *     Test fixed due to test bug:
 *     4908513 TEST_BUG: nsk/jdwp/ReferenceType/NestedTypes/nestedtypes001 assumes order
 *
 * @library /vmTestbase /test/hotspot/jtreg/vmTestbase
 *          /test/lib
 * @build nsk.jdwp.ReferenceType.NestedTypes.nestedtypes001
 *        nsk.jdwp.ReferenceType.NestedTypes.nestedtypes001a
 * @run main/othervm PropertyResolvingWrapper
 *      nsk.jdwp.ReferenceType.NestedTypes.nestedtypes001
 *      -arch=${os.family}-${os.simpleArch}
 *      -verbose
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

