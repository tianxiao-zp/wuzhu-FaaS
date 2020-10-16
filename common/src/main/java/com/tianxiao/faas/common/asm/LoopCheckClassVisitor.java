package com.tianxiao.faas.common.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LoopCheckClassVisitor extends ClassVisitor implements Opcodes {
    public LoopCheckClassVisitor(int api) {
        super(api);
    }

    public LoopCheckClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature,
                exceptions);
        // 跳过构造方法
        if (!name.equals("<init>") && mv != null) {
            mv = new MethodLoopVisitor(ASM5, mv);
        }
        return mv;
    }

    static class MethodLoopVisitor extends MethodVisitor implements Opcodes {

        public MethodLoopVisitor(int api) {
            super(api);
        }

        public MethodLoopVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            if (opcode == Opcodes.GOTO && label != null) {
                // 在goto指令前插入计数器执行，统计循环体执行次数
                this.visitLdcInsn(label.toString());
                this.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "com/asm/mytest/LoopCounter",
                        "incr",
                        "(Ljava/lang/String;)V",
                        false);
            }
            super.visitJumpInsn(opcode, label);
        }
    }
}
