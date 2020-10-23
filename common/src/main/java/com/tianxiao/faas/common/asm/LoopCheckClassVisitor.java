package com.tianxiao.faas.common.asm;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * @author jianmiao.xu
 * @date 2020/10/17
 */
public class LoopCheckClassVisitor extends ClassVisitor implements Opcodes {

    public LoopCheckClassVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        // 跳过构造方法
        if (!name.equals("<init>") && mv != null) {
            mv = new MyMethodVisitor(mv);
        }
        return mv;
    }

    // 针对方法增强
    class MyMethodVisitor extends MethodVisitor implements Opcodes {

        public MyMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM5, mv);
        }

        @Override
        public void visitJumpInsn(int opcode,
                                  Label label) {
            if (opcode == Opcodes.GOTO && label != null) {
                // 在goto指令前插入计数器执行，统计循环体执行次数
                this.visitLdcInsn(label.toString());
                this.visitMethodInsn(Opcodes.INVOKESTATIC,
                                     "com/tianxiao/faas/common/asm/LoopCounter",
                                     "incr",
                                     "(Ljava/lang/String;)V",
                                     false);
            }
            super.visitJumpInsn(opcode, label);
        }
    }
}