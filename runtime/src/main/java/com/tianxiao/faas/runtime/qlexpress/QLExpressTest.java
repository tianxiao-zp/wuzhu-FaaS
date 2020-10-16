package com.tianxiao.faas.runtime.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

public class QLExpressTest {

    public static void main(String[] args) throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a",1);
        context.put("b",2);
        context.put("c",3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);

        runner.addOperatorWithAlias("如果", "if",null);
        runner.addOperatorWithAlias("则", "then",null);
        runner.addOperatorWithAlias("否则", "else",null);

        context.clear();
        context.put("语文", 88);
        context.put("数学", 99);
        context.put("英语", 95);
        String exp = "如果  (语文+数学+英语>270) 则 {return 1;} 否则 {return 0;}";
        Object execute = runner.execute(exp, context, null, false, false, null);
        System.out.printf(execute.toString());
    }
}
