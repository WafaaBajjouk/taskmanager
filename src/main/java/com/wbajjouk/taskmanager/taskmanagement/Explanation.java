//package com.wbajjouk.taskmanager.taskmanagement;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//
//public class Explanation {
//
//    public class MyController {
//        @Autowired
//        MyService myService;
//
//        public void saveFoo() {
//            myService.saveFoo();
//        }
//    }
//
//
//    public class MyTransactionalServiceDecorator implements MyService {
//        MyService delegate;
//
//        @Override
//        public void saveFoo() {
//            // begin tx
//            delegate.saveFoo();
//            // commit
//        }
//    }
//
//    public class GenericTransactionalInterceptor implements InvocationHandler {
//
//        @Override
//        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//            try {
//                // begin tx
//                return method.invoke(o, objects);
//            } catch (Exception ex) {
//                // rollback
//            } finally {
//                // commit
//            }
//        }
//    }
//
//    public interface MyService {
//        void saveFoo();
//    }
//
//    @Transactional
//    public class MyServiceImpl implements MyService {
//
//        @Override
//        public void saveFoo() {
//            // todo
//        }
//    }
//
//
//    // Without interface
//
//    public class MyOtherController {
//        @Autowired
//        MyOtherService myService;
//
//        public void saveFoo() {
//            myService.saveFoo();
//        }
//    }
//
//    @Transactional
//    public class MyOtherService{
//
//        public void saveFoo() {
//            // todo
//        }
//    }
//
//    public class GenericTransactionalInterceptorSubclass extends MyOtherService {
//        @Override
//        public void saveFoo() {
//            this.invocationHandler.invoke(object, args);
//        }
//    }
//    
//
//    public class MyOtherServiceTransactionalDecorator extends MyOtherService {
//        @Override
//        public void saveFoo() {
//            // open tx
//            super.saveFoo();
//            // commit
//        }
//    }
//    
//
//
//}
