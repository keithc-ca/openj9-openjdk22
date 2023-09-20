/*
 * Copyright (c) 2003, 2023, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Robot;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.HTMLEditorKit;


/*
 * @test
 * @bug 4284162
 * @key headful
 * @summary Tests if css text-indent attribute is supported with negative values.
 */

public class bug4284162 {
    private static JEditorPane jep;
    private static JFrame frame;
    private static volatile boolean passed = false;

    public static void main(String[] args) throws Exception {
        try {
            Robot robot = new Robot();

            SwingUtilities.invokeAndWait(bug4284162::createAndShowUI);
            robot.waitForIdle();
            robot.delay(500);

            SwingUtilities.invokeAndWait(bug4284162::testUI);

            if (!passed) {
                throw new RuntimeException("Test failed!!" +
                        " CSS Text-indent attribute doesn't support negative values");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    public static void createAndShowUI() {
        String text ="<html><head><style>body {text-indent: -24.000000pt;}</style></head>"
                + "<body><p>paragraph</body></html>";

        frame = new JFrame("CSS Text-Indent Test");
        jep = new JEditorPane();
        jep.setEditorKit(new HTMLEditorKit());
        jep.setEditable(false);

        jep.setText(text);

        frame.getContentPane().add(jep);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private static void testUI() {
        View v = jep.getUI().getRootView(jep);
        while (!(v instanceof javax.swing.text.html.ParagraphView)) {
            int n = v.getViewCount();
            v = v.getView(n - 1);
        }

        AttributeSet attrs = v.getAttributes();
        Object attr = attrs.getAttribute(StyleConstants.FirstLineIndent);
        passed = (attr.toString().startsWith("-"));
    }
}
