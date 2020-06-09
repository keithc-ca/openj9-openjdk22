/*
 * Copyright (c) 2016, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8149991
 * @summary safepoint+cleanup=info should have output from the code
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver SafepointCleanupTest
 * @run driver SafepointCleanupTest -XX:+AsyncDeflateIdleMonitors
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class SafepointCleanupTest {
    static final String ASYNC_DISABLE_OPTION = "-XX:-AsyncDeflateIdleMonitors";
    static final String ASYNC_ENABLE_OPTION = "-XX:+AsyncDeflateIdleMonitors";
    static final String UNLOCK_DIAG_OPTION = "-XX:+UnlockDiagnosticVMOptions";

    static void analyzeOutputOn(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldContain("[safepoint,cleanup]");
        output.shouldContain("safepoint cleanup tasks");
        output.shouldContain("deflating global idle monitors");
        output.shouldContain("deflating per-thread idle monitors");
        output.shouldContain("updating inline caches");
        output.shouldContain("compilation policy safepoint handler");
        output.shouldHaveExitValue(0);
    }

    static void analyzeOutputOff(ProcessBuilder pb) throws Exception {
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldNotContain("[safepoint,cleanup]");
        output.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {
        String async_option;
        if (args.length == 0) {
            // By default test deflating idle monitors at a safepoint.
            async_option = ASYNC_DISABLE_OPTION;
        } else {
            async_option = args[0];
        }
        if (!async_option.equals(ASYNC_DISABLE_OPTION) &&
            !async_option.equals(ASYNC_ENABLE_OPTION)) {
            throw new RuntimeException("Unknown async_option value: '"
                                       + async_option + "'");
        }

        ProcessBuilder pb = ProcessTools.createJavaProcessBuilder("-Xlog:safepoint+cleanup=info",
                                                                  UNLOCK_DIAG_OPTION,
                                                                  async_option,
                                                                  InnerClass.class.getName());
        analyzeOutputOn(pb);

        pb = ProcessTools.createJavaProcessBuilder("-XX:+TraceSafepointCleanupTime",
                                                   UNLOCK_DIAG_OPTION,
                                                   async_option,
                                                   InnerClass.class.getName());
        analyzeOutputOn(pb);

        pb = ProcessTools.createJavaProcessBuilder("-Xlog:safepoint+cleanup=off",
                                                   UNLOCK_DIAG_OPTION,
                                                   async_option,
                                                   InnerClass.class.getName());
        analyzeOutputOff(pb);

        pb = ProcessTools.createJavaProcessBuilder("-XX:-TraceSafepointCleanupTime",
                                                   UNLOCK_DIAG_OPTION,
                                                   async_option,
                                                   InnerClass.class.getName());
        analyzeOutputOff(pb);
    }

    public static class InnerClass {
        public static void main(String[] args) throws Exception {
            System.out.println("Safepoint Cleanup test");
        }
    }
}
