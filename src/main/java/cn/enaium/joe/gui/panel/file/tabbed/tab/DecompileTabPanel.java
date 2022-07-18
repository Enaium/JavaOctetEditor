package cn.enaium.joe.gui.panel.file.tabbed.tab;

import cn.enaium.joe.gui.panel.CodeArea;
import cn.enaium.joe.util.ASyncUtil;
import cn.enaium.joe.util.CfrUtil;
import org.benf.cfr.reader.PluginRunner;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Enaium
 */
public class DecompileTabPanel extends ClassNodeTabPanel {
    public DecompileTabPanel(ClassNode classNode) {
        super(classNode);
        setLayout(new BorderLayout());
        CodeArea codeArea = new CodeArea();
        codeArea.setSyntaxEditingStyle("text/java");
        ASyncUtil.execute(() -> {
            codeArea.setText(CfrUtil.getSource(classNode));
        });
        codeArea.setCaretPosition(0);
        add(new RTextScrollPane(codeArea));
    }
}
