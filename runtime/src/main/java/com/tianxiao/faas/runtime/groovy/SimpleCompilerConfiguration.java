package com.tianxiao.faas.runtime.groovy;

import com.tianxiao.faas.common.asm.LoopCheckClassVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import org.codehaus.groovy.control.BytecodeProcessor;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ParserPluginFactory;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义扩展groovy编译配置
 */
public class SimpleCompilerConfiguration extends CompilerConfiguration {


    /**
     * 扩展一个字节码的后置处理器，对动态编译后的groovy代码进行字节码增强
     */
    public SimpleCompilerConfiguration() {
        //super();
        this.setBytecodePostprocessor(new BytecodeProcessor() {
            @Override
            public byte[] processBytecode(String name, byte[] original) {
                return hackByteCode(name, original);
            }
        });
    }

    @Override
    public List<String> getClasspath() {
        return Collections.unmodifiableList(super.getClasspath());
    }

    @Override
    public List<CompilationCustomizer> getCompilationCustomizers() {
        return Collections.unmodifiableList(super.getCompilationCustomizers());
    }

    @Override
    public Set<String> getDisabledGlobalASTTransformations() {
        return Collections.emptySet();
    }

    @Override
    public Map<String, Object> getJointCompilationOptions() {
        return null;
    }

    @Override
    public Map<String, Boolean> getOptimizationOptions() {
        return Collections.unmodifiableMap(super.getOptimizationOptions());
    }

    @Override
    public Set<String> getScriptExtensions() {
        return Collections.unmodifiableSet(super.getScriptExtensions());
    }

    @Override
    public void setBytecodePostprocessor(BytecodeProcessor bytecodePostprocessor) {
        super.setBytecodePostprocessor(bytecodePostprocessor);
    }

    @Override
    public void setClasspath(String classpath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setClasspathList(List<String> parts) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CompilerConfiguration addCompilationCustomizers(CompilationCustomizer... customizers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDebug(boolean debug) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDefaultScriptExtension(String defaultScriptExtension) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDisabledGlobalASTTransformations(Set<String> disabledGlobalASTTransformations) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setJointCompilationOptions(Map<String, Object> options) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMinimumRecompilationInterval(int time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOptimizationOptions(Map<String, Boolean> options) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOutput(PrintWriter output) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setParameters(boolean parameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPluginFactory(ParserPluginFactory pluginFactory) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRecompileGroovySource(boolean recompile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setScriptBaseClass(String scriptBaseClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setScriptExtensions(Set<String> scriptExtensions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSourceEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTargetBytecode(String version) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTargetDirectory(File directory) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTargetDirectory(String directory) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTolerance(int tolerance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setVerbose(boolean verbose) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPreviewFeatures(boolean previewFeatures) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWarningLevel(int level) {
        throw new UnsupportedOperationException();
    }

    /**
     * 采用字节码增加的方法，将待测试类的字节码文件织入循环检测计数器，实现死循环检测的功能
     */
    private byte[] hackByteCode(String name, byte[] originCode) {
        String me = name;
        // 读取原class文件二进制文件
        ClassReader classReader = new ClassReader(originCode);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //织入循环检测计数器
        ClassVisitor classVisitor = new LoopCheckClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        // 重新编译
        byte[] data = classWriter.toByteArray();
        return data;
    }
}
