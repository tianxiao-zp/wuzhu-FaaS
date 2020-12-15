package com.tianxiao.faas.common.exception.biz;

public class LockedException extends RuntimeException {
    private String editor;

    public LockedException(String message, String editor) {
        super(message);
        this.editor = editor;
    }

    public LockedException(String message, Throwable cause, String editor) {
        super(message, cause);
        this.editor = editor;
    }

    public LockedException(Throwable cause, String editor) {
        super(cause);
        this.editor = editor;
    }

    public LockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String editor) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.editor = editor;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
