package cn.enaium.joe.gui.panel.file.tabbed.tab;

import cn.enaium.joe.gui.panel.CodeArea;
import cn.enaium.joe.util.ASyncUtil;
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
            ClassFileSource cfs = new ClassFileSource() {
                @Override
                public void informAnalysisRelativePathDetail(String a, String b) {
                }

                @Override
                public String getPossiblyRenamedPath(String path) {
                    return path;
                }

                @Override
                public Pair<byte[], String> getClassFileContent(String path) throws IOException {
                    String name = path.substring(0, path.length() - 6);
                    if (name.equals(classNode.name)) {
                        ClassWriter classWriter = new ClassWriter(0);
                        classNode.accept(classWriter);
                        return Pair.make(classWriter.toByteArray(), name);
                    }
                    return null;
                }

                @Override
                public Collection<String> addJar(String arg0) {
                    throw new RuntimeException();
                }
            };

            codeArea.setText(new PluginRunner(new HashMap<>(), cfs).getDecompilationFor(classNode.name));
        });
        codeArea.setCaretPosition(0);
        add(new RTextScrollPane(codeArea));
    }
}
