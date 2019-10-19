package com.antkorwin.unicoder;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created on 16/10/2019
 * <p>
 * The action to convert from a native unicode string value
 * to the valid java escaped string.
 *
 * This action convert selected string in editor to a valid UTF-8 value.
 *
 * @author Korovin Anatoliy
 */
public class ConvertToUnicodeAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        final Document document = editor.getDocument();

        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();

        // Replace the selection with a unicode string
        String selectedText = document.getText(TextRange.from(start, end-start));
        // ToDo: implement this locally, instead of using the apache commons:
        String result = StringEscapeUtils.escapeJava(selectedText);

        WriteCommandAction.runWriteCommandAction(project,
                                                 () -> document.replaceString(start, end, result));
    }
}
