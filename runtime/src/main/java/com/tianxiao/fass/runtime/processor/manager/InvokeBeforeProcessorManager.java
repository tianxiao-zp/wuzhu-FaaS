package com.tianxiao.fass.runtime.processor.manager;

import com.tianxiao.fass.runtime.processor.InvokeBeforeProcessor;

import java.util.List;

public interface InvokeBeforeProcessorManager {
    List<InvokeBeforeProcessor> getProcessors();
}
