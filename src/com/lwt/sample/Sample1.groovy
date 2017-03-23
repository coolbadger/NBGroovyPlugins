package com.lwt.sample

import com.navis.argo.business.api.GroovyApi

/**
 * Created by lekoxnfx on 2017/3/23.
 */
class Sample1 {
    GroovyApi groovyApi = new GroovyApi()

    public void test1(){
        String className = "Class2"
        //better use try-catch
        //Way1:java-style
        def classInstance1 = groovyApi.getGroovyClassInstance(className)
        String res1 = classInstance1.invokeMethod("doTest",null)
        //Way2:groovy-style，需要import Class2
        Class2 classInstance2 = (Class2)groovyApi.getGroovyClassInstance(className)
        String res2 = classInstance2.doTest()
        //way3:无法import Class2
        def classInstance3 = groovyApi.getGroovyClassInstance(className)
        String res3 = classInstance3.doTest()//不要在意下划线


    }


    class Class2{
        public String doTest(){
            return "test"
        }
    }
}
